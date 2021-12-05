package com.example.addressesSpringVersion;

import com.example.addressesSpringVersion.reader.CSVReader;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(args = {"8", "import.csv"})
class AppRunnerTest {

    @Autowired
    private AppRunner appRunner;

    @MockBean
    private CSVReader csvReader;

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenThereAreNoTwoArguments() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            appRunner.run("8");
        });

        assertEquals(exception.getMessage(), "Where parameters?");
    }

}