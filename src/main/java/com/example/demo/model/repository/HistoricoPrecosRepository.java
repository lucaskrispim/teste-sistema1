package com.example.demo.model.repository;

import com.example.demo.model.entity.HistoricoPrecos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoricoPrecosRepository extends JpaRepository<HistoricoPrecos,Long> {

}
