package com.example.demo.service;

import com.example.demo.exception.RegraNegocioException;
import com.example.demo.model.entity.AtivoAdquirido;
import com.example.demo.model.entity.Carteira;
import com.example.demo.model.repository.AtivoAdquiridoRepository;
import com.example.demo.model.repository.CarteiraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CarteiraService {

//    @Autowired
//    private PasswordEncoder encoder;

    @Autowired
    private CarteiraRepository repository;

    public CarteiraService(CarteiraRepository repository) {
        this.repository = repository;
    }

    public List<Carteira> getCarteiras() {
        return repository.findAll();
    }

    public Optional<Carteira> getCarteiraById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Carteira salvar(Carteira carteira) {
        validar(carteira);
        return repository.save(carteira);
    }

    @Transactional
    public void excluir(Carteira carteira) {
        Objects.requireNonNull(carteira.getId());

        repository.delete(carteira);
    }

    public void validar(Carteira carteira) {
        if(carteira == null){
            throw new RegraNegocioException("Carteira inválido");
        }
        if(carteira.getNome().trim().equals("")){
            throw new RegraNegocioException("Nome inválido");
        }
        if(carteira.getUsuario() == null){
            throw new RegraNegocioException("Usuário Proprietário inválido");
        }
        if(carteira.getDataModificacao() == null){
            carteira.setDataModificacao(LocalDateTime.now());
        }
        if(carteira.getDataCriacao() == null){
            carteira.setDataCriacao(LocalDateTime.now());
        }
    }
}
