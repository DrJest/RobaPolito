package supermercato;

public class ProdottoDaFrigo extends Prodotto{
	
	private int temperatura;

	public ProdottoDaFrigo(String codice, String nomeP, int volume) {
		super(codice, nomeP, volume,true);
		
		
	}

	public int getTemperaturaDiConservazione(){
		return temperatura;
	}

	public void setTemperaturaDiConservazione(int temperaturaDiConservazione){
		this.temperatura = temperaturaDiConservazione;

	}

}
