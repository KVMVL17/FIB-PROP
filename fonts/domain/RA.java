package fonts.domain;

import java.util.ArrayList;

public class RA {

	private double suport,confianca;
	private ArrayList<String> antecedent;
	private String consequent;

	public RA(ArrayList<String> ante, String cons, double supo, double conf) {
		antecedent = ante;
		consequent = cons;
		suport = supo;
		confianca = conf;
	}

	public void modificarRegla(RA ra) {
		antecedent = ra.getAntecedent();
		consequent = ra.getConsequent();
		suport = ra.getSuport();
		confianca = ra.getConf();
	}

	public double getConf() {
		return confianca;
	}

	public void setConf(double conf) {
		confianca = conf;
	}

	public double getSuport() {
		return suport;
	}

	public void setSuport(double supo) {
		this.suport = supo;
	}


	public ArrayList<String> getAntecedent() {
		return antecedent;
	}

	public void setAntecedent(ArrayList<String> ante) {
		antecedent = ante;
	}

	public void afegirAntecedent(String ante) {
		antecedent.add(ante);
	}

	public String getConsequent() {
		return consequent;
	}

	public void setConsequent(String cons) {
		consequent = cons;
	}

	public void validarRegla(ArrayList<ArrayList<String>> dades){
		int columns = dades.get(0).size();
		int rows = dades.size();
		int auxsuport=0;
		int auxsuportante=0;
		for(int j=0; j < rows; ++j) {

			boolean trobatante=false;
			int antestrobats=0;
			boolean trobatcons=false;

			for(int k = 0; k < antecedent.size(); ++k) {
				for(int z=0; z < columns; ++z) {
					if(dades.get(j).get(z).equals(antecedent.get(k))) {
						++antestrobats;
					}

				}


			}
			if(antestrobats == antecedent.size()) {
				++auxsuportante;
				trobatante=true;

			}

			for(int z=0; z < columns; ++z) {
				if(dades.get(j).get(z).equals(consequent)) {
					trobatcons=true;
				}
			}
			if(trobatcons && trobatante) {
				++auxsuport;

			}


		}
		double row=rows;
		double suport= auxsuport/row;
		double suportante= auxsuportante/row;
		double conf = suport/suportante;
		setSuport(suport * 100);
		setConf(conf * 100);
	}
}
