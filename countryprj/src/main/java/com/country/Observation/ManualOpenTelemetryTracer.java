package com.country.Observation;

import ch.qos.logback.core.util.SystemInfo;
import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.common.AttributesBuilder;
import io.opentelemetry.api.trace.propagation.W3CTraceContextPropagator;
import io.opentelemetry.context.propagation.ContextPropagators;
import io.opentelemetry.exporter.otlp.http.trace.OtlpHttpSpanExporter;
import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.logs.SdkLoggerProvider;
import io.opentelemetry.sdk.logs.export.BatchLogRecordProcessor;
import io.opentelemetry.sdk.resources.Resource;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor;
import io.opentelemetry.sdk.trace.samplers.Sampler;
import io.opentelemetry.semconv.resource.attributes.ResourceAttributes;

public class ManualOpenTelemetryTracer {


    public void setup() {
       //String otLPEndPoint = "https://612dfe4aee8b4a39813fefc77144d97e.apm.us-central1.gcp.cloud.es.io:443";
     // String otLPEndPoint = "http://localhost:8080/country/postjson";
	    String otLPEndPoint = "http://localhost:4318/v1/traces";
	 // String otLPEndPoint = "http://localhost:8282/games/manual/v1/traces";
        String secretToken = "gy328vOqmYYjWLMkrY";
        System.out.println("OTLP URL " + otLPEndPoint);
        System.out.println("OTLP Token " + secretToken);

        String ServiceName = "CountryService";

       // set service name on all OTel signals
		Resource resource = Resource.getDefault().merge(Resource.create(
            Attributes.of(ResourceAttributes.SERVICE_NAME, ServiceName,ResourceAttributes.SERVICE_VERSION,"1.0",ResourceAttributes.DEPLOYMENT_ENVIRONMENT,"BalajiHome")));

         //   ResourceAttributes.TelemetrySdkLanguageValues.JAVA,"JAVA"
    
        // init OTel trace provider with export to OTLP
		//SdkTracerProvider sdkTracerProvider2 = extractedTracerProvider2(otLPEndPoint,secretToken,resource);
        
        SdkTracerProvider sdkTracerProvider2 = extractedHTTPTracerProvider2(otLPEndPoint,secretToken,resource);
        
            // create sdk object and set it as global
            OpenTelemetrySdk sdk = OpenTelemetrySdk.builder()
            .setTracerProvider(sdkTracerProvider2)
            .setPropagators(ContextPropagators
                    .create(W3CTraceContextPropagator.getInstance()))
            .build();

            GlobalOpenTelemetry.set(sdk);
           
            // Add hook to close SDK, which flushes logs
            Runtime.getRuntime().addShutdownHook(new Thread(sdk::close));

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

    private static SdkTracerProvider extractedHTTPTracerProvider2(String otLPEndPoint, String secretToken, Resource resource) {
		// init OTel trace provider with export to OTLP
		SdkTracerProvider sdkTracerProvider = SdkTracerProvider.builder()
				.setResource(resource).setSampler(Sampler.alwaysOn())
				// add span processor to add baggage as span attributes
				.addSpanProcessor(BatchSpanProcessor.builder(OtlpHttpSpanExporter
						.builder()
						.setEndpoint(otLPEndPoint).addHeader("Authorization", "Bearer " + secretToken)
						.build()).build())
				.build();

				return sdkTracerProvider;
	}

	// private static SdkTracerProvider extractedTracerProvider2(String otLPEndPoint, String secretToken, Resource resource) {

		

	// 	return SdkTracerProvider.builder()
	// 			.setResource(Resource.getDefault()).setSampler(Sampler.alwaysOn())
	// 			// add span processor to add baggage as span attributes
	// 			.addSpanProcessor(BatchSpanProcessor.builder(OtlpHttpSpanExporter
	// 					.builder()
	// 					.setEndpoint(
	// 						otLPEndPoint).addHeader("Authorization", "Bearer " + secretToken)
	// 					.build()).build())
	// 			.build();

	// }

}
