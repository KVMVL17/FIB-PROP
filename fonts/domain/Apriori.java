package fonts.domain;

import java.util.*;

public class Apriori{

    private double suport, confiansa;
    private ArrayList<ArrayList<String>> dades;

	private HashMap<HashSet<String>,Integer> resultat;
	private HashMap<HashSet<String>,Integer> antecedent;

	

    public Apriori(double s, double c){
        this.suport = s;
        this.confiansa = c;
    }

    public ArrayList<ArrayList<String>> getDades() {
        return dades;
    }

    public void setDades(ArrayList<ArrayList<String>>dades) {
        this.dades = dades;
    }

    private ArrayList<HashSet<String>> trobarCand(ArrayList<HashSet<String>> li){
		ArrayList<HashSet<String>> lis = new ArrayList<HashSet<String>>();
		for(int i=0;i<li.size();i++){
			for(int j=i+1;j<li.size();j++){
				lis.add(unir(li.get(i),li.get(j)));
			}
		}
		return lis;
	}
	
    private HashSet<String> unir(HashSet<String> a,HashSet<String> b){
		HashSet<String> d = new HashSet<String>();
		for(String s:a){
			d.add(s);
		}
		for(String s:b){
			d.add(s);
		}
		return d;
	}

    private void generarCandidats(){

		ArrayList<HashSet<String>> temp = new ArrayList<HashSet<String>>();
		HashMap<HashSet<String>, Integer> tab = new HashMap<HashSet<String>, Integer>();
		antecedent = new HashMap<HashSet<String>, Integer>();
		resultat = new HashMap<HashSet<String>, Integer>();
		HashMap<HashSet<String>, Integer> antemp = tab = new HashMap<HashSet<String>, Integer>();

        int iteracio = 0;
        double _suport = dades.size()*(suport/100);

		while(true){
			if(iteracio == 0){

					for(int i=0;i<dades.size();i++){
						for(int j=0; j<dades.get(i).size(); ++j){
							String s = dades.get(i).get(j);
							if(!s.equals("NULL")){
								HashSet<String> aux = new HashSet<String>();
								aux.add(s);
								if(!tab.containsKey(aux)) tab.put(aux,1);
								else{
									Integer rep = tab.get(aux);
									rep++;
									tab.put(aux, rep);
								}
							}
						}
					}

					tab.entrySet().removeIf(entry -> entry.getValue() < _suport);

					HashSet<String> aux;

					for (Iterator<Map.Entry<HashSet<String>, Integer>> it = tab.entrySet().iterator(); it.hasNext(); ) {
						Map.Entry<HashSet<String>, Integer> entry = it.next();
						aux = new HashSet<String>();
						aux.addAll(entry.getKey());
						temp.add(aux);
					}
					antecedent = new HashMap<HashSet<String>, Integer>(tab);
			}
			else {

				ArrayList<HashSet<String>> tempCand = trobarCand(temp);
				if (tempCand.size() == 0) break;

				antemp = new HashMap<HashSet<String>, Integer>(tab);
				
				tab.clear();

				for (HashSet<String> h : tempCand) {
					if (h.size() == iteracio + 1) tab.put(h, 0);
				}

				for (Iterator<Map.Entry<HashSet<String>, Integer>> it = tab.entrySet().iterator(); it.hasNext(); ) {
					Map.Entry<HashSet<String>, Integer> entry = it.next();
					for (int i = 0; i < dades.size(); i++) {
						if (dades.get(i).containsAll(entry.getKey())) {
							Integer aux = tab.get(entry.getKey());
							aux++;
							tab.put(entry.getKey(), aux);
						}
					}
				}

				tab.entrySet().removeIf(entry -> entry.getValue() < _suport);
				if(!tab.isEmpty()){
					antecedent = new HashMap<HashSet<String>, Integer>(antemp);
					resultat = new HashMap<HashSet<String>, Integer>(tab);
				}
				else break;

				HashSet<String> aux;
				temp = new ArrayList<HashSet<String>>();

				for (Iterator<Map.Entry<HashSet<String>, Integer>> it = tab.entrySet().iterator(); it.hasNext(); ) {
					Map.Entry<HashSet<String>, Integer> entry = it.next();
					aux = new HashSet<String>();
					aux.addAll(entry.getKey());
					temp.add(aux);
				}
			}
				iteracio++;
		}

	}

	private ArrayList<ArrayList<String>> crearCombinacions(HashSet<String> a){
		ArrayList<ArrayList<String>> comb = new ArrayList<ArrayList<String>>();

		for(Iterator<String> it = a.iterator(); it.hasNext();){
			HashSet<String> copiaSenseElement = new HashSet<String>(a);
			String s = it.next();
			copiaSenseElement.remove(s);
			ArrayList<String> aux = new ArrayList<String>(copiaSenseElement);
			aux.add(s);
			comb.add(aux);
		}

		return comb;
	}

	private Integer trobarAntecedent(ArrayList<String> a){
		TreeSet<String> ord = new TreeSet<String>(a);
		for (Iterator<Map.Entry<HashSet<String>, Integer>> it = antecedent.entrySet().iterator(); it.hasNext();){
			Map.Entry<HashSet<String>, Integer> entry = it.next();
			if(entry.getKey().equals(ord)){
				return entry.getValue();
			}
		}
		return 0;
	}

    public CjtRA aplicarAlgorisme(){

			CjtRA regles = new CjtRA();
			generarCandidats();

			if(!resultat.isEmpty()){
				for(Iterator<Map.Entry<HashSet<String>, Integer>> it = resultat.entrySet().iterator(); it.hasNext();) {
					Map.Entry<HashSet<String>, Integer> entry = it.next();
					Integer support = entry.getValue();
					ArrayList<ArrayList<String>> comb = crearCombinacions(entry.getKey());
					
					for(int i = 0 ; i < comb.size(); ++i){
						ArrayList<String> ante = new ArrayList<String>(comb.get(i));
						ante.remove(ante.size()-1);
						Integer antSup = trobarAntecedent(ante);
						double antConf = (support/Double.valueOf(antSup))*100;
						if(antConf >= confiansa){
							double _suport = (support/ (double) dades.size()) * 100;
							String auxCons = comb.get(i).get(comb.get(i).size()-1);
							RA novaRegla = new RA(ante, auxCons, _suport, antConf);
							regles.afegirRegla(novaRegla);
						}
					}
				}
			}
			return regles;
    }
}
