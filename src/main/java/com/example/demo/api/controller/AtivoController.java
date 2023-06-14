package com.example.demo.api.controller;

import com.example.demo.api.dto.AtivoAdquiridoDTO;
import com.example.demo.api.dto.CarteiraDTO;
import com.example.demo.exception.RegraNegocioException;
import com.example.demo.model.entity.Ativo;
import com.example.demo.model.entity.AtivoAdquirido;
import com.example.demo.model.entity.Carteira;
import com.example.demo.service.AtivoService;
import com.example.demo.api.dto.AtivoDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/ativo")
@RequiredArgsConstructor
public class AtivoController {

    private final AtivoService service;

    @GetMapping()
    public ResponseEntity get() {
        List<Ativo> ativos = service.getAtivos();
        return ResponseEntity.ok(ativos.stream().map(AtivoDTO::create).collect(Collectors.toList()));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody AtivoDTO ativoDto){
        try{
            Ativo ativo = converter(ativoDto);
            ativo = service.salvar(ativo);
            return new ResponseEntity(ativo, HttpStatus.CREATED);
        }catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody AtivoDTO dto) {
        if (!service.getAtivoById(id).isPresent()) {
            return new ResponseEntity("Ativo não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Ativo ativo = converter(dto);
            ativo.setId(id);
            service.salvar(ativo);
            return ResponseEntity.ok(ativo);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Ativo> ativo = service.getAtivoById(id);
        if (!ativo.isPresent()) {
            return new ResponseEntity("Ativo não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(ativo.get());
            return new ResponseEntity(HttpStatus.ACCEPTED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Ativo converter(AtivoDTO ativoDTO){
        ModelMapper modelMapper = new ModelMapper();
        Ativo ativo = modelMapper.map(ativoDTO, Ativo.class);
        return ativo;
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Ativo> ativo = service.getAtivoById(id);
        if (!ativo.isPresent()) {
            return new ResponseEntity("Ativo não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(ativo.map(AtivoDTO::create));
    }

}
