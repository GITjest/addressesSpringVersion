package com.example.addressesSpringVersion;

import com.example.addressesSpringVersion.controller.LocationController;
import com.example.addressesSpringVersion.reader.CSVReader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.ThreadPoolExecutor;

@Component
public class AppRunner implements CommandLineRunner {

    private final ThreadPoolExecutor threadPoolExecutor;
    private final LocationController locationController;
    private final CSVReader csvReader;

    public AppRunner(ThreadPoolExecutor threadPoolExecutor, LocationController locationController, CSVReader csvReader) {
        this.threadPoolExecutor = threadPoolExecutor;
        this.locationController = locationController;
        this.csvReader = csvReader;
    }

    @Override
    public void run(String... args) throws InterruptedException, IOException {
        if(args.length < 2) {
            throw new IllegalArgumentException("Where parameters?");
        }

        locationController.saveLocations(csvReader.read(args[1]));
        threadPoolExecutor.shutdown();

        Thread.sleep(1000);
    }
}
