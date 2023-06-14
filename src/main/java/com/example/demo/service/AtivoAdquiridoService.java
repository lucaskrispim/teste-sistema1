package com.example.demo.service;

import com.example.demo.exception.RegraNegocioException;
import com.example.demo.model.entity.Ativo;
import com.example.demo.model.entity.AtivoAdquirido;
import com.example.demo.model.repository.AtivoAdquiridoRepository;
import com.example.demo.model.repository.AtivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AtivoAdquiridoService {

//    @Autowired
//    private PasswordEncoder encoder;

    @Autowired
    private AtivoAdquiridoRepository repository;

    public AtivoAdquiridoService(AtivoAdquiridoRepository repository) {
        this.repository = repository;
    }
    public List<AtivoAdquirido> getAtivosAdquiridos() {
        return repository.findAll();
    }
    public Optional<AtivoAdquirido> getAtivoAdquiridoById(Long id) {
        return repository.findById(id);
    }

    public List<AtivoAdquirido> getAtivoAdquiridoByCarteiraId(Long id) {
        return repository.findByCarteiraId(id);
    }

    public List<AtivoAdquirido> getAtivoAdquiridoByDataAquisicaoBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return repository.findAllByDataAquisicaoBetween(startDate, endDate);
    }

    @Transactional
    public AtivoAdquirido salvar(AtivoAdquirido ativoAdquirido) {
        validar(ativoAdquirido);
        return repository.save(ativoAdquirido);
    }

    @Transactional
    public void excluir(AtivoAdquirido ativoAdquirido) {
        Objects.requireNonNull(ativoAdquirido.getId());
        repository.delete(ativoAdquirido);
    }

    public void validar(AtivoAdquirido ativoAdquirido) {
        if(ativoAdquirido == null){
            throw new RegraNegocioException("Ativo Adquirido inválido");
        }
        if(ativoAdquirido.getCarteira() == null){
            throw new RegraNegocioException("Carteira inválida");
        }
        if(ativoAdquirido.getAtivo() == null){
            throw new RegraNegocioException("Ativo inválido");
        }
        if(ativoAdquirido.getDataAquisicao() == null){
            ativoAdquirido.setDataAquisicao(LocalDateTime.now());
        }
    }
}
