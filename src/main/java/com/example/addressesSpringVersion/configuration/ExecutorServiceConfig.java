package com.example.addressesSpringVersion.configuration;

import org.springframework.boot.ApplicationArguments;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class ExecutorServiceConfig {

    private final ApplicationArguments args;

    public ExecutorServiceConfig(ApplicationArguments args) {
        this.args = args;
    }

    @Bean
    public ThreadPoolExecutor fixedThreadPool() {
        int nThreads = Integer.parseInt(args.getNonOptionArgs().get(0));
        if(nThreads < 1 || nThreads > 8) {
            throw new IllegalArgumentException("The number of threads must not be less than 1 and greater than 8");
        }
        return (ThreadPoolExecutor) Executors.newFixedThreadPool(nThreads);
    }
}
