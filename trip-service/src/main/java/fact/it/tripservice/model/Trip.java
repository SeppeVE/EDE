package fact.it.tripservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "trips")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tripNr;
    private double lengthInKm;
    private BigDecimal price;
    private String licencePlate;
    private double pricePerKm;
    private String customerNr;
    private String firstNameCustomer;
    private String lastNameCustomer;

}
