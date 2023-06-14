package com.example.demo.api.dto;

import com.example.demo.model.entity.Carteira;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarteiraDTO {

    private Long id;

    private String nome;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataModificacao;
    private Long usuarioId;

    public static CarteiraDTO create(Carteira carteira) {
        ModelMapper modelMapper = new ModelMapper();
        CarteiraDTO dto = modelMapper.map(carteira, CarteiraDTO.class);

        return dto;
    }
}
