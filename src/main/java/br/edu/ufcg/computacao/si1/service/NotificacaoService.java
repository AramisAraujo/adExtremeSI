package br.edu.ufcg.computacao.si1.service;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

import br.edu.ufcg.computacao.si1.model.Notificacao;


public interface NotificacaoService {
	
    public Notificacao create(Notificacao notificacao);

    public Optional<Notificacao> getById(Long id);
    
    Collection<Notificacao> getByUserId(long id);

    public Collection<Notificacao> getAll();

    public boolean update(Notificacao notificacao);

    public boolean delete(Long id);

}
