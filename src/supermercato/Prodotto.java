package supermercato;

import java.util.*;

public class Prodotto implements Comparable<Prodotto>{
	
	private String codice;
	private String nomeP;
	private int volume;
	
	private boolean daFrigo;
	
	private double prezzo;
	private int sconto;
	
	private int quantitaesposta=0;
	
	private Map<String,Integer> quantita = new TreeMap<String,Integer>();//mappa nomecorsia e quantita
	
	
	public Prodotto(String codice, String nomeP, int volume, boolean daFrigo) {
		
		this.codice = codice;
		this.nomeP = nomeP;
		this.volume = volume;
		this.daFrigo = daFrigo;
		this.prezzo = 0;
		this.sconto = 0;
	}
	


	public Prodotto(String codice, String nomeP, int volume, boolean daFrigo,
			double prezzo, int sconto) {
		
		this.codice = codice;
		this.nomeP = nomeP;
		this.volume = volume;
		this.daFrigo = daFrigo;
		this.prezzo = prezzo;
		this.sconto = sconto;
	}







	public Map<String, Integer> getQuantita() {
		return quantita;
	}





	public int getQuantita(String nomeC) {
		return quantita.get(nomeC);
	}



	public void setQuantita(String nomeC, int quantitaP) {
		quantita.put(nomeC, quantitaP);
	}
	
	public void aggiornaQuantita(String nomeC,int quantitapresa){
		
		int quantitanuova = quantita.get(nomeC) - quantitapresa;
		quantita.put(nomeC, quantitanuova);
		
		
	}



	public int getQuantitaesposta() {
		return quantitaesposta;
	}



	public void setQuantitaesposta(int quantitaesposta) {
		this.quantitaesposta += quantitaesposta;
	}



	public String getCodice(){
		return codice;
	}
	
	public String getNome(){
		return nomeP;
	}
	
	public int getVolume(){
		return volume;
	}

	public void setPrezzoListino(double prezzo) {
		this.prezzo=prezzo;

	}

	public double getPrezzoListino() {
		return prezzo;
	}

	public void setPercentualeSconto(int percentualeSconto) {
		this.sconto=percentualeSconto;

	}

	public int getPercentualeSconto() {
		return sconto;
	}

	public boolean isDaFrigo(){
		if(this instanceof ProdottoDaFrigo){
			return true;
		}else{
			return false;
		}
	}

	

	public int compareTo(Prodotto altro) {
		
		if(altro.getPrezzoListino() == this.getPrezzoListino()){
			return 0;
		}
		
		if(altro.getPrezzoListino() > this.getPrezzoListino()){
			return 1;
		}
		
		else {//if(altro.getPrezzoListino() < this.getPrezzoListino()){
			return -1;
			
		}
		
	}
	
}
