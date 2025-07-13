package com.lightning.test.config;

import org.springframework.cloud.client.ServiceInstance;

import org.springframework.cloud.loadbalancer.core.RoundRobinLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;

import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class LoadBalancerConfig {


    @Bean
    public ReactorServiceInstanceLoadBalancer helloServiceLoadBalancer(
            LoadBalancerClientFactory clientFactory,
            Environment environment) {
        String serviceName = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);

        return new RoundRobinLoadBalancer(
                clientFactory.getLazyProvider(serviceName, ServiceInstanceListSupplier.class),
                serviceName
        );
    }
}

