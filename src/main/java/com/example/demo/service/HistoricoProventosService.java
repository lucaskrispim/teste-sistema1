package com.example.demo.service;

import com.example.demo.model.entity.HistoricoPrecos;
import com.example.demo.model.entity.HistoricoProventos;
import com.example.demo.model.repository.HistoricoPrecosRepository;
import com.example.demo.model.repository.HistoricoProventosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class HistoricoProventosService {

//    @Autowired
//    private PasswordEncoder encoder;

    @Autowired
    private HistoricoProventosRepository repository;

    public HistoricoProventosService(HistoricoProventosRepository repository) {
        this.repository = repository;
    }

    public List<HistoricoProventos> getHistoricosProventos() {
        return repository.findAll();
    }

    public Optional<HistoricoProventos> getHistoricoProventosById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public HistoricoProventos salvar(HistoricoProventos historicoProventos) {
        validar(historicoProventos);
        return repository.save(historicoProventos);
    }

    @Transactional
    public void excluir(HistoricoProventos historicoProventos) {
        Objects.requireNonNull(historicoProventos.getId());
        repository.delete(historicoProventos);
    }

    public void validar(HistoricoProventos historicoProventos) {

    }
}
