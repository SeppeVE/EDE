package fact.it.customerservice.service;

import fact.it.customerservice.dto.CustomerRequest;
import fact.it.customerservice.dto.CustomerResponse;
import fact.it.customerservice.model.Customer;
import fact.it.customerservice.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public void createCustomer(CustomerRequest customerRequest){
        Customer customer = Customer.builder()
                .customerNr(customerRequest.getCustomerNr())
                .firstName(customerRequest.getFirstName())
                .lastName(customerRequest.getLastName())
                .email(customerRequest.getEmail())
                .build();
        customerRepository.save(customer);
    }

    public List<CustomerResponse> getAllCustomersByCustomerNr(List<String> customerNr){
        List<Customer> customers = customerRepository.findByCustomerNr(customerNr.toString());

        return customers.stream().map(this::mapToCustomerResponse).toList();
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
