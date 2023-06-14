package com.example.demo.api.dto;

import com.example.demo.model.entity.Ativo;
import com.example.demo.model.entity.AtivoAdquirido;
import com.example.demo.model.entity.Carteira;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AtivoAdquiridoDTO {
    private Long id;
    private BigDecimal valor;
    private Long quantidade;
    private LocalDateTime dataAquisicao;
    private Long ativoId;
    private AtivoDTO ativoDto;
    private Long carteiraId;

    public static AtivoAdquiridoDTO create(AtivoAdquirido ativoAdquirido) {
        ModelMapper modelMapper = new ModelMapper();
        AtivoAdquiridoDTO dto = modelMapper.map(ativoAdquirido, AtivoAdquiridoDTO.class);

        return dto;
    }
}
