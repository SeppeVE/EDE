package fact.it.customerservice.controller;

import fact.it.customerservice.dto.CustomerRequest;
import fact.it.customerservice.dto.CustomerResponse;
import fact.it.customerservice.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void createCustomer
            (@RequestBody CustomerRequest customerRequest){
        customerService.createCustomer(customerRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerResponse> getAllCustomers(){
        return customerService.getAllCustomers();
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateCustomer(@PathVariable("id") String customerId,@RequestBody CustomerRequest customerRequest)
    {
        customerService.updateCustomer(customerId, customerRequest);
    }

}
