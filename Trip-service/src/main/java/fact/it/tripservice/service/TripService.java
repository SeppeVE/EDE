package fact.it.tripservice.service;

import fact.it.tripservice.dto.*;
import fact.it.tripservice.model.Trip;
import fact.it.tripservice.repository.TripRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TripService {
    private final TripRepository tripRepository;
    private final WebClient webClient;


    public List<TripResponse> getAllTrips(){
        List<Trip> trips = tripRepository.findAll();

        return trips.stream().map(this::mapToTripResponse).toList();
    }

    public TripResponse getTripById(String tripId) {
        Trip trip = tripRepository.findTripById(Long.valueOf(tripId));
        return mapToTripResponse(trip);
    }

    @PostConstruct
    public void loadData() {
        tripRepository.deleteAll();
        if (tripRepository.count() <= 0) {
            Trip trip = new Trip();
            trip.setTripNr("1234");
            trip.setCustomerNr("420");
            trip.setLastNameCustomer("Doe");
            trip.setFirstNameCustomer("John");
            trip.setLicencePlate("1-JVY-048");
            trip.setPricePerKm(5);
            trip.setLengthInKm(5);
            trip.setPrice(BigDecimal.valueOf(25));

            tripRepository.save(trip);
        }

    }


    public boolean callTrip(TripRequest tripRequest) {
        Trip trip = new Trip();
        trip.setTripNr(UUID.randomUUID().toString());

        String licencePlate = tripRequest.getLicencePlate();

        TaxiResponse[] taxiAr = webClient.get()
                .uri("http://localhost:8082/api/taxi", uriBuilder -> uriBuilder.queryParam("licencePlate", licencePlate)
                        .build())
                .retrieve()
                .bodyToMono(TaxiResponse[].class)
                .block();

        String customerNr = tripRequest.getCustomerNr();

        CustomerResponse[] customerAr = webClient.get()
                .uri("http://localhost:8081/api/customer", uriBuilder -> uriBuilder.queryParam("customerNr", customerNr)
                        .build())
                .retrieve()
                .bodyToMono(CustomerResponse[].class)
                .block();

        boolean isAvailable = Arrays.stream(taxiAr).allMatch(TaxiResponse::isAvailable);

        if (isAvailable){
            TaxiResponse taxi = Arrays.stream(taxiAr)
                    .filter(e -> e.getLicencePlate().equals(tripRequest.getLicencePlate()))
                    .findFirst()
                    .orElse(null);
            if (taxi != null) {
                trip.setPricePerKm(taxi.getPricePerKm());
            }
            CustomerResponse customer = Arrays.stream(customerAr)
                    .filter(s -> s.getCustomerNr().equals(tripRequest.getCustomerNr()))
                    .findFirst()
                    .orElse(null);
            if (customer != null) {
                trip.setFirstNameCustomer(customer.getFirstName());
                trip.setLastNameCustomer(customer.getLastName());
            }

            tripRepository.save(trip);
            return true;

        } else {

            return false;
        }

    }

    public boolean deleteTrip(String tripId) {
        Trip trip = tripRepository.findTripById(Long.valueOf(tripId));
        if(trip != null) {
            tripRepository.deleteById(trip.getId());
            return true;
        } else {
            return false;
        }
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
