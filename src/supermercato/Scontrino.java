package supermercato;

import java.util.*;

public class Scontrino {
	
	private int codiceS;
	
	private Map<Prodotto,Integer> acquisti = new TreeMap<Prodotto,Integer>();
	
	
	public Scontrino(int codiceS) {
	
		this.codiceS = codiceS;
	}

	
	public Map<Prodotto, Integer> getAcquisti() {
		return acquisti;
	}

	public void setAcquisti(Prodotto p, int q) {
		acquisti.put(p, q);
	}

	

	public int getCodiceS() {
		return codiceS;
	}

	public void setCodiceS(int codiceS) {
		this.codiceS = codiceS;
	}

	
	
	

}
