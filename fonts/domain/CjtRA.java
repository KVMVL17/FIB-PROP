package fonts.domain;

import java.util.ArrayList;
import java.text.DecimalFormat;

public class CjtRA {

	private ArrayList<RA> regles;

	public CjtRA() {
		regles = new ArrayList<RA>();
	}

	public ArrayList<RA> getRegles() {
		return regles;
	}

	public void afegirRegla(RA ra) {
		int pos=-1;
		int size= regles.size();
        for(int i = 0; i < size; ++i) {
            if(regles.get(i).getAntecedent().equals(ra.getAntecedent()) && regles.get(i).getConsequent().equals(ra.getConsequent())) pos=i;
        }
        if(pos == -1) { 
        	regles.add(ra);
        }
        
    }

	public void eliminarRegla(int index) {
		if(index > regles.size() - 1 || index < 0) {
			
		}
		else {
			regles.remove(index);
		}
	}

	public void modificarAntecedent(int index, ArrayList<String> ante, ArrayList<ArrayList<String>> dades) {
		regles.get(index).setAntecedent(ante);
		//validarRegla(index, dades);
		regles.get(index).validarRegla(dades);
	}

	public void afegirAntecedent(int index, String ante) {
		regles.get(index).afegirAntecedent(ante);
	}

	public void modificarConsequent(int index, String cons) {
		regles.get(index).setConsequent(cons);
	}

	public void validarRegles(ArrayList<ArrayList<String>> dades) {
		int size= regles.size();
		for(int i = 0; i < size; ++i) {
			//validarRegla(i, dades);
			regles.get(i).validarRegla(dades);
		}
	}

}
