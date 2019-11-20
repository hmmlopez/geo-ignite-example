package com.example.geoigniteserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GeoIgniteServerApplication {
	static {
		System.setProperty("IGNITE_QUIET","false");
		System.setProperty("java.net.preferIPv4Stack","true");
	}

	public static void main(String[] args) {
		SpringApplication.run(GeoIgniteServerApplication.class, args);
	}

}
