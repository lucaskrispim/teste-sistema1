package com.example.demo.api.dto;

import com.example.demo.model.entity.Ativo;
import com.example.demo.model.entity.HistoricoProventos;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class    HistoricoProventosDTO {
    private Long id;

    private String descricao;
    private BigDecimal valor;
    private LocalDateTime dataPagamento;
    private Long ativoId;

    public static HistoricoProventosDTO create(HistoricoProventos historico) {
        ModelMapper modelMapper = new ModelMapper();
        HistoricoProventosDTO dto = modelMapper.map(historico, HistoricoProventosDTO.class);

        return dto;
    }
}
