import supermercato.*;

import java.io.IOException;
import java.util.*;

public class Esempio {

	public static void main(String[] args) throws ProdottoInesistenteException, IOException, CorsiaInesistenteException {

		Supermercato s = new Supermercato();

		System.out.println("############# R1. Corsie e catalogo prodotti trattati #############");
		
		s.aggiungiCorsia("Pasta", 5500);
		s.aggiungiCorsia("Sughi", 3000);
		s.aggiungiCorsia("Prodotti vari", 10500);
		
		Prodotto p1 = s.catalogaProdotto("BAR0500RIG", "Rigatoni Barilla 1000g",1000, false);

		p1.setPrezzoListino(0.8);
		p1.setPercentualeSconto(10);

		Prodotto p2 = s.catalogaProdotto("AGN1000SPA", "Spaghetti Agnesi 500g",400, false);
		p2.setPrezzoListino(0.6);
		
		Prodotto p3 = s.catalogaProdotto("BUI0000PES", "Pesto Buitoni 125g",250, true);
		p3.setPrezzoListino(2.30);

		((ProdottoDaFrigo) p3).setTemperaturaDiConservazione(4);
		
		 			  s.catalogaProdotto("BAR0500RIG", "Rigatoni Barilla 500g", 500, false);

		List<Prodotto> prodotti = new LinkedList<Prodotto>(s.elencoProdotti());
		
		System.out.println("\nElenco dei prodotti catalogati (prezzo decrescente):");
		
		for (Prodotto ptemp : prodotti)
		{
			System.out.println(" "+ptemp.getCodice()+"\t"+ptemp.getNome()+"\t"+ptemp.getVolume()+" cm^3"+"\t"+ptemp.getPrezzoListino()+" euro"+"\t"+" (sconto "+ptemp.getPercentualeSconto()+"%)");
		}

		System.out.println("\n#################### R2. Esposizione prodotti #####################");
		
		s.esponiProdotto("Pasta", p1, 10);
		s.esponiProdotto("Pasta", p2, 25);
		s.esponiProdotto("Prodotti vari", p1, 50);
		s.esponiProdotto("Sughi", p3, 2);

		System.out.println("\nElenco dei codici prodotto esposti nella corsia Pasta (ordine alfabetico):");
		
		List<String> codiciCodiciProdottoCorsiaPasta = new LinkedList<String>(s.elencoCodiciProdottoPerCorsia("Pasta"));

		for (String ctemp : codiciCodiciProdottoCorsiaPasta)
		{
			System.out.println(" "+ctemp);
		}
		
		System.out.println("\nPercentuale di occupazione della corsia Pasta:");
		System.out.println(" "+s.calcolaPercentualeDiOccupazione("Pasta"));
		
		System.out.println("\nQuantita' di prodotto BAR0500RIG esposto nella corsia Pasta:");
		System.out.println(" "+s.quantitaProdottoEsposto(p1,"Pasta")+" pezzi");

		System.out.println("\nQuantita' di prodotto BAR0500RIG esposto nelle diverse corsie:");
		System.out.println(" "+s.quantitaProdottoEsposto(p1)+" pezzi");

		System.out.println("\n#################### R3. Acquisti e scontrini #####################");
		
		s.apriScontrino();
		s.acquistaProdotto(1000, p1, "Pasta", 5);
		s.acquistaProdotto(1000, p3, "Sughi", 3);
		
		System.out.println("\nDettagli scontrino sullo scontrino 1000:");
		System.out.println(s.dettagliScontrino(1000));
		
		double importo = s.chiudiScontrino(1000);
		System.out.println("\nImporto scontrino:");
		System.out.println(" "+importo);
		
		System.out.println("\n##################### R4. Caricamento da file #####################");
		
		System.out.println("\nLeggo altre informazioni da file e aggiungo alla struttura dati.");
		s.leggiFile("input.txt");

		System.out.println("\nElenco dei codici prodotto esposti nella corsia Prodotti vari (ordine alfabetico):");
		
		List<String> codiciProdottoCorsiaProdottiVari = new LinkedList<String>(s.elencoCodiciProdottoPerCorsia("Prodotti vari"));

		for (String ctemp : codiciProdottoCorsiaProdottiVari)
		{
			System.out.println(" "+ctemp);
		}
		
		Prodotto p4 = s.cercaProdotto("FIN0024BAS");
		
		System.out.println("\nQuantita' di prodotto FIN0024BAS esposto nella corsia Prodotti vari:");
		System.out.println(" "+s.quantitaProdottoEsposto(p4,"Prodotti vari")+" pezzi");
		
	}

}
