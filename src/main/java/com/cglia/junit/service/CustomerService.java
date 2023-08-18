package com.cglia.junit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cglia.junit.model.Customer;
import com.cglia.junit.repository.CustomerRepository;

@Service
public class CustomerService {
	@Autowired
	private CustomerRepository repo;

	public List<Customer> listAll() {
		return repo.findAll();
	}

	public void save(Customer customer) {
		repo.save(customer);
	}

	public void delete(Integer id) {
		repo.deleteById(id);

	}
	 public Customer updateCustomer(Integer id, Customer customer) {
	        if (repo.existsById(id)) {
	            customer.setId(id);
	            return repo.save(customer);
	        }
	        return null;
	    }

	  public Customer getCustomerById(Integer id) {
	        return repo.findById(id).orElse(null);
	    }
	

}
