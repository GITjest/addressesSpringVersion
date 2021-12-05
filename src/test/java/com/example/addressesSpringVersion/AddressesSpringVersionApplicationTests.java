package com.example.addressesSpringVersion;

import com.example.addressesSpringVersion.controller.LocationController;
import com.example.addressesSpringVersion.entity.Location;
import com.example.addressesSpringVersion.geo2.Geo2Request;
import com.example.addressesSpringVersion.reader.CSVReader;
import com.example.addressesSpringVersion.repository.LocationRepository;
import com.example.addressesSpringVersion.service.LocationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.io.IOException;
import java.io.Reader;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(args = {"8", "import.csv"})
@ExtendWith(MockitoExtension.class)
class AddressesSpringVersionApplicationTests {

	@SpyBean
	private AppRunner appRunner;

	@SpyBean
	private CSVReader csvReader;

	@SpyBean
	private LocationController locationController;

	@SpyBean
	private LocationService locationService;

	@SpyBean
	private Geo2Request geo2Request;

	@SpyBean
	private LocationRepository locationRepository;

	@Test
	void contextLoads() throws IOException, InterruptedException {
		verify(appRunner, times(1)).run(any());
		verify(csvReader, times(1)).read(any(Reader.class));
		verify(locationController, times(1)).saveLocations(anyCollection());
		verify(locationService, times(1)).saveLocations(anyCollection());
		verify(geo2Request, times(18)).getLocation(anyDouble(), anyDouble());
		verify(locationRepository, times(18)).save(any(Location.class));
	}

}
