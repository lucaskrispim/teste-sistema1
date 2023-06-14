package com.example.demo.api.controller;

import com.example.demo.api.dto.AtivoDTO;
import com.example.demo.api.dto.CarteiraDTO;
import com.example.demo.exception.RegraNegocioException;
import com.example.demo.model.entity.Ativo;
import com.example.demo.model.entity.AtivoAdquirido;
import com.example.demo.model.entity.Carteira;
import com.example.demo.service.AtivoAdquiridoService;
import com.example.demo.api.dto.AtivoAdquiridoDTO;
import com.example.demo.service.AtivoService;
import com.example.demo.service.CarteiraService;
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
@RequestMapping("/api/v1/ativoadquirido")
@RequiredArgsConstructor
public class AtivoAdquiridoController {

    private final AtivoAdquiridoService service;
    private final AtivoService ativoService;

    @GetMapping()
    public ResponseEntity get() {
        List<AtivoAdquirido> ativosAdquiridos = service.getAtivosAdquiridos();
        var ativosAdquiridosDTO = converteEPreencheAtivoDto(ativosAdquiridos);
        return ResponseEntity.ok(ativosAdquiridosDTO.stream().collect(Collectors.toList()));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody AtivoAdquiridoDTO ativoAdquiridoDTO){
        try{
            AtivoAdquirido ativoAdquirido = converter(ativoAdquiridoDTO);
            ativoAdquirido = service.salvar(ativoAdquirido);
            return new ResponseEntity(ativoAdquirido, HttpStatus.CREATED);
        }catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody AtivoAdquiridoDTO dto) {
        if (!service.getAtivoAdquiridoById(id).isPresent()) {
            return new ResponseEntity("AtivoAdquirido não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            AtivoAdquirido ativoAdquirido = converter(dto);
            ativoAdquirido.setId(id);
            service.salvar(ativoAdquirido);
            return ResponseEntity.ok(ativoAdquirido);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<AtivoAdquirido> ativoAdquirido = service.getAtivoAdquiridoById(id);
        if (!ativoAdquirido.isPresent()) {
            return new ResponseEntity("AtivoAdquirido não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(ativoAdquirido.get());
            return new ResponseEntity(HttpStatus.ACCEPTED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    public AtivoAdquirido converter(AtivoAdquiridoDTO ativoAdquiridoDTO){
        ModelMapper modelMapper = new ModelMapper();
        AtivoAdquirido ativoAdquirido = modelMapper.map(ativoAdquiridoDTO, AtivoAdquirido.class);
        return ativoAdquirido;
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<AtivoAdquirido> ativoAdquirido = service.getAtivoAdquiridoById(id);
        if (ativoAdquirido.isEmpty()) {
            return new ResponseEntity("AtivoAdquirido não encontrado", HttpStatus.NOT_FOUND);
        }
        Ativo ativo = ativoAdquirido.get().getAtivo();
        var ativoAdquiridoDTO = ativoAdquirido.map(AtivoAdquiridoDTO::create);
        ativoAdquiridoDTO.get().setAtivoDto(new AtivoDTO(ativo.getId(), ativo.getNome(), ativo.getTipo()));

        return ResponseEntity.ok(ativoAdquiridoDTO);
    }

    @GetMapping("/carteira={id}")
    public ResponseEntity getByCarteira(@PathVariable("id") Long id) {
        List<AtivoAdquirido> ativosAdquiridos = service.getAtivoAdquiridoByCarteiraId(id);
        if (ativosAdquiridos == null || ativosAdquiridos.isEmpty()) {
            return new ResponseEntity("Nenhum AtivoAdquirido encontrado", HttpStatus.NOT_FOUND);
        }
        var ativosAdquiridosDTO = converteEPreencheAtivoDto(ativosAdquiridos);
        return ResponseEntity.ok(ativosAdquiridosDTO.stream().collect(Collectors.toList()));
    }

    public List<AtivoAdquiridoDTO> converteEPreencheAtivoDto(List<AtivoAdquirido> ativosAdquiridos){
        var ativosAdquiridosDTO = ativosAdquiridos.stream().map(AtivoAdquiridoDTO::create).collect(Collectors.toList());
        for (var ativoAdquiridoDTO:ativosAdquiridosDTO) {
            var ativo = ativoService.getAtivoById(ativoAdquiridoDTO.getAtivoId()).get();
            var ativoDTO = new AtivoDTO(ativo.getId(), ativo.getNome(), ativo.getTipo());
            ativoAdquiridoDTO.setAtivoDto(ativoDTO);
        }
        return ativosAdquiridosDTO;
    }

}
