package com.example.addressesSpringVersion.geo2;

import com.example.addressesSpringVersion.entity.Location;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class Geo2RequestTest {

    @Mock
    private HttpResponse<String> httpResponse;

    @Mock
    private ObjectMapper objectMapper;

    @Spy
    private HttpClient httpClient;

    @InjectMocks
    private Geo2Request geo2Request;

    private String responseBody;
    private Location location;

    @BeforeEach
    public void setUp() throws JsonProcessingException {
        responseBody = "{\n" +
                "  \"place_id\": \"81398445\",\n" +
                "  \"licence\": \"Data © OpenStreetMap contributors, ODbL 1.0. http://www.openstreetmap.org/copyright\",\n" +
                "  \"osm_type\": \"way\",\n" +
                "  \"osm_id\": \"316135048\",\n" +
                "  \"lat\": \"52.1760099\",\n" +
                "  \"lon\": \"23.9488437\",\n" +
                "  \"display_name\": \"Н148, Стеброво, Жабинковский район, Брестская область, 225102, Беларусь\",\n" +
                "  \"address\": {\n" +
                "    \"road\": \"Н148\",\n" +
                "    \"hamlet\": \"Стеброво\",\n" +
                "    \"county\": \"Жабинковский район\",\n" +
                "    \"state\": \"Брестская область\",\n" +
                "    \"postcode\": \"225102\",\n" +
                "    \"country\": \"Беларусь\",\n" +
                "    \"country_code\": \"by\"\n" +
                "  }\n" +
                "}";

        ObjectMapper objectMapper = new ObjectMapper();
        location = objectMapper.readValue(responseBody, Location.class);
    }

    @Test
    public void shouldReturnLocationWhenStatusIs200() throws IOException, InterruptedException {
        when(httpClient.send(any(), any(HttpResponse.BodyHandlers.ofString().getClass()))).thenReturn(httpResponse);
        when(httpResponse.statusCode()).thenReturn(200);
        when(httpResponse.body()).thenReturn(responseBody);
        when(objectMapper.readValue(anyString(), eq(Location.class))).thenReturn(location);

        Location returnLocation = geo2Request.getLocation(52.1760099, 23.9488437);

        assertEquals(location, returnLocation);
    }

    @Test
    public void shouldReturnNullWhenStatusIsNot200() throws IOException, InterruptedException {
        when(httpClient.send(any(), any(HttpResponse.BodyHandlers.ofString().getClass()))).thenReturn(httpResponse);
        when(httpResponse.statusCode()).thenReturn(500);

        Location returnLocation = geo2Request.getLocation(52.1760099, 23.9488437);

        assertNull(returnLocation);
    }

    @Test
    public void shouldReturnNullWhenResponseIsNull() throws IOException, InterruptedException {
        when(httpClient.send(any(), any(HttpResponse.BodyHandlers.ofString().getClass()))).thenReturn(null);

        Location returnLocation = geo2Request.getLocation(52.1760099, 23.9488437);

        assertNull(returnLocation);
    }

    @Test
    public void shouldReturnNullWhenJsonProcessingException() throws IOException, InterruptedException {
        when(httpClient.send(any(), any(HttpResponse.BodyHandlers.ofString().getClass()))).thenReturn(httpResponse);
        when(httpResponse.statusCode()).thenReturn(200);
        when(httpResponse.body()).thenReturn(responseBody);
        when(objectMapper.readValue(anyString(), eq(Location.class))).thenThrow(JsonProcessingException.class);

        Location returnLocation = geo2Request.getLocation(52.1760099, 23.9488437);

        assertNull(returnLocation);
    }

    @Test
    public void shouldReturnNullWhenIOException() throws IOException, InterruptedException {
        when(httpClient.send(any(), any(HttpResponse.BodyHandlers.ofString().getClass()))).thenThrow(IOException.class);

        Location returnLocation = geo2Request.getLocation(52.1760099, 23.9488437);

        assertNull(returnLocation);
    }

    @Test
    public void shouldReturnNullWhenInterruptedException() throws IOException, InterruptedException {
        when(httpClient.send(any(), any(HttpResponse.BodyHandlers.ofString().getClass()))).thenThrow(InterruptedException.class);

        Location returnLocation = geo2Request.getLocation(52.1760099, 23.9488437);

        assertNull(returnLocation);
    }

}