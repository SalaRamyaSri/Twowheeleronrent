package com.bikerental.daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Repository;

import com.bikerental.entities.Booking;
import com.bikerental.entities.Payment;

@EnableJpaAuditing
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
	
	List<Payment> findByBooking(Booking booking);

}
