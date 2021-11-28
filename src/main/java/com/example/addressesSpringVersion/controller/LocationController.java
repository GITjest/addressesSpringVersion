package com.example.addressesSpringVersion.controller;

import com.example.addressesSpringVersion.entity.Location;
import com.example.addressesSpringVersion.service.LocationService;
import org.springframework.stereotype.Controller;

import java.util.Collection;

@Controller
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    public void saveLocations(Collection<Location> locations) {
        locationService.saveLocations(locations);
    }
}
