package com.example.demo.model.repository;

import com.example.demo.model.entity.AtivoAdquirido;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AtivoAdquiridoRepository extends JpaRepository<AtivoAdquirido,Long> {
    @EntityGraph(attributePaths = "ativo")
    List<AtivoAdquirido> findByCarteiraId (Long carteiraId);
    @EntityGraph(attributePaths = "ativo")
    List<AtivoAdquirido> findAllByDataAquisicaoBetween (LocalDateTime startDate, LocalDateTime endDate);
}
