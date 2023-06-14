package com.example.demo.service;

import com.example.demo.model.entity.Carteira;
import com.example.demo.model.entity.TaxaResgate;
import com.example.demo.model.repository.CarteiraRepository;
import com.example.demo.model.repository.TaxaResgateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TaxaResgateService {

//    @Autowired
//    private PasswordEncoder encoder;

    @Autowired
    private TaxaResgateRepository repository;

    public TaxaResgateService(TaxaResgateRepository repository) {
        this.repository = repository;
    }

    public List<TaxaResgate> getTaxasResgate() {
        return repository.findAll();
    }

    public Optional<TaxaResgate> getTaxaResgateById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public TaxaResgate salvar(TaxaResgate taxaResgate) {
        validar(taxaResgate);
        return repository.save(taxaResgate);
    }

    @Transactional
    public void excluir(TaxaResgate taxaResgate) {
        Objects.requireNonNull(taxaResgate.getId());
        repository.delete(taxaResgate);
    }

    public void validar(TaxaResgate taxaResgate) {

    }
}
