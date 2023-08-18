package com.cglia.junit.test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.cglia.junit.controller.CustomerController;
import com.cglia.junit.model.Customer;
import com.cglia.junit.service.CustomerService;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CustomerService customerService;

	private List<Customer> mockCustomers;

	@BeforeEach
	void setUp() {
		mockCustomers = new ArrayList<>();
		mockCustomers.add(new Customer(1, "Alice", "alice@example.com", 8897096758L));
		mockCustomers.add(new Customer(2, "Bob", "bob@example.com", 9877342210L));
	}

	@Test
	void testListCustomers() throws Exception {
		when(customerService.listAll()).thenReturn(mockCustomers);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/viewcustomers"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(mockCustomers.size()));
	}

	@Test
	void testGetCustomerById() throws Exception {
		int customerId = 1;
		Customer customer = new Customer(customerId, "Alice", "alice@example.com", 8897096758L);
		when(customerService.getCustomerById(customerId)).thenReturn(customer);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/viewcustomers/{id}", customerId))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value(customer.getName()));
	}

	@Test
	void testAddCustomer() throws Exception {
		Customer newCustomer = new Customer(3, "New Customer", "new@example.com", 9877342210L);
		String requestBody = "{\"customerId\": 3, \"customerName\": \"New Customer\", \"email\": \"new@example.com\"}";

		mockMvc.perform(MockMvcRequestBuilders.post("/api/addCustomers").contentType(MediaType.APPLICATION_JSON)
				.content(requestBody)).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void testUpdateCustomer() throws Exception {
		int customerId = 1;
		Customer updatedCustomer = new Customer(customerId, "Henry", "henry@example.com", 8897096758L);
		String requestBody = "{\"customerName\": \"Henry\", \"email\": \"henry@example.com\"}";

		when(customerService.updateCustomer(eq(customerId), any(Customer.class))).thenReturn(updatedCustomer);

		mockMvc.perform(MockMvcRequestBuilders.put("/api/{id}", customerId).contentType(MediaType.APPLICATION_JSON)
				.content(requestBody)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value(updatedCustomer.getName()));
	}

	@Test
	void testDeleteCustomer() throws Exception {
		int customerId = 1;

		mockMvc.perform(MockMvcRequestBuilders.delete("/api/deletecustomers/{id}", customerId))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}
}
