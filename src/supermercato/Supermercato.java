package supermercato;

import java.util.*;
import java.lang.*;
import java.io.*;

public class Supermercato extends Exception{
	
	//private List<Corsia> listacorsie = new ArrayList<Corsia>();
	private Map<String,Corsia> corsie = new TreeMap<String,Corsia>();
	
	private Map<String,Prodotto> prodotti = new TreeMap<String,Prodotto>();
	
	private List<Prodotto> listaprodotti = new ArrayList<Prodotto>();
	
	
	private int numeroscontrini =0;
	
	private Map<Integer,Scontrino> scontrini = new TreeMap<Integer,Scontrino>();

	public void aggiungiCorsia(String nome, int capienzaMassima){
		
		Corsia ctemp = new Corsia(nome,capienzaMassima);
		
		//listacorsie.add(ctemp);
		corsie.put(nome, ctemp);

	}
	
	public Prodotto catalogaProdotto(String codiceProdotto, String nomeProdotto, int volume, boolean daFrigo){
		
		if(prodotti.containsKey(codiceProdotto))
			return prodotti.get(codiceProdotto);
		
		if(daFrigo){
			ProdottoDaFrigo pdf = new ProdottoDaFrigo(codiceProdotto,nomeProdotto,volume);
			prodotti.put(codiceProdotto, pdf);
			listaprodotti.add(pdf);
			return pdf;
		}
		
		if(daFrigo==false){
			Prodotto p = new Prodotto(codiceProdotto,nomeProdotto,volume,daFrigo);
			prodotti.put(codiceProdotto, p);
			listaprodotti.add(p);
			return p;
		}
		
		return null;
	}	
	
	public Collection<Prodotto> elencoProdotti(){
		Collections.sort(listaprodotti);
		return listaprodotti; 
	}
	
	public Prodotto cercaProdotto(String codiceProdotto)throws ProdottoInesistenteException{
		
		if(prodotti.containsKey(codiceProdotto)){
			return prodotti.get(codiceProdotto);
		}else{
			
			throw new ProdottoInesistenteException();
			
		}
		
	}
	
	public void esponiProdotto(String nomeCorsia, Prodotto prodotto, int quantita){
		int spaziodisponibile = corsie.get(nomeCorsia).getCapienzaRimasta();
		int spaziorichiesto = prodotto.getVolume() * quantita ;
		
		if(spaziorichiesto<=spaziodisponibile){
			
			prodotto.setQuantitaesposta(quantita);
		
			corsie.get(nomeCorsia).setCapienzaRimasta(spaziorichiesto);
			
			corsie.get(nomeCorsia).aggiungiPaC(prodotto.getCodice());
			
			prodotto.setQuantita(nomeCorsia, quantita);
		}else{
			int quantitaccettabile = (int)Math.floor(spaziodisponibile/(prodotto.getVolume()));
			if(quantitaccettabile==0) return;
			prodotto.setQuantitaesposta(quantitaccettabile);
			spaziorichiesto = (prodotto.getVolume() * quantitaccettabile);
			corsie.get(nomeCorsia).setCapienzaRimasta(spaziorichiesto);
			
			corsie.get(nomeCorsia).aggiungiPaC(prodotto.getCodice());
			
			prodotto.setQuantita(nomeCorsia, quantitaccettabile);
			
		}
		
		
	}

	public int calcolaPercentualeDiOccupazione(String nomeCorsia){
		
		double capienzatot = corsie.get(nomeCorsia).getCapienzamax();
		double capienzausata = capienzatot - corsie.get(nomeCorsia).getCapienzaRimasta();
		System.out.println(capienzatot/capienzausata);
		return (int)((capienzausata/capienzatot)*100);
		
		
	}
	
	public Collection<String> elencoCodiciProdottoPerCorsia(String nomeCorsia){
		
		List<String> pr = corsie.get(nomeCorsia).getProdottiC();
		ComparatoreDiProdottiPerCodice cpc = new ComparatoreDiProdottiPerCodice();
		
		Collections.sort(pr,cpc);
		
		return pr;
	}

	public int quantitaProdottoEsposto(Prodotto prodotto, String nomeCorsia){
		
		
		return prodotto.getQuantita(nomeCorsia);
	}
	
	public int quantitaProdottoEsposto(Prodotto prodotto){
		return prodotto.getQuantitaesposta();
	}
	
	public int apriScontrino(){
		
		int codice = 1000+numeroscontrini;
		
		Scontrino s = new Scontrino(codice);
				
		numeroscontrini++;
		
		scontrini.put(codice,s);
		
		return codice;
	}
	
	public void acquistaProdotto(int codiceScontrino, Prodotto prodotto, String nomeCorsia, int quantita)throws CorsiaInesistenteException{
		if(prodotto.getQuantita().containsKey(nomeCorsia)==false){
			throw new CorsiaInesistenteException();
		}
		int q = (int) Math.min(quantita, prodotto.getQuantita(nomeCorsia));
			scontrini.get(codiceScontrino).setAcquisti(prodotto, q);
			prodotto.aggiornaQuantita(nomeCorsia, q);
	}
	
	public String dettagliScontrino(int codiceScontrino){
		
		String risultato = "";
		
		risultato += codiceScontrino + "\n";
		
		int iterazioni=0;
		
		Collection<Scontrino> listas = new ArrayList<Scontrino>();
		
		listas = scontrini.values();
		
		for(Scontrino stemp : listas){
			for(Prodotto ptemp : stemp.getAcquisti().keySet()){
				
				String codiceP = ptemp.getCodice();
				
				risultato += (codiceP + " " + stemp.getAcquisti().get(ptemp));
				
				iterazioni++;
				
				if(iterazioni!=stemp.getAcquisti().keySet().size()){
					risultato += "\n";
					
				}
				
			}
			
		}
		
		return risultato;
	}
	
	public double chiudiScontrino(int codiceScontrino) throws ProdottoInesistenteException{
		
		Scontrino stemp = scontrini.get(codiceScontrino);
		double totale = 0 ;
		
		for(Prodotto ptemp : stemp.getAcquisti().keySet()){
			double costounitario = ptemp.getPrezzoListino() - (ptemp.getPrezzoListino()*ptemp.getPercentualeSconto()/100);
			int quantita = scontrini.get(codiceScontrino).getAcquisti().get(ptemp);
			totale += costounitario*quantita;
			System.out.println(quantita+" x "+ptemp.getNome()+" Prezzo: "+ptemp.getPrezzoListino()+
									" Sconto "+ptemp.getPercentualeSconto()+ " Unit "+ costounitario);
			System.out.println(totale);
		}
		
		
		return totale;
	}
	
	public void leggiFile(String nomeFile) throws IOException {
		
		Reader r = new FileReader(nomeFile);
		
		BufferedReader br = new BufferedReader ( r );
		
		String riga ;
		
		
		while((riga=br.readLine())!=null){
			
			StringTokenizer st = new StringTokenizer(riga, ";");
			String tipo = st.nextToken();
			
			if (("PRODOTTO_NO_FRIGO".equals(tipo))){
				
				String codice = st.nextToken().trim();
				String nome = st.nextToken().trim();
				String volume = st.nextToken().trim();
				String prezzo = st.nextToken().trim();
				String percentuale = st.nextToken().trim();
				int volumei = Integer.parseInt(volume);
				Prodotto p = catalogaProdotto(codice,nome,volumei,false);
				p.setPrezzoListino(Double.parseDouble(prezzo));
				p.setPercentualeSconto(Integer.parseInt(percentuale));
			}
			
			if ("PRODOTTO_DA_FRIGO".equals(tipo)){
				
				String codice = st.nextToken().trim();
				String nome = st.nextToken().trim();
				String volume = st.nextToken().trim();
				String prezzo = st.nextToken().trim();
				String percentuale = st.nextToken().trim();
				String temperatura = st.nextToken().trim();
				
				int volumei = Integer.parseInt(volume);
				
				
				Prodotto p = catalogaProdotto(codice,nome,volumei,true);
				p.setPrezzoListino(Double.parseDouble(prezzo));
				p.setPercentualeSconto(Integer.parseInt(percentuale));
			}
			
			if ("ESPOSIZI_PRODOTTO".equals(tipo)){
				
				String codicep = st.nextToken().trim();
				String nomeC = st.nextToken().trim();
				String quantita = st.nextToken().trim();
				
				int quantitai = Integer.parseInt(quantita);
				
				Prodotto ptemp = prodotti.get(codicep);
				
				esponiProdotto(nomeC,ptemp,quantitai);
				
				
			}
			
		}
		
		br.close();

	}	
	
}
