package com.example.demo.service;

import com.example.demo.model.entity.Carteira;
import com.example.demo.model.entity.HistoricoPrecos;
import com.example.demo.model.repository.CarteiraRepository;
import com.example.demo.model.repository.HistoricoPrecosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class HistoricoPrecosService {

//    @Autowired
//    private PasswordEncoder encoder;

    @Autowired
    private HistoricoPrecosRepository repository;

    public HistoricoPrecosService(HistoricoPrecosRepository repository) {
        this.repository = repository;
    }

    public List<HistoricoPrecos> getHistoricosPrecos() {
        return repository.findAll();
    }

    public Optional<HistoricoPrecos> getHistoricoPrecosById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public HistoricoPrecos salvar(HistoricoPrecos historicoPrecos) {
        validar(historicoPrecos);
        return repository.save(historicoPrecos);
    }

    @Transactional
    public void excluir(HistoricoPrecos historicoPrecos) {
        Objects.requireNonNull(historicoPrecos.getId());
        repository.delete(historicoPrecos);
    }

    public void validar(HistoricoPrecos historicoPrecos) {

    }
}
