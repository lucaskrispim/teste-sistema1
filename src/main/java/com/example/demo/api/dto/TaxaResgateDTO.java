package com.example.demo.api.dto;

import com.example.demo.model.entity.AtivoAdquirido;
import com.example.demo.model.entity.TaxaResgate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaxaResgateDTO {
    private Long id;

    private String descricao;
    private BigDecimal porcentagemTaxa;
    private LocalDateTime dataVencimento;
    private Long ativoAdquiridoId;

    public static TaxaResgateDTO create(TaxaResgate taxaResgate) {
        ModelMapper modelMapper = new ModelMapper();
        TaxaResgateDTO dto = modelMapper.map(taxaResgate, TaxaResgateDTO.class);

        return dto;
    }
}
