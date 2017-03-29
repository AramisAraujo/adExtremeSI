package br.edu.ufcg.computacao.si1.service;

import br.edu.ufcg.computacao.si1.model.Notificacao;
import br.edu.ufcg.computacao.si1.repository.NotificacaoRepository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class NotificacaoServiceImpl implements NotificacaoService {
	
	    private NotificacaoRepository notificacaoRepository;
	    private static final Log LOGGER = LogFactory.getLog(NotificacaoServiceImpl.class);

	    @Autowired
	    public NotificacaoServiceImpl(NotificacaoRepository  notificacaoRepository) {
	        /*neste codigo apenas atribuimos o repositorio jpa ao atributo */
	        this. notificacaoRepository =  notificacaoRepository;
	    }

	    public  NotificacaoRepository getNotificacaoRepository(){
	        return this. notificacaoRepository;
	    }

	    @Override
	    public Notificacao create( Notificacao noti) {
	        LOGGER.debug("Notificacao " + noti.getId() + " criada");
	        /*aqui salvamos o anuncio recem criado no repositorio jpa*/
	        return  notificacaoRepository.save(noti);
	    }

	    @Override
	    public Optional<Notificacao> getById(Long id) {
	        LOGGER.debug("Notificacao está sendo retornada pelo id");
	        /*aqui recuperamos o anuncio pelo seu id*/
	        return Optional.ofNullable(notificacaoRepository.findOne(id));
	    }
	    
	    @Override
	    public Collection<Notificacao> getByUserId(long id){
	    	Collection<Notificacao>  notificacoes = this.getAll();
	    	
	    	Stream<Notificacao> filteredNoti=  notificacoes.stream().filter( notificacao ->  notificacao.getUsuario().getId() == id);
	        LOGGER.debug("Retornando  notificacões a partir do id do usuário");
	    	return filteredNoti.collect(Collectors.toCollection(ArrayList::new));
	    	
	    }
	    
	    @Override
	    public Collection<Notificacao> getAll() {
	        /*aqui retornamos todos os anuncios, sem distincao*/
	        LOGGER.debug("Retornando todos os tipos de  notificação");
	        return  notificacaoRepository.findAll();
	    }

	    @Override
	    public boolean update( Notificacao notificacao) {
	        /*a atualizacao do anuncio eh feita apenas se o anuncio ja existir*/
	        if ( notificacaoRepository.exists( notificacao.getId())) {
	            LOGGER.debug("Atualizando  notificação " +  notificacao.getId());
	            notificacaoRepository.save( notificacao);
	            return true;
	        }
	        return false;
	    }

	    @Override
	    public boolean delete(Long id) {
	        /*aqui se apaga o anuncio se ele existir*/

	        if ( notificacaoRepository.exists(id)) {
	            LOGGER.debug("Deletando  notificação");
	            notificacaoRepository.delete(id);
	            return true;
	        }
	        return false;
	    }
	    
	


}
