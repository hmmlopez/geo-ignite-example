package com.example.geoigniteserver.config;

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
                .setPeerClassLoadingEnabled(true)
                .setGridLogger(new Slf4jLogger())
                .setIgniteHome(System.getProperty("user.dir") + "/server/target/ignite")
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
