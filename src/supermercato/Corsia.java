package supermercato;

import java.util.*;

public class Corsia {
	
	private String nomeC;
	
	private int capienzamax;
	
	private int capienzarimasta;
	
	private List<String> prodottiC = new ArrayList<String>();
	
	
	
	public Corsia(String nomeC, int capienza) {
		
		this.nomeC = nomeC;
		this.capienzamax = capienza;
		this.capienzarimasta = capienza;
		
	}
	
	
	public void aggiungiPaC(String cod){
		prodottiC.add(cod);
	}
	
	public List<String> getProdottiC(){
		return prodottiC;
	}



	public int getCapienzaRimasta() {
		return capienzarimasta;
	}



	public void setCapienzaRimasta(int capienza) {
		capienzarimasta = capienzarimasta - capienza;
	}



	public int getCapienzamax() {
		return capienzamax;
	}



	public void setCapienzamax(int capienzamax) {
		this.capienzamax = capienzamax;
	}



	public String getNomeC() {
		return nomeC;
	}



	public void setNomeC(String nomeC) {
		this.nomeC = nomeC;
	}
	
	
	
	
	
	

}
