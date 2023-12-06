package fact.it.tripservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TripResponse {
    private String tripNr;
    private double lengthInKm;
    private BigDecimal price;
    private String licencePlate;
    private double pricePerKm;
    private String customerNr;
    private String customerName;
}
