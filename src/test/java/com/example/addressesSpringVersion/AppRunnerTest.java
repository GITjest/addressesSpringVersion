package com.example.addressesSpringVersion;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
@SpringBootTest
class AppRunnerTest {

    @Autowired
    private AppRunner appRunner;

    @Test
    public void testRunApplication() throws IOException, InterruptedException {
        appRunner.run("8", "import.csv");

    }

}