package com.example.demo.api.dto;

import com.example.demo.model.entity.Ativo;
import com.example.demo.model.entity.Carteira;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AtivoDTO {
    private Long id;

    private String nome;
    private String tipo;

    public static AtivoDTO create(Ativo ativo) {
        ModelMapper modelMapper = new ModelMapper();
        AtivoDTO dto = modelMapper.map(ativo, AtivoDTO.class);

        return dto;
    }
}
