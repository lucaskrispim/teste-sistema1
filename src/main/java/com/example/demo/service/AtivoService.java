package com.example.demo.service;

import com.example.demo.api.dto.AtivoDTO;
import com.example.demo.exception.RegraNegocioException;
import com.example.demo.model.entity.Ativo;
import com.example.demo.model.repository.AtivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AtivoService {
    @Autowired
    private AtivoRepository repository;

    public AtivoService(AtivoRepository repository) {
        this.repository = repository;
    }

    public List<Ativo> getAtivos() {
        return repository.findAll();
    }

    public Optional<Ativo> getAtivoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Ativo salvar(Ativo ativo) {
        validar(ativo);
        return repository.save(ativo);
    }

    @Transactional
    public void excluir(Ativo ativo) {
        Objects.requireNonNull(ativo.getId());
        repository.delete(ativo);
    }

    public void validar(Ativo ativo) {
        if(ativo == null){
            throw new RegraNegocioException("Ativo inválido");
        }
        if(ativo.getNome().trim().equals("")){
            throw new RegraNegocioException("Nome inválido");
        }
        if(ativo.getTipo().trim().equals("")){
            throw new RegraNegocioException("Tipo inválido");
        }
    }
}
