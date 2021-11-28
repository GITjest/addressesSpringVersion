package com.example.addressesSpringVersion.service;

import com.example.addressesSpringVersion.entity.Location;
import com.example.addressesSpringVersion.geo2.Geo2Request;
import com.example.addressesSpringVersion.repository.LocationRepository;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.concurrent.ThreadPoolExecutor;

@Service
@Transactional
public class LocationService {

    private final ThreadPoolExecutor threadPoolExecutor;
    private final LocationRepository locationRepository;
    private final Geo2Request geo2Request;

    public LocationService(ThreadPoolExecutor threadPoolExecutor, LocationRepository locationRepository, Geo2Request geo2Request) {
        this.threadPoolExecutor = threadPoolExecutor;
        this.locationRepository = locationRepository;
        this.geo2Request = geo2Request;
    }

    public void saveLocations(Collection<Location> locations) {
        Observable.fromIterable(locations)
                .flatMap(location ->
                        Observable.just(location)
                                .map(geo2Request::getLocation)
                                .subscribeOn(Schedulers.from(threadPoolExecutor))
                                .observeOn(Schedulers.single())
                                .map(locationRepository::save)
                ).subscribe();
    }
}
