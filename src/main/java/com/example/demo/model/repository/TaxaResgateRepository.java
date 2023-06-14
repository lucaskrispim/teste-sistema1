package com.example.demo.model.repository;

import com.example.demo.model.entity.TaxaResgate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxaResgateRepository extends JpaRepository<TaxaResgate,Long> {

}
