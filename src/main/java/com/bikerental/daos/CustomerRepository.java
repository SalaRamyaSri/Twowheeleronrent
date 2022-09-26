package com.bikerental.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Repository;

import com.bikerental.entities.Customer;

@EnableJpaAuditing
@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

}
