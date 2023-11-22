package fact.it.taxiservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaxiRequest {
    private String licencePlate;
    private String brand;
    private boolean isAvailable;
    private double pricePerKm;
}
