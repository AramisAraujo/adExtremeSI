package br.edu.ufcg.computacao.si1.model;

/**
 * Enumerator que representa a avaliacao de um anuncio
 * utilizando caracteres de estrela.
 *
 */

public enum Notas {
	
	ESTRELA0(""),
	ESTRELA1("★"),
	ESTRELA2("★★"),
	ESTRELA3("★★★"),
	ESTRELA4("★★★★"),
	ESTRELA5("★★★★★");
	
	private String estrelas;
	
	private Notas(String estrelas){
		this.estrelas = estrelas;
	}
	
	@Override
	public String toString() {
		return this.estrelas;
	}

}
