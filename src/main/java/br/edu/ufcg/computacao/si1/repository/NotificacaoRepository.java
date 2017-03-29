package br.edu.ufcg.computacao.si1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ufcg.computacao.si1.model.Anuncio;
import br.edu.ufcg.computacao.si1.model.Notificacao;

public interface NotificacaoRepository  extends JpaRepository<Notificacao, Long> {

}
