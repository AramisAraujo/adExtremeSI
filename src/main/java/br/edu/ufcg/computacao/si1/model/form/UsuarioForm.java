package br.edu.ufcg.computacao.si1.model.form;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import br.edu.ufcg.computacao.si1.model.RazaoSocial;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UsuarioForm {
    @NotNull(message = "O nome não pode ser nulo.")
    @NotEmpty(message = "O nome não pode ser vazio.")
    @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres.")
    private String nome;
    @NotEmpty(message = "O email não pode ser vazio.")
    @Email
    private String email;
    @NotNull(message = "A senha não pode ser nula.")
    @NotEmpty
    @Size(min = 4, max = 16, message = "A senha deve ter entre 4 e 16 caracteres.")
    private String senha;
    @NotNull
    @Enumerated(EnumType.STRING)
    private RazaoSocial role;

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
}
