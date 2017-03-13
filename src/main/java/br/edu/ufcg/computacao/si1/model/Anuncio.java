package br.edu.ufcg.computacao.si1.model;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Marcus Oliveira on 08/12/16.
 */
@Entity
@Table(name = "tb_anuncio")
public class Anuncio {

    private static final String[] tipos = new String[]{"movel", "imovel", "emprego"};

    private final static DateFormat DATE_FORMAT = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "data_criacao", nullable = false)
    private Date dataDeCriacao;

    @Column(name = "preco", nullable = false)
    private double preco;

    @Column(name = "nota")
    private Notas nota;

    @Column(name = "tipo", nullable = false)
    private String tipo;

    public Anuncio(String titulo, Date dataDeCriacao, double preco, Notas nota, String tipo) {
        this.titulo = titulo;
        this.dataDeCriacao = dataDeCriacao;
        this.preco = preco;
        this.nota = nota;
        this.tipo = tipo;
    }

    private Anuncio(AnuncioBuilder builder) {
        this.titulo = builder.titulo;
        this.dataDeCriacao = builder.dataDeCriacao;
        this.preco = builder.preco;
        this.nota = builder.nota;
        this.tipo = builder.tipo;
    }

    /**
     * Requerimento do JPA
     */
    Anuncio() {

    }

    /**
     * Retorna o id do anuncio
     *
     * @return o id do anuncio
     */
    public Long getId() {
        return id;
    }

    /**
     * Modifica o id do anuncio
     *
     * @param id id a ser colocado no anuncio
     */
    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDataDeCriacao() {
        return DATE_FORMAT.format(dataDeCriacao);
    }

    public void setDataDeCriacao(Date dataDeCriacao) {
        this.dataDeCriacao = dataDeCriacao;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public Notas getNota() {
        return nota;
    }

    public void setNota(Notas nota) {
        this.nota = nota;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public static class AnuncioBuilder {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "id", nullable = false, unique = true)
        private Long id;

        @Column(name = "titulo", nullable = false)
        private String titulo;

        @Column(name = "data_criacao", nullable = false)
        private Date dataDeCriacao = new Date();

        @Column(name = "preco", nullable = false)
        private double preco;

        @Column(name = "nota")
        private Notas nota = Notas.ESTRELA0;

        @Column(name = "tipo", nullable = false)
        private String tipo;

        public AnuncioBuilder(String titulo, double preco, String tipo) {
            this.titulo = titulo;
            this.preco = preco;
            this.tipo = tipo;
        }

        public AnuncioBuilder nota(Notas nota) {
            this.nota = nota;
            return this;
        }

        public AnuncioBuilder dataDeCriacao(Date dataDeCriacao) {
            this.dataDeCriacao = dataDeCriacao;
            return this;
        }

        public Anuncio build() {
            return new Anuncio(this);
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Anuncio))
            return false;

        Anuncio anuncio = (Anuncio) o;

        if (Double.compare(anuncio.getPreco(), getPreco()) != 0)
            return false;
        if (!getId().equals(anuncio.getId()))
            return false;
        if (!getTitulo().equals(anuncio.getTitulo()))
            return false;
        if (!getDataDeCriacao().equals(anuncio.getDataDeCriacao()))
            return false;
        if (getNota() != null ? !getNota().equals(anuncio.getNota()) : anuncio.getNota() != null)
            return false;
        return getTipo().equals(anuncio.getTipo());

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = getId().hashCode();
        result = 31 * result + getTitulo().hashCode();
        result = 31 * result + getDataDeCriacao().hashCode();
        temp = Double.doubleToLongBits(getPreco());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (getNota() != null ? getNota().hashCode() : 0);
        result = 31 * result + getTipo().hashCode();
        return result;
    }

    @Override
    public String toString() {

        String infoAnuncio = "";

        infoAnuncio += String.format("Anuncio{_id=%d, ", id);
        infoAnuncio += String.format("titulo=%s, ", titulo);
        infoAnuncio += String.format("dataDeCriacao=%s, ", getDataDeCriacao());
        infoAnuncio += String.format("preco=%.2f, ", preco);
        infoAnuncio += String.format("nota=%s, ", nota);
        infoAnuncio += String.format("tipo=%s}", tipo);

        return infoAnuncio;
    }
}