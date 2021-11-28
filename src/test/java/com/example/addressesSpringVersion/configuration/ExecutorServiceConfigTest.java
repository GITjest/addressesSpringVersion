package com.example.addressesSpringVersion.configuration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.ApplicationArguments;

import java.util.Arrays;
import java.util.concurrent.ThreadPoolExecutor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExecutorServiceConfigTest {

    @Mock
    private ApplicationArguments applicationArguments;

    @InjectMocks
    private ExecutorServiceConfig executorServiceConfig;

    @Test
    public void shouldSetFixedThreadPool() {
        when(applicationArguments.getNonOptionArgs()).thenReturn(Arrays.asList("4", "import.csv"));

        ThreadPoolExecutor threadPoolExecutor = executorServiceConfig.fixedThreadPool();

        assertEquals(threadPoolExecutor.getCorePoolSize(), 4);
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenNThreadsIsLessThan1() {
        when(applicationArguments.getNonOptionArgs()).thenReturn(Arrays.asList("0", "import.csv"));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            executorServiceConfig.fixedThreadPool();
        });

        assertEquals(exception.getMessage(), "The number of threads must not be less than 1 and greater than 8");
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenNThreadsIsGreaterThan8() {
        when(applicationArguments.getNonOptionArgs()).thenReturn(Arrays.asList("9", "import.csv"));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            executorServiceConfig.fixedThreadPool();
        });

        assertEquals(exception.getMessage(), "The number of threads must not be less than 1 and greater than 8");
    }
}