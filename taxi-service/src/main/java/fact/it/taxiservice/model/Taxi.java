package fact.it.taxiservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "taxi")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Taxi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String licencePlate;
    private String brand;
    private boolean isAvailable;
    private double pricePerKm;
}
