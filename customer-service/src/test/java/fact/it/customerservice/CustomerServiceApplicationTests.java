package fact.it.customerservice;

import fact.it.customerservice.dto.CustomerRequest;
import fact.it.customerservice.dto.CustomerResponse;
import fact.it.customerservice.model.Customer;
import fact.it.customerservice.repository.CustomerRepository;
import fact.it.customerservice.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceApplicationTests {
	@Mock
	private CustomerRepository customerRepository;


	@InjectMocks
	private CustomerService customerService;

	@Test
	public void createCustomerTest() {
		CustomerRequest customerRequest = new CustomerRequest();
		customerRequest.setCustomerNr("123");
		customerRequest.setFirstName("John");
		customerRequest.setLastName("Doe");
		customerRequest.setEmail("johndoe@example.com");

		customerService.createCustomer(customerRequest);

		verify(customerRepository, times(1)).save(any(Customer.class));
	}

	@Test
	public void testGetAllProducts() {
		// Arrange
		Customer customer = new Customer();
		customer.setId("1");
		customer.setCustomerNr("123");
		customer.setFirstName("John");
		customer.setLastName("Doe");
		customer.setEmail("johndoe@example.com");

		when(customerRepository.findAll()).thenReturn(List.of(customer));

		// Act
		List<CustomerResponse> customers = customerService.getAllCustomers();

		// Assert
		assertEquals(1, customers.size());
		assertEquals("123", customers.get(0).getCustomerNr());
		assertEquals("John", customers.get(0).getFirstName());
		assertEquals("Doe", customers.get(0).getLastName());
		assertEquals("johndoe@example.com", customers.get(0).getEmail());

		verify(customerRepository, times(1)).findAll();
	}

	@Test
	public void testUpdateCustomer() {
		// Arrange
		String id = "1";
		CustomerRequest customerRequest = new CustomerRequest();
		customerRequest.setCustomerNr("12");
		customerRequest.setFirstName("John");
		customerRequest.setLastName("Doe");
		customerRequest.setEmail("johndoe@example.com");

		Customer customer = new Customer();
		customer.setCustomerNr("12");
		customer.setFirstName("Balthasar");
		customer.setLastName("Boma");
		customer.setEmail("pdg@bomavleesindustrienv.be");

		when(customerRepository.findById(id)).thenReturn(Optional.of(customer));

		// Act
		customerService.updateCustomer(id, customerRequest);

		// Assert
		verify(customerRepository, times(1)).findById(id);
		verify(customerRepository, times(1)).save(customer);
		assertEquals(customerRequest.getCustomerNr(), customer.getCustomerNr());
		assertEquals(customerRequest.getFirstName(), customer.getFirstName());
		assertEquals(customerRequest.getLastName(), customer.getLastName());
		assertEquals(customerRequest.getEmail(), customer.getEmail());
	}

	@Test
	public void testDeleteCustomer() {
		// Arrange
		String customerIdToDelete = "1";
		Customer customerToDelete = new Customer();
		customerToDelete.setId(customerIdToDelete);
		customerToDelete.setCustomerNr("123");
		customerToDelete.setFirstName("John");
		customerToDelete.setLastName("Doe");
		customerToDelete.setEmail("john.doe@example.com");

		// Mock the repository to return a customer when findById is called
		Mockito.when(customerRepository.findById(customerIdToDelete)).thenReturn(Optional.of(customerToDelete));

		// Act
		customerService.deleteCustomer(customerIdToDelete);

		// Assert
		// Verify that delete method on repository was called with the expected customer
		Mockito.verify(customerRepository).delete(customerToDelete);
	}

}
