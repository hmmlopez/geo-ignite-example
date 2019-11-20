package com.example.geoigniteclient.config;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.logger.slf4j.Slf4jLogger;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class AppConfig {

    @Bean
    public Ignite igniteInstance() {
        return Ignition.start(igniteConfiguration());
    }

    private IgniteConfiguration igniteConfiguration() {
        return new IgniteConfiguration()
                .setClientMode(true)
                .setPeerClassLoadingEnabled(true)
                .setIgniteHome(System.getProperty("user.dir") + "/client/target/ignite")
                .setGridLogger(new Slf4jLogger())
                .setMetricsLogFrequency(0)
                .setDiscoverySpi(discoverySpi());
    }

    private TcpDiscoverySpi discoverySpi() {
        return new TcpDiscoverySpi().setIpFinder(ipFinder());
    }

    private TcpDiscoveryVmIpFinder ipFinder() {
        return new TcpDiscoveryVmIpFinder().setAddresses(Arrays.asList("localhost:47500"));
    }

}
