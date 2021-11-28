package com.example.addressesSpringVersion.reader;

import com.example.addressesSpringVersion.entity.Location;;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CSVReaderTest {

    @InjectMocks
    private CSVReader csvReader;

    @Mock
    private BufferedReader bufferedReader;

    @Test
    public void shouldReturnLocations() throws IOException {
        String records = "52,17403465;20,9335285023814\n" +
                "52,174310;25,21\n" +
                "52,17403465;20,9335285023814";

        Collection<Location>  locations = new ArrayList<>(Arrays.asList(
                new Location(52.17403465,20.9335285023814),
                new Location(52.174310,25.21),
                new Location(52.17403465, 20.9335285023814)
        ));

        Collection<Location> returnLocations = csvReader.read(new StringReader(records));

        assertEquals(locations, returnLocations);
    }

    @Test
    public void shouldNotAddLocationWhenNumberFormatException() throws IOException {
        String records = "52,17403465;20.9335285023814\n" +
                "asdasd;asdasd\n" +
                "5217403465209335285023814\n" +
                "52,17403465;20,9335285,023814";

        Collection<Location> locations = new ArrayList<>(List.of(
                new Location(52.17403465, 20.9335285023814)
        ));

        Collection<Location> returnLocations = csvReader.read(new StringReader(records));

        assertEquals(locations, returnLocations);
    }

    @Test
    public void shouldThrowIOExceptionWhenFileNameIsInvalid() {
        assertThrows(IOException.class, () -> {
            csvReader.read("");
        });
    }

}