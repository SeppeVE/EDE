package fact.it.tripservice.service;

import fact.it.tripservice.dto.CustomerResponse;
import fact.it.tripservice.dto.TaxiResponse;
import fact.it.tripservice.dto.TripRequest;
import fact.it.tripservice.dto.TripResponse;
import fact.it.tripservice.model.Trip;
import fact.it.tripservice.repository.TripRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class TripService {
    private final TripRepository tripRepository;
    private final WebClient webClient;

    @Value("${taxiservice.baseurl}")
    private String taxiServiceBaseUrl;

    @Value("${customerservice.baseurl}")
    private String customerServiceBaseUrl;


    public List<TripResponse> getAllTrips(){
        List<Trip> trips = tripRepository.findAll();

        return trips.stream().map(this::mapToTripResponse).toList();
    }


    @PostConstruct
    public void loadData() {
        tripRepository.deleteAll();
        if (tripRepository.count() <= 0) {
            Trip trip = new Trip();
            trip.setTripNr("1234");
            trip.setCustomerNr("144");
            trip.setLastNameCustomer("Vermeulen");
            trip.setFirstNameCustomer("Joske");
            trip.setLicencePlate("1-abc-048");
            trip.setPricePerKm(5);
            trip.setLengthInKm(5);
            trip.setPrice(BigDecimal.valueOf(25));

            tripRepository.save(trip);
        }

    }


    public boolean callTrip(TripRequest tripRequest) {
        // Validate input parameters
        if (tripRequest.getLengthInKm() <= 0 || tripRequest.getLicencePlate().isEmpty() || tripRequest.getCustomerNr().isEmpty()) {
            // Handle invalid input parameters
            return false;
        }

        Trip trip = new Trip();
        trip.setTripNr(UUID.randomUUID().toString());

        // Retrieve taxi information
        TaxiResponse[] taxiAr = getTaxiInformation(tripRequest.getLicencePlate());

        // Retrieve customer information
        CustomerResponse[] customerAr = getCustomerInformation(tripRequest.getCustomerNr());

        // Check taxi availability
        boolean isAvailable = Arrays.stream(taxiAr).allMatch(TaxiResponse::isAvailable);

        if (isAvailable) {
            setTripDetails(trip, tripRequest, taxiAr, customerAr);
            tripRepository.save(trip);
            return true;
        } else {
            // Handle unavailable taxis
            return false;
        }
    }

    private TaxiResponse[] getTaxiInformation(String licencePlate) {
        return webClient.get()
                .uri("http://" + taxiServiceBaseUrl + "/api/taxi", uriBuilder ->
                        uriBuilder.queryParam("licencePlate", licencePlate)
                                .build())
                .retrieve()
                .bodyToMono(TaxiResponse[].class)
                .block();
    }

    private CustomerResponse[] getCustomerInformation(String customerNr) {
        return webClient.get()
                .uri("http://" + customerServiceBaseUrl + "/api/customer", uriBuilder ->
                        uriBuilder.queryParam("customerNr", customerNr)
                                .build())
                .retrieve()
                .bodyToMono(CustomerResponse[].class)
                .block();
    }

    private void setTripDetails(Trip trip, TripRequest tripRequest, TaxiResponse[] taxiAr, CustomerResponse[] customerAr) {
        Arrays.stream(taxiAr)
                .filter(e -> e.getLicencePlate().equals(tripRequest.getLicencePlate()))
                .findFirst()
                .ifPresent(taxi -> trip.setPricePerKm(taxi.getPricePerKm()));

        CustomerResponse customer = Arrays.stream(customerAr)
                .filter(s -> s.getCustomerNr().equals(tripRequest.getCustomerNr()))
                .findFirst()
                .orElse(null);

        if (customer != null) {
            trip.setFirstNameCustomer(customer.getFirstName());
            trip.setLastNameCustomer(customer.getLastName());
        }

        trip.setLengthInKm(tripRequest.getLengthInKm());
    }


    private TripResponse mapToTripResponse(Trip trip) {
        return TripResponse.builder()
                .tripNr(trip.getTripNr())
                .licencePlate(trip.getLicencePlate())
                .lengthInKm(trip.getLengthInKm())
                .pricePerKm(trip.getPricePerKm())
                .price(BigDecimal.valueOf(trip.getPricePerKm() * trip.getLengthInKm()))
                .customerNr(trip.getCustomerNr())
                .customerName(trip.getFirstNameCustomer() + ' ' + trip.getLastNameCustomer())
                .build();
    }
}
