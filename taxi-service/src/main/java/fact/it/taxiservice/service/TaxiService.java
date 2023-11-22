package fact.it.taxiservice.service;

import fact.it.taxiservice.dto.TaxiRequest;
import fact.it.taxiservice.dto.TaxiResponse;
import fact.it.taxiservice.model.Taxi;
import fact.it.taxiservice.repository.TaxiRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaxiService {
    private final TaxiRepository taxiRepository;
    @PostConstruct
    public void loadData(){
        if (taxiRepository.count() == 0){
            Taxi taxi = new Taxi();
            taxi.setLicencePlate("1-abc-123");
            taxi.setBrand("Toyota");
            taxi.setAvailable(false);
            taxi.setPricePerKm(2.00);

            Taxi taxi2 = new Taxi();
            taxi.setLicencePlate("1-def-456");
            taxi.setBrand("Mazda");
            taxi.setAvailable(true);
            taxi.setPricePerKm(4.00);

            taxiRepository.save(taxi);
            taxiRepository.save(taxi2);
        }
    }

    public void createTaxi(TaxiRequest taxiRequest){
        Taxi taxi = Taxi.builder()
                .brand(taxiRequest.getBrand())
                .licencePlate(taxiRequest.getLicencePlate())
                .pricePerKm(taxiRequest.getPricePerKm())
                .isAvailable(taxiRequest.isAvailable())
                .build();

        taxiRepository.save(taxi);
    }


    @Transactional(readOnly = true)
    public List<TaxiResponse> isAvailable(List<String> licencePlate){
        return taxiRepository.findByLicencePlate(licencePlate.toString()).stream()
                .map(taxi -> TaxiResponse.builder()
                        .licencePlate(taxi.getLicencePlate())
                        .isAvailable(taxi.isAvailable())
                        .build()).toList();
    }


    public List<TaxiResponse> getAllTaxis(){
        List<Taxi> taxis = taxiRepository.findAll();
        return taxis.stream().map(this::mapToTaxiResponse).toList();
    }

    private List<TaxiResponse> getAllTaxisByBrand(String brand){
        List<Taxi> taxis = taxiRepository.findAllByBrand(brand);
        return taxis.stream().map(this::mapToTaxiResponse).toList();
    }


    private TaxiResponse mapToTaxiResponse(Taxi taxi){
        return TaxiResponse.builder()
                .id(taxi.getId())
                .brand(taxi.getBrand())
                .licencePlate(taxi.getLicencePlate())
                .pricePerKm(taxi.getPricePerKm())
                .isAvailable(taxi.isAvailable())
                .build();
    }
}
