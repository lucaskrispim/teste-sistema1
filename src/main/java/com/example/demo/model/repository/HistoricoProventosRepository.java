package com.example.demo.model.repository;

import com.example.demo.model.entity.HistoricoProventos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoricoProventosRepository extends JpaRepository<HistoricoProventos,Long> {

}
