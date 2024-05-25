package com.movieapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.common.AttributeKey;
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

@SpringBootApplication
public class FavoriteApplication {
	private static final Logger logger = LoggerFactory.getLogger(FavoriteApplication.class);

	public static void main(String[] args) {

		//oldcode
		/*Map<String, String> env = System.getenv();
		env.put("OTEL_EXPORTER_OTLP_ENDPOINT", "https://612dfe4aee8b4a39813fefc77144d97e.apm.us-central1.gcp.cloud.es.io:443");
		env.put("ELASTIC_APM_SECRET_TOKEN", "gy328vOqmYYjWLMkrY");
		System.out.println("Env variables..." + env.toString()); */

		String otLPEndPoint = "https://612dfe4aee8b4a39813fefc77144d97e.apm.us-central1.gcp.cloud.es.io:443";
		
		//String otLPEndPoint = "http://localhost:8080/country/postjson";
		String secretToken = "gy328vOqmYYjWLMkrY";
		System.out.println("OTLP URL " + otLPEndPoint);
		System.out.println("OTLP Token " + secretToken);

		//oldcode
		//String SERVICE_NAME = System.getenv("OTEL_SERVICE_NAME");
		String SERVICE_NAME ="Testservice";

		System.out.println("SERVICE_NAME " + SERVICE_NAME);


		// set service name on all OTel signals
		Resource resource = Resource.getDefault().merge(Resource.create(
				Attributes.of(ResourceAttributes.SERVICE_NAME, SERVICE_NAME,ResourceAttributes.SERVICE_VERSION,"1.0",ResourceAttributes.DEPLOYMENT_ENVIRONMENT,"production")));
				
		// init OTel logger provider with export to OTLP
		SdkLoggerProvider sdkLoggerProvider = extractedLoggerProvider(otLPEndPoint, secretToken, resource);
			System.out.println("Provider details.." + sdkLoggerProvider.toString());
		

		// init OTel trace provider with export to OTLP
		SdkTracerProvider sdkTracerProvider = extractedTracerProvider2(otLPEndPoint, secretToken, resource);


		// init OTel meter provider with export to OTLP
		SdkMeterProvider sdkMeterProvider = extractedMeterProvider(otLPEndPoint, secretToken, resource);

		
			

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

		SpringApplication.run(FavoriteApplication.class, args);
	}

	private static SdkLoggerProvider extractedLoggerProvider(String otLPEndPoint, String secretToken, Resource resource) {
		return SdkLoggerProvider.builder()
				.setResource(resource)
				.addLogRecordProcessor(BatchLogRecordProcessor.builder(
								OtlpGrpcLogRecordExporter.builder().setEndpoint(
									otLPEndPoint).addHeader("Authorization", "Bearer " + secretToken)

										.build())
						.build())
				.build();
	}

	private static SdkTracerProvider extractedTracerProvider(String otLPEndPoint, String secretToken, Resource resource) {
		return SdkTracerProvider.builder()
				.setResource(resource).setSampler(Sampler.alwaysOn())
				// add span processor to add baggage as span attributes
				.addSpanProcessor(BatchSpanProcessor.builder(OtlpGrpcSpanExporter
						.builder()
						.setEndpoint(
							otLPEndPoint).addHeader("Authorization", "Bearer " + secretToken)
						.build()).build())
				.build();
				
	}
	private static SdkTracerProvider extractedTracerProvider2(String otLPEndPoint, String secretToken, Resource resource) {

		

		// init OTel trace provider with export to OTLP
		SdkTracerProvider sdkTracerProvider = SdkTracerProvider.builder()
				.setResource(resource).setSampler(Sampler.alwaysOn())
				// add span processor to add baggage as span attributes
				.addSpanProcessor(BatchSpanProcessor.builder(OtlpGrpcSpanExporter
						.builder()
						.setEndpoint(otLPEndPoint).addHeader("Authorization", "Bearer " + secretToken)
						.build()).build())
				.build();

				return sdkTracerProvider;

	}


	private static SdkMeterProvider extractedMeterProvider(String otLPEndPoint, String secretToken, Resource resource) {
		return SdkMeterProvider.builder().setResource(resource)
				.registerMetricReader(PeriodicMetricReader.builder(
								OtlpGrpcMetricExporter.builder().setEndpoint(
									otLPEndPoint).addHeader("Authorization", "Bearer " + secretToken)
										.build())
						.build())
				.build();
	}
}
