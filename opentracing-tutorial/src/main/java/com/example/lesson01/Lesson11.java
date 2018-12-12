package com.example.lesson01;

import io.jaegertracing.Configuration;
import io.jaegertracing.Configuration.ReporterConfiguration;
import io.jaegertracing.Configuration.SamplerConfiguration;
import io.jaegertracing.internal.JaegerTracer;
import io.opentracing.Span;
import io.opentracing.Tracer;

public class Lesson11 {
    private final Tracer tracer;

    public Lesson11(Tracer tracer) {
        this.tracer = tracer;
    }

    public static void main(String[] args) {
        if (args.length != 1) throw new IllegalArgumentException();
        Tracer tracer = initTracer("hello-world");
        new Lesson11(tracer).sayHello(args[0]);
    }

    private void sayHello(String helloTo) {
        Span span = tracer.buildSpan("say-hello").start();
        String helloStr = String.format("Hello, %s", helloTo);
        System.out.println(helloStr);
        span.finish();
    }

    private static JaegerTracer initTracer(String service) {
        SamplerConfiguration samplerConfiguration = SamplerConfiguration.fromEnv().withType("const").withParam(1);
        ReporterConfiguration reporterConfiguration = ReporterConfiguration.fromEnv().withLogSpans(true);
        Configuration configuration = new Configuration(service).withSampler(samplerConfiguration).withReporter(reporterConfiguration);
        return configuration.getTracer();
    }
}
