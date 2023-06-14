package com.example.demo.api.controller;

import com.example.demo.api.dto.AtivoAdquiridoDTO;
import com.example.demo.api.dto.CarteiraDTO;
import com.example.demo.exception.RegraNegocioException;
import com.example.demo.model.entity.Ativo;
import com.example.demo.model.entity.AtivoAdquirido;
import com.example.demo.model.entity.Carteira;
import com.example.demo.model.entity.HistoricoPrecos;
import com.example.demo.service.HistoricoPrecosService;
import com.example.demo.api.dto.HistoricoPrecosDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/historicopreco")
@RequiredArgsConstructor
public class HistoricoPrecosController {

    private final HistoricoPrecosService service;

    @GetMapping()
    public ResponseEntity get() {
        List<HistoricoPrecos> historicoPrecos = service.getHistoricosPrecos();
        return ResponseEntity.ok(historicoPrecos.stream().map(HistoricoPrecosDTO::create).collect(Collectors.toList()));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody HistoricoPrecosDTO historicoPrecosDTO){
        try{
            HistoricoPrecos historicoPrecos = converter(historicoPrecosDTO);
            historicoPrecos = service.salvar(historicoPrecos);
            return new ResponseEntity(historicoPrecos, HttpStatus.CREATED);
        }catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody HistoricoPrecosDTO dto) {
        if (!service.getHistoricoPrecosById(id).isPresent()) {
            return new ResponseEntity("HistoricoPrecos não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            HistoricoPrecos historicoPrecos = converter(dto);
            historicoPrecos.setId(id);
            service.salvar(historicoPrecos);
            return ResponseEntity.ok(historicoPrecos);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<HistoricoPrecos> historicoPrecos = service.getHistoricoPrecosById(id);
        if (!historicoPrecos.isPresent()) {
            return new ResponseEntity("HistoricoPrecos não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(historicoPrecos.get());
            return new ResponseEntity(HttpStatus.ACCEPTED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public HistoricoPrecos converter(HistoricoPrecosDTO historicoPrecosDTO){
        ModelMapper modelMapper = new ModelMapper();
        HistoricoPrecos historicoPrecos = modelMapper.map(historicoPrecosDTO, HistoricoPrecos.class);
        return historicoPrecos;
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<HistoricoPrecos> historicoPreco = service.getHistoricoPrecosById(id);
        if (!historicoPreco.isPresent()) {
            return new ResponseEntity("HistoricoPrecos não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(historicoPreco.map(HistoricoPrecosDTO::create));
    }

}