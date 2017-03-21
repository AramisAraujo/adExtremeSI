package br.edu.ufcg.computacao.si1.service;

import br.edu.ufcg.computacao.si1.model.Anuncio;

import java.util.Collection;
import java.util.Optional;

public interface AnuncioService {

    public Anuncio create(Anuncio anuncio);

    public Optional<Anuncio> getById(Long id);

    public Collection<Anuncio> get(String tipo);

    public Collection<Anuncio> getAll();

    public boolean update(Anuncio anuncio);

    public boolean delete(Long id);

}
