package br.edu.ufcg.computacao.si1.model;

public enum TipoAnuncio {
	TODOS("Todos"),
	MOVEL("Móvel"),
	IMOVEL("Imóvel"),
	EMPREGO("Emprego"),
	SERVICO("Serviço");
	
	private  final String NAME;
	
	private  TipoAnuncio(String name) {
		this.NAME = name;
	}
	
	@Override
	public String toString() {
		
		return this.NAME;
	}

}
