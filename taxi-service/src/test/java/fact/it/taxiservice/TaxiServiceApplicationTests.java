package fact.it.taxiservice;

import fact.it.taxiservice.dto.TaxiRequest;
import fact.it.taxiservice.dto.TaxiResponse;
import fact.it.taxiservice.model.Taxi;
import fact.it.taxiservice.repository.TaxiRepository;
import fact.it.taxiservice.service.TaxiService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaxiServiceApplicationTests {

    @Mock
    private TaxiRepository taxiRepository;

    @InjectMocks
    private TaxiService taxiService;

    @Test
    void createTaxiTest() {
        TaxiRequest taxiRequest = new TaxiRequest("2-xyz-456", "Honda", true, 3.50);

        taxiService.createTaxi(taxiRequest);

        verify(taxiRepository, times(1)).save(any(Taxi.class));
    }

    @Test
    void updateTaxiTest() {
        Long taxiId = 1L;
        TaxiRequest updatedTaxiRequest = new TaxiRequest("1-ghi-789", "Hyundai", false, 5.00);

        Taxi existingTaxi = new Taxi();
        existingTaxi.setId(taxiId);

        when(taxiRepository.findById(taxiId)).thenReturn(Optional.of(existingTaxi));

        taxiService.updateTaxi(taxiId, updatedTaxiRequest);

        assertEquals("Hyundai", existingTaxi.getBrand());
        assertEquals("1-ghi-789", existingTaxi.getLicencePlate());
        assertEquals(5.00, existingTaxi.getPricePerKm());
        assertFalse(existingTaxi.isAvailable());

        verify(taxiRepository, times(1)).save(existingTaxi);
    }

    @Test
    void updateTaxiNotFoundTest() {
        Long taxiId = 1L;
        TaxiRequest updatedTaxiRequest = new TaxiRequest("1-ghi-789", "Hyundai", false, 5.00);

        when(taxiRepository.findById(taxiId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> taxiService.updateTaxi(taxiId, updatedTaxiRequest));

        verify(taxiRepository, never()).save(any(Taxi.class));
    }

    @Test
    void explicitBuilderTest() {
        Taxi taxi = Taxi.builder()
                .brand("Toyota")
                .licencePlate("1-abc-123")
                .pricePerKm(2.00)
                .isAvailable(false)
                .build();

        assertNotNull(taxi);
        assertEquals("Toyota", taxi.getBrand());
        assertEquals("1-abc-123", taxi.getLicencePlate());
        assertEquals(2.00, taxi.getPricePerKm());
        assertFalse(taxi.isAvailable());
    }

    @Test
    void isAvailableTest() {
        List<String> licencePlates = Arrays.asList("1-abc-123", "1-xyz-048");

        when(taxiRepository.findByLicencePlate(any())).thenReturn(Arrays.asList(
                Taxi.builder().brand("Toyota").licencePlate("1-abc-123").pricePerKm(2.00).isAvailable(false).build(),
                Taxi.builder().brand("Mazda").licencePlate("1-xyz-048").pricePerKm(4.00).isAvailable(true).build()
        ));

        List<TaxiResponse> taxiResponses = taxiService.isAvailable(licencePlates);

        assertEquals(2, taxiResponses.size());
        assertTrue(taxiResponses.get(1).isAvailable());
        assertFalse(taxiResponses.get(0).isAvailable());
    }

    @Test
    void getAllTaxisTest() {
        when(taxiRepository.findAll()).thenReturn(Arrays.asList(
                Taxi.builder().brand("Toyota").licencePlate("1-abc-123").pricePerKm(2.00).isAvailable(false).build(),
                Taxi.builder().brand("Mazda").licencePlate("1-xyz-048").pricePerKm(4.00).isAvailable(true).build()
        ));

        List<TaxiResponse> taxiResponses = taxiService.getAllTaxis();

        assertEquals(2, taxiResponses.size());
        assertEquals("Toyota", taxiResponses.get(0).getBrand());
        assertEquals("Mazda", taxiResponses.get(1).getBrand());
    }

}
