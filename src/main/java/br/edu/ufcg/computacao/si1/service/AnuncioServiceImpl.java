package br.edu.ufcg.computacao.si1.service;

import br.edu.ufcg.computacao.si1.model.Anuncio;
import br.edu.ufcg.computacao.si1.model.Usuario;
import br.edu.ufcg.computacao.si1.repository.AnuncioRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Marcus Oliveira on 28/12/16.
 */
@Service
public class AnuncioServiceImpl implements AnuncioService {
    //TODO add validity checks

    private AnuncioRepository anuncioRepository;
    protected Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    public AnuncioServiceImpl(AnuncioRepository anuncioRepository) {
        /*neste codigo apenas atribuimos o repositorio jpa ao atributo */
        this.anuncioRepository = anuncioRepository;
    }

    public AnuncioRepository getAnuncioRepository(){
        return this.anuncioRepository;
    }

    @Override
    public Anuncio create(Anuncio anuncio) {
        logger.debug("Anúncio " + anuncio.getTitulo() + " criado");
        /*aqui salvamos o anuncio recem criado no repositorio jpa*/
        return anuncioRepository.save(anuncio);
    }

    @Override
    public Optional<Anuncio> getById(Long id) {
        logger.debug("Anúncio está sendo retornado pelo id");
        /*aqui recuperamos o anuncio pelo seu id*/
        return Optional.ofNullable(anuncioRepository.findOne(id));
    }

    @Override
    public Collection<Anuncio> getByType(String tipo) {
        logger.debug("Retornando anúncios com o tipo " + tipo);
        /*pegamos aqui todos os anuncios, mas retornamos os anuncios por tipo
        * filtrando o tipo, pelo equals, retornando um arrayList*/
        return anuncioRepository.findAll().stream()
                .filter(anuncio -> anuncio.getTipo().equalsIgnoreCase(tipo))
                .collect(Collectors.toCollection(ArrayList::new));
    }
    
    @Override
    public Collection<Anuncio> getByDateBetween(Date fromDate, Date untilDate) {
    	/*pegamos aqui todos os anuncios, mas retornamos os anuncios por data
    	 * filtrando a data de criacao, pelo equals, retornando um arrayList*/

        logger.debug("Retornando anúncios entre " + fromDate + " e " + untilDate);
    	
    	Collection<Anuncio> anuncios = this.getAll();
    	
    	Stream<Anuncio> filteredAnuncios = anuncios.stream().filter(anuncio -> anuncio.getDataDeCriacao()
    			.after(fromDate));
    	
    	filteredAnuncios = filteredAnuncios.filter(anuncio -> anuncio.getDataDeCriacao().before(untilDate));
    		
    		return filteredAnuncios.collect(Collectors.toCollection(ArrayList::new));
    	
    }
    
    @Override
    public Collection<Anuncio> getByUserId(long id){
    	Collection<Anuncio> anuncios = this.getAll();
    	
    	Stream<Anuncio> filteredAnuncios = anuncios.stream().filter(anuncio -> anuncio.getAnunciante().getId() == id);
        logger.debug("Retornando anúncios a partir do id do usuário");
    	return filteredAnuncios.collect(Collectors.toCollection(ArrayList::new));
    	
    }
    
    @Override
    public Collection<Anuncio> getAll() {
        /*aqui retornamos todos os anuncios, sem distincao*/
        logger.debug("Retornando todos os tipos de anúncios");
        return anuncioRepository.findAll();
    }

    @Override
    public boolean update(Anuncio anuncio) {
        /*a atualizacao do anuncio eh feita apenas se o anuncio ja existir*/
        if (anuncioRepository.exists(anuncio.getId())) {
            logger.debug("Atualizando anúncio " + anuncio.getTitulo());
            anuncioRepository.save(anuncio);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Long id) {
        /*aqui se apaga o anuncio se ele existir*/

        if (anuncioRepository.exists(id)) {
            logger.debug("Deletando anúncio");
            anuncioRepository.delete(id);
            return true;
        }
        return false;
    }
    
}
