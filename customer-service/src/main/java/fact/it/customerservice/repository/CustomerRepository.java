package fact.it.customerservice.repository;

import fact.it.customerservice.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends MongoRepository<Customer, String> {
    List<Customer> findByCustomerNr(String customerNr);

    Optional<Customer> getByCustomerNr(String nr);
}
