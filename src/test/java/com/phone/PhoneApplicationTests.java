package com.phone;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class PhoneApplicationTests {

	@Test
	void contextLoads() {
		// This test ensures that the Spring application context loads successfully
	}

	@Test
	void main() {
		// This test ensures that the main method runs without throwing any exceptions
		assertDoesNotThrow(() -> PhoneApplication.main(new String[] {}));
	}
}
