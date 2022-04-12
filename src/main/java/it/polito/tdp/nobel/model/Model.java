package it.polito.tdp.nobel.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.polito.tdp.nobel.db.EsameDAO;

public class Model {

	private List<Esame> esami;
	private Set<Esame> migliore;
	private double mediaMigliore;
	
	public Model() {
		EsameDAO dao = new EsameDAO();
		this.esami =  dao.getTuttiEsami();
	}
	
	public Set<Esame> calcolaSottoinsiemeEsami(int m) {
		migliore = new HashSet<Esame>();
		mediaMigliore = 0;
		
		
		Set<Esame> parziale = new HashSet<Esame>();
		cerca(parziale, 0, m);
		
		
		return migliore;	
	}
	
	//complessita' 2^N
	private void cerca(Set<Esame> parziale, int L, int m) {
		int sommaCrediti = sommaCrediti(parziale);
		
		if(sommaCrediti > m) {
			return;
		}
		
		if(sommaCrediti == m) {
			double mediaVoti = calcolaMedia(parziale);
			
			if(mediaVoti > mediaMigliore) {
				migliore = new HashSet<Esame>(parziale);
				mediaMigliore = mediaVoti;
			}
			
			return;
		}
		
		if(L == esami.size()) {
			return;
		}
		
		//sottoproblemi
		parziale.add(esami.get(L));		//aggiungo
		cerca(parziale, L+1, m);		//provo
		
		parziale.remove(esami.get(L));	//tolgo (per ogni livello provo a mettere e non mettere l'ellesimo esame)
		cerca(parziale, L+1, m);		//provo
	}

	//complessita' N!, esploro troppi casi inutili
	/*private void cerca(Set<Esame> parziale, int L, int m) {
		int sommaCrediti = sommaCrediti(parziale);
		
		if(sommaCrediti > m) {
			return;
		}
		
		if(sommaCrediti == m) {
			double mediaVoti = calcolaMedia(parziale);
			
			if(mediaVoti > mediaMigliore) {
				migliore = new HashSet<Esame>(parziale);
				mediaMigliore = mediaVoti;
			}
			
			return;
		}
		
		if(L == esami.size()) {
			return;
		}
		
		//sottoproblemi
		for(Esame e : esami) {
			if(!parziale.contains(e)) {
				parziale.add(e);			//aggiungo
				cerca(parziale, L+1, m);	//cerco
				parziale.remove(e);			//rimuovo (backtracking)
			}
		}
	}*/

	public double calcolaMedia(Set<Esame> esami) {
		
		int crediti = 0;
		int somma = 0;
		
		for(Esame e : esami){
			crediti += e.getCrediti();
			somma += (e.getVoto() * e.getCrediti());
		}
		
		return somma/crediti;
	}
	
	public int sommaCrediti(Set<Esame> esami) {
		int somma = 0;
		
		for(Esame e : esami)
			somma += e.getCrediti();
		
		return somma;
	}

}
