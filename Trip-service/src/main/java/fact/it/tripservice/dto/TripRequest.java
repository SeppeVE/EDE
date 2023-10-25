package fact.it.tripservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripRequest {
    private String tripNr;
    private double lengthInKm;
    private BigDecimal price;
    private String licencePlate;
    private double pricePerKm;
    private String customerNr;
}
