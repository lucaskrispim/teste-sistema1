package com.example.demo.api.controller;

import com.example.demo.api.dto.HistoricoPrecosDTO;
import com.example.demo.exception.RegraNegocioException;
import com.example.demo.model.entity.Ativo;
import com.example.demo.model.entity.HistoricoPrecos;
import com.example.demo.model.entity.HistoricoProventos;
import com.example.demo.service.HistoricoProventosService;
import com.example.demo.api.dto.HistoricoProventosDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/historicoprovento")
@RequiredArgsConstructor
public class HistoricoProventosController {

    private final HistoricoProventosService service;

    @GetMapping()
    public ResponseEntity get() {
        List<HistoricoProventos> historicoProventos = service.getHistoricosProventos();
        return ResponseEntity.ok(historicoProventos.stream().map(HistoricoProventosDTO::create).collect(Collectors.toList()));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody HistoricoProventosDTO historicoProventosDTO){
        try{
            HistoricoProventos historicoProventos = converter(historicoProventosDTO);
            historicoProventos = service.salvar(historicoProventos);
            return new ResponseEntity(historicoProventos, HttpStatus.CREATED);
        }catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody HistoricoProventosDTO dto) {
        if (!service.getHistoricoProventosById(id).isPresent()) {
            return new ResponseEntity("HistoricoProventos não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            HistoricoProventos historicoProventos = converter(dto);
            historicoProventos.setId(id);
            service.salvar(historicoProventos);
            return ResponseEntity.ok(historicoProventos);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<HistoricoProventos> historicoProventos = service.getHistoricoProventosById(id);
        if (!historicoProventos.isPresent()) {
            return new ResponseEntity("HistoricoProventos não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(historicoProventos.get());
            return new ResponseEntity(HttpStatus.ACCEPTED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public HistoricoProventos converter(HistoricoProventosDTO historicoProventosDTO){
        ModelMapper modelMapper = new ModelMapper();
        HistoricoProventos historicoProventos = modelMapper.map(historicoProventosDTO, HistoricoProventos.class);
        return historicoProventos;
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<HistoricoProventos> historicoProventos = service.getHistoricoProventosById(id);
        if (!historicoProventos.isPresent()) {
            return new ResponseEntity("HistoricoProventos não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(historicoProventos.map(HistoricoProventosDTO::create));
    }

}