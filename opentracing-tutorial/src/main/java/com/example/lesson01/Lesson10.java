package com.example.lesson01;

import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.util.GlobalTracer;

public class Lesson10 {
    private final Tracer tracer;

    public Lesson10(Tracer tracer) {
        this.tracer = tracer;
    }

    public static void main(String[] args) {
        if (args.length != 1) throw new IllegalArgumentException();
        new Lesson10(GlobalTracer.get()).sayHello(args[0]);
    }

    private void sayHello(String helloTo) {
        Span span = tracer.buildSpan("say-hello").start();
        String helloStr = String.format("Hello, %s", helloTo);
        System.out.println(helloStr);
        span.finish();
    }
}
