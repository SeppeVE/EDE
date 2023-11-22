package fact.it.taxiservice.repository;

import fact.it.taxiservice.model.Taxi;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface TaxiRepository extends JpaRepository<Taxi, Long> {
    List<Taxi> findByLicencePlate(String licencePlate);
    List<Taxi> findAllByBrand(String brand);
}
