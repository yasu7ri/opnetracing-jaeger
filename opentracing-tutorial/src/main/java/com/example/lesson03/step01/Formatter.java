package com.example.lesson03.step01;

import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Environment;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

public class Formatter extends Application<Configuration> {
    public static void main(String[] args) throws Exception {
        System.setProperty("dw.server.applicationConnectors[0].port", "8081");
        System.setProperty("dw.server.adminConnectors[0].port", "9081");
        new Formatter().run(args);
    }

    @Override
    public void run(Configuration configuration, Environment environment) throws Exception {
        environment.jersey().register(new FormatterResource());
    }

    @Path("/format")
    @Produces(MediaType.TEXT_PLAIN)
    public class FormatterResource {
        @GET
        public String format(@QueryParam("helloTo") String helloTo) {
            return String.format("Hello, %s!", helloTo);
        }
    }
}
