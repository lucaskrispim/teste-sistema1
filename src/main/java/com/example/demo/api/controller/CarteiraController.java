package com.example.demo.api.controller;

import com.example.demo.api.dto.AtivoDTO;
import com.example.demo.exception.RegraNegocioException;
import com.example.demo.model.entity.Ativo;
import com.example.demo.model.entity.Carteira;
import com.example.demo.service.AtivoAdquiridoService;
import com.example.demo.service.CarteiraService;
import com.example.demo.api.dto.CarteiraDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/carteira")
@RequiredArgsConstructor
public class CarteiraController {

    private final CarteiraService service;
    private final AtivoAdquiridoService ativoAdquiridoService;

    @GetMapping()
    public ResponseEntity get() {
        List<Carteira> carteira = service.getCarteiras();
        return ResponseEntity.ok(carteira.stream().map(CarteiraDTO::create).collect(Collectors.toList()));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody CarteiraDTO carteiraDto){
        try{
            Carteira carteira = converter(carteiraDto);
            carteira = service.salvar(carteira);
            return new ResponseEntity(carteira, HttpStatus.CREATED);
        }catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody CarteiraDTO dto) {
        if (!service.getCarteiraById(id).isPresent()) {
            return new ResponseEntity("Carteira não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Carteira carteira = converter(dto);
            carteira.setId(id);
            service.salvar(carteira);
            return ResponseEntity.ok(carteira);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Carteira> carteira = service.getCarteiraById(id);
        if (!carteira.isPresent()) {
            return new ResponseEntity("Carteira não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(carteira.get());
            return new ResponseEntity(HttpStatus.ACCEPTED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Carteira converter(CarteiraDTO carteiraDto){
        ModelMapper modelMapper = new ModelMapper();
        Carteira carteira = modelMapper.map(carteiraDto, Carteira.class);
        return carteira;
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Carteira> carteira = service.getCarteiraById(id);
        if (!carteira.isPresent()) {
            return new ResponseEntity("Carteira não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(carteira.map(CarteiraDTO::create));
    }

}