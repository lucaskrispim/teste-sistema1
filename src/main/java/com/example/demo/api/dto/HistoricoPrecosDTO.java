package com.example.demo.api.dto;

import com.example.demo.model.entity.Ativo;
import com.example.demo.model.entity.HistoricoPrecos;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoricoPrecosDTO {
    private Long id;

    private LocalDateTime data;
    private BigDecimal valor;
    private Long ativoId;

    public static HistoricoPrecosDTO create(HistoricoPrecos historico) {
        ModelMapper modelMapper = new ModelMapper();
        HistoricoPrecosDTO dto = modelMapper.map(historico, HistoricoPrecosDTO.class);

        return dto;
    }
}
