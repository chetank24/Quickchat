package com.quickchat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

//extend SpringBootServletInitializer and override configure method
//if you want to deploy war on servlet container which is not embedded

@SpringBootApplication
@EnableDiscoveryClient
public class QuickchatApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(QuickchatApplication.class, args);
	}
	
	 @Override
	    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	        return application.sources(QuickchatApplication.class);
	    }
}
