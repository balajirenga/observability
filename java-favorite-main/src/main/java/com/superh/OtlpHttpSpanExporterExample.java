package com.superh;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.ContextKey;
import io.opentelemetry.context.propagation.TextMapPropagator;
import io.opentelemetry.exporter.otlp.http.trace.OtlpHttpSpanExporter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.resources.Resource;
import io.opentelemetry.sdk.trace.IdGenerator;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.SimpleSpanProcessor;

public class OtlpHttpSpanExporterExample {

  // private static final String TRACER_NAME = "MyTracer";
  // private static final ContextKey<String> TRACE_ID_KEY = ContextKey.named("traceId");
  // private static final ContextKey<String> SPAN_ID_KEY = ContextKey.named("spanId");

  // public static void main(String[] args) {
  //   // Create an OpenTelemetrySdk with an OtlpHttpSpanExporter
  //   OtlpHttpSpanExporter spanExporter =
  //       OtlpHttpSpanExporter.builder()
  //          .setEndpoint("http://example.com:4317")
  //          .build();
  //   OpenTelemetrySdk openTelemetrySdk =
  //       OpenTelemetrySdk.builder()
  //          .setTracerProvider(
  //               SdkTracerProvider.builder()
  //                  .addSpanProcessor(SimpleSpanProcessor.create(spanExporter))
  //                  .setResource(Resource.create(Attributes.empty()))
  //                  .build())
  //          .build();

  //   // Get a Tracer
  //   Tracer tracer = openTelemetrySdk.builder().getOpenTelemetry().getTracer(TRACER_NAME);

  //   // Start a span
  //   Span span = tracer.spanBuilder("MySpan").startSpan();

  //   // Set some attributes
  //   span.setAttribute("attribute.key", "attribute.value");

  //   // Add an event
  //   span.addEvent("Event 1", Attributes.empty());

  //   // Finish the span
  //   span.end();

  //   // Close the OpenTelemetrySdk
  //   openTelemetrySdk.close();
  // }
}