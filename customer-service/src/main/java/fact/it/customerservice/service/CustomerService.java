package fact.it.customerservice.service;

import fact.it.customerservice.dto.CustomerRequest;
import fact.it.customerservice.dto.CustomerResponse;
import fact.it.customerservice.model.Customer;
import fact.it.customerservice.repository.CustomerRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    @PostConstruct
    public void loadData(){
        if (customerRepository.count() == 0){
            Customer customer1 = new Customer();
            customer1.setCustomerNr("144");
            customer1.setFirstName("Joske");
            customer1.setLastName("Vermeulen");
            customer1.setEmail("zoneke@vtm.be");

            Customer customer2 = new Customer();
            customer2.setCustomerNr("1");
            customer2.setFirstName("Balthasar");
            customer2.setLastName("Boma");
            customer2.setEmail("pdg@bomavleesindustrienv.be");

            customerRepository.save(customer1);
            customerRepository.save(customer2);

        }
    }

    public void createCustomer(CustomerRequest customerRequest){
        Customer customer = Customer.builder()
                .customerNr(customerRequest.getCustomerNr())
                .firstName(customerRequest.getFirstName())
                .lastName(customerRequest.getLastName())
                .email(customerRequest.getEmail())
                .build();
        customerRepository.save(customer);
    }

    public void updateCustomer (String nr,CustomerRequest customerRequest){
        Optional<Customer> customerEdit = customerRepository.getByCustomerNr(nr);
        if(customerEdit.isPresent()){
            Customer customer = customerEdit.get();
            customer.setFirstName(customerRequest.getFirstName());
            customer.setLastName(customerRequest.getLastName());
            customer.setEmail(customerRequest.getLastName());
            customer.setCustomerNr(customerRequest.getLastName());
            customerRepository.save(customer);

        }
    }


    private CustomerResponse mapToCustomerResponse(Customer customer){
        return CustomerResponse.builder()
                .id(customer.getId())
                .customerNr(customer.getCustomerNr())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .build();
    }

    public List<CustomerResponse> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream().map(this::mapToCustomerResponse).toList();
    }
}
