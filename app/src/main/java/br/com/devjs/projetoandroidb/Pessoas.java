package br.com.devjs.projetoandroidb;


import java.io.Serializable;

/**
 * Created by antonios on 07/08/17.
 */

public class Pessoas implements Serializable{

    private String nome;
    private Double altura;
    private Double peso;
    private String url;
    private String imc;


    private static Double IMC_IDEAL = 24.9;

    public Pessoas(String nome, Double altura, Double peso, String url, String imc) {
        this.nome = nome;
        this.altura = altura;
        this.peso = peso;
        this.url = url;
        this.imc = imc;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getAltura() {
        return altura;
    }

    public void setAltura(Double altura) {
        this.altura = altura;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImc() {
        return imc;
    }

    public void setImc(String imc) {
        this.imc = imc;
    }

    public Double getPesoIdeal(){
        return (IMC_IDEAL * (getAltura() * getAltura()));
    }

}
