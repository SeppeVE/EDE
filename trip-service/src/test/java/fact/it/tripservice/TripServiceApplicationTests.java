package fact.it.tripservice;

import fact.it.tripservice.service.TripService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import fact.it.tripservice.dto.CustomerResponse;
import fact.it.tripservice.dto.TaxiResponse;
import fact.it.tripservice.dto.TripRequest;
import fact.it.tripservice.dto.TripResponse;
import fact.it.tripservice.model.Trip;
import fact.it.tripservice.repository.TripRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class TripServiceApplicationTests {

	@Mock
	private TripRepository tripRepository;

	@Mock
	private WebClient webClient;

	@InjectMocks
	private TripService tripService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void getAllTrips() {
		// Mocking the behavior of tripRepository.findAll()
		when(tripRepository.findAll()).thenReturn(Collections.singletonList(createSampleTrip()));

		// Calling the method to be tested
		var result = tripService.getAllTrips();

		// Asserting the result
		assertEquals(1, result.size());
		assertEquals("1234", result.get(0).getTripNr());
	}

	@Test
	void getTripById() {
		// Mocking the behavior of tripRepository.findTripById()
		when(tripRepository.findTripById(any())).thenReturn(Optional.of(createSampleTrip()));

		// Calling the method to be tested
		var result = tripService.getTripById("1");

		// Asserting the result
		assertNotNull(result);
		assertEquals("1234", result.getTripNr());
	}

	@Test
	void callTrip_Success() {
		// Mocking the behavior of webClient calls
		when(webClient.get()).thenReturn(webClient);
		when(webClient.uri(any(), any())).thenReturn(webClient);
		when(webClient.retrieve()).thenReturn(webClient);
		when(webClient.bodyToMono(TaxiResponse[].class)).thenReturn(Mockito.just(new TaxiResponse[]{new TaxiResponse(true, "1-JVY-048", 10)}));
		when(webClient.bodyToMono(CustomerResponse[].class)).thenReturn(Mockito.just(new CustomerResponse[]{new CustomerResponse("420", "John", "Doe")}));

		// Mocking the behavior of tripRepository.save()
		when(tripRepository.save(any())).thenReturn(createSampleTrip());

		// Calling the method to be tested
		var result = tripService.callTrip(createSampleTripRequest());

		// Asserting the result
		assertTrue(result);
	}

	@Test
	void callTrip_Failure() {
		// Mocking the behavior of webClient calls
		when(webClient.get()).thenReturn(webClient);
		when(webClient.uri(any(), any())).thenReturn(webClient);
		when(webClient.retrieve()).thenReturn(webClient);
		when(webClient.bodyToMono(TaxiResponse[].class)).thenReturn(Mockito.just(new TaxiResponse[]{new TaxiResponse(false, "1-JVY-048", 10)}));

		// Calling the method to be tested
		var result = tripService.callTrip(createSampleTripRequest());

		// Asserting the result
		assertFalse(result);
	}

	@Test
	void deleteTrip_Success() {
		// Mocking the behavior of tripRepository.findTripById() and tripRepository.deleteById()
		when(tripRepository.findTripById(any())).thenReturn(Optional.of(createSampleTrip()));

		// Calling the method to be tested
		var result = tripService.deleteTrip("1");

		// Asserting the result
		assertTrue(result);
	}

	@Test
	void deleteTrip_Failure() {
		// Mocking the behavior of tripRepository.findTripById() for a non-existing trip
		when(tripRepository.findTripById(any())).thenReturn(Optional.empty());

		// Calling the method to be tested
		var result = tripService.deleteTrip("1");

		// Asserting the result
		assertFalse(result);
	}

	private Trip createSampleTrip() {
		Trip trip = new Trip();
		trip.setTripNr("1234");
		trip.setLicencePlate("1-JVY-048");
		trip.setCustomerNr("420");
		trip.setFirstNameCustomer("John");
		trip.setLastNameCustomer("Doe");
		trip.setPricePerKm(5);
		trip.setLengthInKm(10);
		trip.setPrice(BigDecimal.valueOf(50));
		// set other properties...
		return trip;
	}

	private TripRequest createSampleTripRequest() {
		TripRequest tripRequest = new TripRequest();
		tripRequest.setLicencePlate("1-JVY-048");
		tripRequest.setCustomerNr("420");
		tripRequest.setFirstNameCustomer("John");
		tripRequest.setLastNameCustomer("Doe");
		tripRequest.setPricePerKm(5);
		tripRequest.setLengthInKm(10);
		tripRequest.setPrice(BigDecimal.valueOf(50));
		return tripRequest;
	}


}
