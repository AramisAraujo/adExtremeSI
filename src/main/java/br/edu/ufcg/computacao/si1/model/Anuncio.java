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

	private static final String[] tipos = new String[] { "movel", "imovel", "emprego" };

	private final static DateFormat DATE_FORMAT = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "_id", nullable = false, unique = true)
	private Long _id;

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
	public Long get_id() {
		return _id;
	}

	/**
	 * Modifica o id do anuncio
	 * 
	 * @param _id
	 *            id a ser colocado no anuncio
	 */
	public void set_id(Long _id) {
		this._id = _id;
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
		@Column(name = "_id", nullable = false, unique = true)
		private Long _id;

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
	public String toString() {

		String infoAnuncio = "";

		infoAnuncio += String.format("Anuncio{_id=%d, ", _id);
		infoAnuncio += String.format("titulo=%s, ", titulo);
		infoAnuncio += String.format("dataDeCriacao=%s, ", getDataDeCriacao());
		infoAnuncio += String.format("preco=%.2f, ", preco);
		infoAnuncio += String.format("nota=%s, ", nota);
		infoAnuncio += String.format("tipo=%s}", tipo);

		return infoAnuncio;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Anuncio anuncio = (Anuncio) o;

        if (Double.compare(anuncio.preco, preco) != 0) {
            return false;
        }
        if (_id != null ? !_id.equals(anuncio._id) : anuncio._id != null) {
            return false;
        }
        if (titulo != null ? !titulo.equals(anuncio.titulo) : anuncio.titulo != null) {
            return false;
        }
        if (dataDeCriacao != null ? !dataDeCriacao.equals(anuncio.dataDeCriacao) : anuncio.dataDeCriacao != null) {
            return false;
        }
        if (nota != anuncio.nota) {
            return false;
        }
        return tipo != null ? tipo.equals(anuncio.tipo) : anuncio.tipo == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = _id != null ? _id.hashCode() : 0;
        result = 31 * result + (titulo != null ? titulo.hashCode() : 0);
        result = 31 * result + (dataDeCriacao != null ? dataDeCriacao.hashCode() : 0);
        temp = Double.doubleToLongBits(preco);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (nota != null ? nota.hashCode() : 0);
        result = 31 * result + (tipo != null ? tipo.hashCode() : 0);
        return result;
    }

}
