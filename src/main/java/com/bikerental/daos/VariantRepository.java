package com.bikerental.daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Repository;

import com.bikerental.entities.Company;
import com.bikerental.entities.Variant;

@EnableJpaAuditing
@Repository
public interface VariantRepository extends JpaRepository<Variant, Integer> {

	List<Variant> findByCompany(Company company);
}
