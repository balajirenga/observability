package com.superh;


import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.logs.GlobalLoggerProvider;
import io.opentelemetry.api.trace.propagation.W3CTraceContextPropagator;
import io.opentelemetry.context.propagation.ContextPropagators;
import io.opentelemetry.exporter.otlp.http.trace.OtlpHttpSpanExporter;
import io.opentelemetry.exporter.otlp.http.trace.OtlpHttpSpanExporterBuilder;
import io.opentelemetry.exporter.otlp.logs.OtlpGrpcLogRecordExporter;
import io.opentelemetry.exporter.otlp.metrics.OtlpGrpcMetricExporter;
import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.logs.SdkLoggerProvider;
import io.opentelemetry.sdk.logs.export.BatchLogRecordProcessor;
import io.opentelemetry.sdk.metrics.SdkMeterProvider;
import io.opentelemetry.sdk.metrics.export.PeriodicMetricReader;
import io.opentelemetry.sdk.resources.Resource;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor;
import io.opentelemetry.sdk.trace.samplers.Sampler;
import io.opentelemetry.semconv.resource.attributes.ResourceAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.codec.EncoderHttpMessageWriter;

@SpringBootApplication
public class SuperHeroApplication  {
	private static final Logger logger = LoggerFactory.getLogger(SuperHeroApplication.class);

	public static void main(String[] args) {

		//String otLPEndPoint = "https://612dfe4aee8b4a39813fefc77144d97e.apm.us-central1.gcp.cloud.es.io:443";
		
		String otLPEndPoint = "http://localhost:8080/country/postjson";
		String secretToken = "gy328vOqmYYjWLMkrY";
		System.out.println("OTLP URL " + otLPEndPoint);
		System.out.println("OTLP Token " + secretToken);

		String SERVICE_NAME = "SuperHeroService";

		// set service name on all OTel signals
		Resource resource = Resource.getDefault().merge(Resource.create(
				Attributes.of(ResourceAttributes.SERVICE_NAME, SERVICE_NAME,ResourceAttributes.SERVICE_VERSION,"1.0",ResourceAttributes.DEPLOYMENT_ENVIRONMENT,"production")));

		// init OTel logger provider with export to OTLP
		SdkLoggerProvider sdkLoggerProvider = SdkLoggerProvider.builder()
				.setResource(resource)
				.addLogRecordProcessor(BatchLogRecordProcessor.builder(
								OtlpGrpcLogRecordExporter.builder().setEndpoint(otLPEndPoint).addHeader("Authorization", "Bearer " + secretToken)

										.build())
						.build())
				.build();

		// init OTel trace provider with export to OTLP
		SdkTracerProvider sdkTracerProvider =  SdkTracerProvider.builder().setResource(resource)
			.addSpanProcessor(BatchSpanProcessor.builder(
				OtlpHttpSpanExporter.builder().setEndpoint(otLPEndPoint).addHeader("Authorization", "Bearer " + secretToken)
				.addHeader("OTEL_EXPORTER_OTLP_PROTOCOL", "http/json")
									.build())
					.build())
			.build();
			
				// add span processor to add baggage as span attributes
				//.addSpanProcessor(BatchSpanProcessor.builder(OtlpGrpcSpanExporter
				// KEY
				// .addSpanProcessor(BatchSpanProcessor.builder(OtlpHttpSpanExporterBuilder.builder()
				// 		.setEndpoint(otLPEndPoint).addHeader("Authorization", "Bearer " + secretToken)
				// 		.build()).build())
				// .build();
					// KEY
		

		// init OTel meter provider with export to OTLP
		SdkMeterProvider sdkMeterProvider = SdkMeterProvider.builder().setResource(resource)
				.registerMetricReader(PeriodicMetricReader.builder(
								OtlpGrpcMetricExporter.builder().setEndpoint(otLPEndPoint).addHeader("Authorization", "Bearer " + secretToken)
										.build())
						.build())
				.build();

		// create sdk object and set it as global
		OpenTelemetrySdk sdk = OpenTelemetrySdk.builder()
				.setTracerProvider(sdkTracerProvider)
				.setLoggerProvider(sdkLoggerProvider)
				.setMeterProvider(sdkMeterProvider)
				.setPropagators(ContextPropagators
						.create(W3CTraceContextPropagator.getInstance()))
				.build();

		GlobalOpenTelemetry.set(sdk);
		// connect logger
		GlobalLoggerProvider.set(sdk.getSdkLoggerProvider());
		// Add hook to close SDK, which flushes logs
		Runtime.getRuntime().addShutdownHook(new Thread(sdk::close));

		SpringApplication.run(SuperHeroApplication.class, args);
	}

}
