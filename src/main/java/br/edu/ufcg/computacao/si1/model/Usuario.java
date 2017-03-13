package br.edu.ufcg.computacao.si1.model;

import org.springframework.security.core.authority.AuthorityUtils;

import javax.persistence.*;

@Entity(name = "Usuario")
@Table(name = "tb_usuario")
public class Usuario extends org.springframework.security.core.userdetails.User{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, unique = true)
    private Long id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String senha;
    @Column(nullable = false)
    private RazaoSocial role;

    public Usuario() {
        super("default", "default", AuthorityUtils.createAuthorityList(RazaoSocial.USER.toString()));
    }

    public Usuario(String nome, String email, String senha, RazaoSocial role) {

        super(email, senha, AuthorityUtils.createAuthorityList(role.toString()));

        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public RazaoSocial getRole() {
        return role;
    }

    public void setRole(RazaoSocial role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Usuario)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        Usuario usuario = (Usuario) o;

        if (id != null ? !id.equals(usuario.id) : usuario.id != null) {
            return false;
        }
        if (email != null ? !email.equals(usuario.email) : usuario.email != null) {
            return false;
        }
        return role != null ? role.equals(usuario.role) : usuario.role == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }

}
