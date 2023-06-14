package com.example.demo.api.controller;

import com.example.demo.api.dto.HistoricoProventosDTO;
import com.example.demo.exception.RegraNegocioException;
import com.example.demo.model.entity.Ativo;
import com.example.demo.model.entity.HistoricoProventos;
import com.example.demo.model.entity.TaxaResgate;
import com.example.demo.service.TaxaResgateService;
import com.example.demo.api.dto.TaxaResgateDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/taxaresgate")
@RequiredArgsConstructor
public class TaxaResgateController {

    private final TaxaResgateService service;

    @GetMapping()
    public ResponseEntity get() {
        List<TaxaResgate> taxasResgate = service.getTaxasResgate();
        return ResponseEntity.ok(taxasResgate.stream().map(TaxaResgateDTO::create).collect(Collectors.toList()));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody TaxaResgateDTO taxaResgateDTO){
        try{
            TaxaResgate taxaResgate = converter(taxaResgateDTO);
            taxaResgate = service.salvar(taxaResgate);
            return new ResponseEntity(taxaResgate, HttpStatus.CREATED);
        }catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody TaxaResgateDTO dto) {
        if (!service.getTaxaResgateById(id).isPresent()) {
            return new ResponseEntity("TaxaResgate não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            TaxaResgate taxaResgate = converter(dto);
            taxaResgate.setId(id);
            service.salvar(taxaResgate);
            return ResponseEntity.ok(taxaResgate);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<TaxaResgate> taxaResgate = service.getTaxaResgateById(id);
        if (!taxaResgate.isPresent()) {
            return new ResponseEntity("TaxaResgate não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(taxaResgate.get());
            return new ResponseEntity(HttpStatus.ACCEPTED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public TaxaResgate converter(TaxaResgateDTO taxaResgateDTO){
        ModelMapper modelMapper = new ModelMapper();
        TaxaResgate taxaResgate = modelMapper.map(taxaResgateDTO, TaxaResgate.class);
        return taxaResgate;
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<TaxaResgate> taxaResgate = service.getTaxaResgateById(id);
        if (!taxaResgate.isPresent()) {
            return new ResponseEntity("TaxaResgate não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(taxaResgate.map(TaxaResgateDTO::create));
    }

}