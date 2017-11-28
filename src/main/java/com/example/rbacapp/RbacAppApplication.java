package com.example.rbacapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;
import reactor.ipc.netty.NettyContext;
import reactor.ipc.netty.http.server.HttpServer;


@SpringBootApplication
@EnableWebFlux
public class RbacAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(RbacAppApplication.class, args);
	}

	@Bean
	public NettyContext nettyContext(ApplicationContext context) {
		HttpHandler handler = WebHttpHandlerBuilder.applicationContext(context).build();
		ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(handler);
		HttpServer httpServer = HttpServer.create("localhost", 8081);
		return httpServer.newHandler(adapter).block();
	}

}
