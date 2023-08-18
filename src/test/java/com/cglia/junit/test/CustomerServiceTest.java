package com.cglia.junit.test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import com.cglia.junit.model.Customer;
import com.cglia.junit.repository.CustomerRepository;
import com.cglia.junit.service.CustomerService;

@SpringBootTest
class CustomerServiceTest {
	@Mock
	private CustomerRepository customerRepository;

	@InjectMocks
	private CustomerService customerService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	
	void testListAllCustomers() {
		Customer customer1 = new Customer(1, "John", "john@example.com", 1234567890L);
		Customer customer2 = new Customer(2, "Jane", "jane@example.com", 9876543210L);

		when(customerRepository.findAll()).thenReturn(Arrays.asList(customer1, customer2));

		List<Customer> customers = customerService.listAll();
		System.out.println("Number of customers: " + customers.size());
	    System.out.println("Customer 1 name: " + customers.get(0).getName());
	    System.out.println("Customer 2 name: " + customers.get(1).getName());

		assertEquals(2, customers.size());
		assertEquals("John", customers.get(0).getName());
		assertEquals("Jane", customers.get(1).getName());
	}

	 @Test
	    void testSaveCustomer() {
		 System.out.println("Running testSaveCustomer...");
	        Customer customerToSave = new Customer(3, "Randon", "randon@example.com", 8775436201L);
	        when(customerRepository.save(customerToSave)).thenReturn(customerToSave);

	        customerService.save(customerToSave);
	        System.out.println("testSaveCustomer completed.");
	    }

	@Test
	void testDeleteCustomer() {
		 System.out.println("Running testDeleteCustomer...");
		doNothing().when(customerRepository).deleteById(1);

		assertDoesNotThrow(() -> customerService.delete(1));
		 System.out.println("testDeleteCustomer completed.");
	}

	@Test
	void testUpdateExistingCustomer() {
		 System.out.println("Running testUpdateExistingCustomer...");
		Customer existingCustomer = new Customer(1, "John", "john@example.com", 1234567890L);
		Customer updatedCustomer = new Customer(1, "Henry", "henry@example.com", 9876543210L);

		when(customerRepository.existsById(1)).thenReturn(true);
		when(customerRepository.save(updatedCustomer)).thenReturn(updatedCustomer);

		Customer result = customerService.updateCustomer(1, updatedCustomer);

		assertNotNull(result);
		assertEquals("Henry", result.getName());
		   System.out.println("testUpdateExistingCustomer completed.");
	}

	@Test
	void testUpdateNonexistentCustomer() {
		Customer updatedCustomer = new Customer(1, "Updated", "updated@example.com", 9876543210L);

		when(customerRepository.existsById(1)).thenReturn(false);

		Customer result = customerService.updateCustomer(1, updatedCustomer);

		assertNull(result);
	}

	@Test
	void testGetCustomerById() {
		Customer customer = new Customer(1, "John", "john@example.com", 1234567890L);
		when(customerRepository.findById(1)).thenReturn(Optional.of(customer));

		Customer result = customerService.getCustomerById(1);

		assertNotNull(result);
		assertEquals("John", result.getName());
	}

	@Test
	void testGetNonexistentCustomerById() {
		when(customerRepository.findById(1)).thenReturn(Optional.empty());

		Customer result = customerService.getCustomerById(1);

		assertNull(result);
	}
}
