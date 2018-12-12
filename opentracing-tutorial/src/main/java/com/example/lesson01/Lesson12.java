package com.example.lesson01;

import com.google.common.collect.ImmutableMap;
import io.jaegertracing.Configuration;
import io.jaegertracing.Configuration.ReporterConfiguration;
import io.jaegertracing.Configuration.SamplerConfiguration;
import io.jaegertracing.internal.JaegerTracer;
import io.opentracing.Span;
import io.opentracing.Tracer;

public class Lesson12 {
    private final Tracer tracer;

    public Lesson12(Tracer tracer) {
        this.tracer = tracer;
    }

    public static void main(String[] args) {
        Tracer tracer = initTracer("hello-world");
        new Lesson12(tracer).sayHello("Seiya Suzuki");
    }

    private void sayHello(String helloTo) {
        Span span = tracer.buildSpan("say-hello").start();
        span.setTag("hello-to", helloTo);
        String helloStr = String.format("Hello, %s", helloTo);
        span.log(ImmutableMap.of("event", "string-format", "value", helloStr));
        System.out.println(helloStr);
        span.log(ImmutableMap.of("event", "println"));
        span.finish();
    }

    private static JaegerTracer initTracer(String service) {
        SamplerConfiguration samplerConfiguration = SamplerConfiguration.fromEnv().withType("const").withParam(1);
        ReporterConfiguration reporterConfiguration = ReporterConfiguration.fromEnv().withLogSpans(true);
        Configuration configuration = new Configuration(service).withSampler(samplerConfiguration).withReporter(reporterConfiguration);
        return configuration.getTracer();
    }
}
