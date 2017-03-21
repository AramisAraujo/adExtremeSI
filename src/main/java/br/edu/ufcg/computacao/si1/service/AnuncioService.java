package br.edu.ufcg.computacao.si1.service;

import br.edu.ufcg.computacao.si1.model.Anuncio;

import java.util.Collection;
import java.util.Optional;

public interface AnuncioService {

    Anuncio create(Anuncio anuncio);

    Optional<Anuncio> getById(Long id);

    Collection<Anuncio> get(String tipo);

    Collection<Anuncio> getAll();

    boolean update(Anuncio anuncio);

    boolean delete(Long id);

}
