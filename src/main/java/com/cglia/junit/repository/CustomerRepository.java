package com.cglia.junit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cglia.junit.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer>{

}
