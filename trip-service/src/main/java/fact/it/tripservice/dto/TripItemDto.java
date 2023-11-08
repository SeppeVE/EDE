package fact.it.tripservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripItemDto {
    private Long id;
    private String tripItemNr;
    private double lengthInKm;
    private BigDecimal price;
}
