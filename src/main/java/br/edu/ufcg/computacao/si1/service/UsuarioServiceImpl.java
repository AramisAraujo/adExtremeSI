package br.edu.ufcg.computacao.si1.service;

import br.edu.ufcg.computacao.si1.model.RazaoSocial;
import br.edu.ufcg.computacao.si1.model.Usuario;
import br.edu.ufcg.computacao.si1.model.form.UsuarioForm;
import br.edu.ufcg.computacao.si1.repository.UsuarioRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService{

    private UsuarioRepository usuarioRepository;
    private static final Log LOGGER = LogFactory.getLog(UsuarioService.class);

    @Autowired
    public void setUsuarioRepository(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Usuario create(UsuarioForm usuarioForm) {

        Usuario usuario=null;

            if(usuarioForm.getRole().equals(RazaoSocial.USER)){
            	
                usuario = new Usuario(usuarioForm.getNome(), usuarioForm.getEmail(),
                usuarioForm.getSenha(), RazaoSocial.USER);
            }

            else if(usuarioForm.getRole().equals(RazaoSocial.COMPANY)){
            	
                usuario = new Usuario(usuarioForm.getNome(), usuarioForm.getEmail(),
                usuarioForm.getSenha(), RazaoSocial.COMPANY);
            }

                //new BCryptPasswordEncoder().encode(usuarioForm.getSenha()), "COMPANY");

        LOGGER.debug("Usuário novo está sendo criado");
        return usuarioRepository.save(usuario);
    }

    @Override
    public Optional<Usuario> getById(Long id) {
        LOGGER.debug("Usuário está sendo retornado pelo id");
        return Optional.ofNullable(usuarioRepository.findOne(id));
    }

    @Override
    public Optional<Usuario> getByEmail(String email) {
        return Optional.ofNullable(usuarioRepository.findByEmail(email));
    }

    @Override
    public Collection<Usuario> getAll() {
        LOGGER.debug("Retornando todos os usuários");
        return usuarioRepository.findAll();
    }

    @Override
    public boolean update(Usuario usuario) {
        LOGGER.debug("Usuário " + usuario.getNome() + " está sendo atualizado");

        if (usuarioRepository.exists(usuario.getId())) {
            usuarioRepository.save(usuario);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Long id) {
        if (usuarioRepository.exists(id)) {
            LOGGER.debug("Usuário está sendo deletado");
            usuarioRepository.delete(id);
            return true;
        }
        return false;
    }
    
    public Usuario getLoggedUser() throws AuthenticationCredentialsNotFoundException{
    	
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	
    	String emailUsuario = auth.getName();
    	
    	if(getByEmail(emailUsuario).isPresent()){
    		
    		return this.getByEmail(emailUsuario).get();
    	}
    	else{
    		throw new AuthenticationCredentialsNotFoundException("Este usuario nao existe ou nao estah logado.");
    	}
    	    	
    	
    }
    
    public boolean userIsLogged(){
    	
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	
    	String emailUsuario = auth.getName();
    	
    	if(getByEmail(emailUsuario).isPresent()){
    		
    		return true;
    	}
    	else{
    		return false;
    	}
    	
    	
    }
    
}
