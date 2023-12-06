package fact.it.tripservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripRequest {
    private double lengthInKm;
    private BigDecimal price;
    private String licencePlate;
    private String customerNr;
}
