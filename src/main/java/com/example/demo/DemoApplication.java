package com.example.demo;

import brave.http.HttpTracing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.netty.NettyServerCustomizer;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import reactor.netty.http.brave.ReactorNettyHttpTracing;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		// enable reactor netty access log
		System.setProperty("reactor.netty.http.server.accessLogEnabled", "true");

		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public NettyServerCustomizer tracingCustomizer(HttpTracing httpTracing) {
		// enable Reactor Netty - Brave integration
		return server -> ReactorNettyHttpTracing.create(httpTracing).decorateHttpServer(server);
	}

	@Bean
	public RouteLocator myRoutes(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(p -> p.path("/get").uri("http://httpbin.org/get"))
				.build();
	}
}
