package br.edu.ufcg.computacao.si1.model;

/* Enumerador que representa a diferença
 * entre pessoa Fisica e pessoa Juridica*/

public enum RazaoSocial {

    USER("fisica"), COMPANY("juridica");

    private String valor;

    RazaoSocial(String valor){
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

}
