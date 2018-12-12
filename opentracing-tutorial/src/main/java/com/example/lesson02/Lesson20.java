package com.example.lesson02;

import com.google.common.collect.ImmutableMap;
import io.jaegertracing.Configuration;
import io.jaegertracing.internal.JaegerTracer;
import io.opentracing.Span;
import io.opentracing.Tracer;

public class Lesson20 {
    private final Tracer tracer;

    public Lesson20(Tracer tracer) {
        this.tracer = tracer;
    }

    public static void main(String[] args) {
        Tracer tracer = initTracer("hello-world");
        new Lesson20(tracer).sayHello("Seiya Suzuki");
    }

    private void sayHello(String helloTo) {
        Span span = tracer.buildSpan("say-hello").start();
        span.setTag("hello-to", helloTo);
        String helloStr = formatString(span, helloTo);
        printHello(span, helloStr);
        span.finish();
    }

    private String formatString(Span rootSpan, String helloTo) {
        Span span = tracer.buildSpan("formatString").start();
        try {
            String helloStr = String.format("Hello, %s", helloTo);
            span.log(ImmutableMap.of("event", "string-format", "value", helloStr));
            return helloStr;
        } finally {
            span.finish();
        }
    }

    private void printHello(Span rootSpan, String helloStr) {
        Span span = tracer.buildSpan("printHello").start();
        try {
            System.out.println(helloStr);
            span.log(ImmutableMap.of("event", "println"));
        } finally {
            span.finish();
        }
    }

    private static JaegerTracer initTracer(String service) {
        Configuration.SamplerConfiguration samplerConfiguration = Configuration.SamplerConfiguration.fromEnv().withType("const").withParam(1);
        Configuration.ReporterConfiguration reporterConfiguration = Configuration.ReporterConfiguration.fromEnv().withLogSpans(true);
        Configuration configuration = new Configuration(service).withSampler(samplerConfiguration).withReporter(reporterConfiguration);
        return configuration.getTracer();
    }
}
