package br.edu.ufcg.computacao.si1.service;

import br.edu.ufcg.computacao.si1.model.Usuario;
import br.edu.ufcg.computacao.si1.model.form.UsuarioForm;

import java.util.Collection;
import java.util.Optional;

public interface UsuarioService {

    public Usuario create(UsuarioForm usuarioForm);

    public Optional<Usuario> getById(Long id);

    public Optional<Usuario> getByEmail(String email);

    public Collection<Usuario> getAll();

    public boolean update(Usuario usuario);

    public boolean delete(Long id);
    
    public Usuario getLoggedUser();
    
    public boolean userIsLogged();
    
}
