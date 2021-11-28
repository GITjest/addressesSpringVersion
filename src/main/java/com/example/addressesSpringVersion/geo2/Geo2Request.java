package com.example.addressesSpringVersion.geo2;

import com.example.addressesSpringVersion.entity.Location;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class Geo2Request {

    private final String API_URL = "http://geo2.osm.gpsserwer.pl/reverse.php?format=json";
    private final Logger logger = LoggerFactory.getLogger(Geo2Request.class);
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public Geo2Request(HttpClient httpClient, ObjectMapper objectMapper) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
    }

    public Location getLocation(Location location) {
        return getLocation(location.getLat(), location.getLon());
    }

    public Location getLocation(double lat, double lon) {
        return get(API_URL + "&lat=" + lat + "&lon=" + lon, Location.class);
    }

    private <T> T get(String url, Class<T> classType) {
        HttpResponse<String> response = sendGetRequest(url);
        if(response == null) return null;
        if(response.statusCode() != 200) return null;
        try {
            return objectMapper.readValue(response.body(), classType);
        } catch (JsonProcessingException e) {
            logger.info(e.getMessage());
            return null;
        }
    }

    private HttpResponse<String> sendGetRequest(String uri) {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .GET()
                .build();
        try {
            return httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            logger.info(e.getMessage());
            return null;
        }
    }
}
