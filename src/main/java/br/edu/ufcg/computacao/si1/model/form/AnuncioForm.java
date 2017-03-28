package br.edu.ufcg.computacao.si1.model.form;


import org.hibernate.validator.constraints.NotEmpty;

import br.edu.ufcg.computacao.si1.model.TipoAnuncio;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AnuncioForm {

    public static final TipoAnuncio[] permUser = new TipoAnuncio[] {TipoAnuncio.MOVEL, TipoAnuncio.IMOVEL};
    public static final TipoAnuncio[] permCompany = new TipoAnuncio[] {TipoAnuncio.MOVEL, TipoAnuncio.IMOVEL,
    		TipoAnuncio.EMPREGO,TipoAnuncio.SERVICO}; 

    @NotNull(message = "O titulo não pode ser nulo.")
    @NotEmpty(message = "O titulo não pode estar vazio.")
    @Size(min = 2, max = 100, message = "O titulo deve ter entre 2 e 100 caracteres.")
    private String titulo;
    @NotNull(message = "O preço não pode ser nulo.")
    @DecimalMin(value = "0.1", message = "O preço minimo é 0.1 para um anúncio.")
    private Double preco;
    @Enumerated(EnumType.STRING)
    private TipoAnuncio tipo;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public TipoAnuncio getTipo() {
        return tipo;
    }

    public void setTipo(TipoAnuncio tipo) {
        this.tipo = tipo;
    }
    
}

