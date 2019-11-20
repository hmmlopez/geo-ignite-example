package com.example.geoigniteclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GeoIgniteClientApplication {
	static {
		System.setProperty("IGNITE_QUIET","false");
		System.setProperty("java.net.preferIPv4Stack","true");
	}

	public static void main(String[] args) {
		SpringApplication.run(GeoIgniteClientApplication.class, args);
	}

}
