package fact.it.tripservice.repository;

import fact.it.tripservice.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface TripRepository extends JpaRepository<Trip, Long> {
    Trip findTripById(Long id);
}
