package com.example.addressesSpringVersion;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@Disabled
@SpringBootTest
class AddressesSpringVersionApplicationTests {

	@SpyBean
	private AppRunner appRunner;

	@Test
	void contextLoads() throws IOException, InterruptedException {
		verify(appRunner, times(1)).run(any());
	}

}
