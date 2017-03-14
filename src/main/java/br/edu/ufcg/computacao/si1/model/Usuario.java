package br.edu.ufcg.computacao.si1.model;

import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Objects;

import javax.persistence.*;

@Entity(name = "Usuario")
@Table(name = "tb_usuario")
public class Usuario extends org.springframework.security.core.userdetails.User {

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
	@Column
	private double saldo;

	public Usuario() {
		super("default", "default", AuthorityUtils.createAuthorityList(RazaoSocial.USER.toString()));
	}

	public Usuario(String nome, String email, String senha, RazaoSocial role) {

		super(email, senha, AuthorityUtils.createAuthorityList(role.toString()));

		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.role = role;
		this.saldo = 0.0;
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

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	@Override
	public boolean equals(Object obj) {

		if (obj.getClass() != Usuario.class) {
			return false;
		}

		Usuario outro = (Usuario) obj;

		if (!this.nome.equals(outro.nome)) {
			return false;
		}
		if (!this.email.equals(outro.email)) {
			return false;
		}
		if (!this.senha.equals(outro.senha)) {
			return false;
		}
		if (!this.role.equals(outro.role)) {
			return false;
		}
		if (this.saldo != outro.saldo) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		long temp;
		temp = Double.doubleToLongBits(saldo);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((senha == null) ? 0 : senha.hashCode());
		return result;
	}

}
