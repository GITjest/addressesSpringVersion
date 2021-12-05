package com.example.addressesSpringVersion.service;

import com.example.addressesSpringVersion.entity.Location;
import com.example.addressesSpringVersion.geo2.Geo2Request;
import com.example.addressesSpringVersion.repository.LocationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LocationServiceTest {

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private Geo2Request geo2Request;

    @Test
    public void test() {
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(8);
        LocationService locationService = new LocationService(threadPoolExecutor, locationRepository, geo2Request);

        Location location = new Location(52.17403465, 20.9335285023814);
        when(geo2Request.getLocation(anyDouble(), anyDouble())).thenReturn(location);
        when(locationRepository.save(location)).thenReturn(location);

        locationService.saveLocations(List.of(location));
    }

}