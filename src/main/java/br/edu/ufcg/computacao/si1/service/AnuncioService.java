package br.edu.ufcg.computacao.si1.service;

import br.edu.ufcg.computacao.si1.model.Anuncio;
import br.edu.ufcg.computacao.si1.model.TipoAnuncio;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

public interface AnuncioService {

    public Anuncio create(Anuncio anuncio);

    public Optional<Anuncio> getById(Long id);

    Collection<Anuncio> getByType(TipoAnuncio tipo);
    
    Collection<Anuncio> getByDateBetween(Date fromDate, Date untilDate);
    
    Collection<Anuncio> getByUserId(long id);

    public Collection<Anuncio> getAll();

    public boolean update(Anuncio anuncio);

    public boolean delete(Long id);

}
