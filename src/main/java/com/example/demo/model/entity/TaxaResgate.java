package com.example.demo.model.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaxaResgate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;
    private BigDecimal porcentagemTaxa;
    private LocalDateTime dataVencimento;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private AtivoAdquirido ativoAdquirido;
}
