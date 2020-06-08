package fonts.domain;

import java.util.ArrayList;

public class CjtRegistres{
    
    private ArrayList<Registre> registres;

    public CjtRegistres() {
        registres = new ArrayList<Registre>();
    }

    public ArrayList<Registre> getRegistres() {
        return registres;
    }

    public Registre getRegistre(int index){
        return registres.get(index);
    }

    public void afegirRegistre(Registre b){
        registres.add(b);
    }

    public void eliminarRegistre(int index) {
        if(index < registres.size() && index >= 0) {
            registres.remove(index);
        }
    }

    public void eliminarColumna(int index){
        if((index < this.registres.size()) && (index >= 0)){
            for(int i = 0; i < registres.size(); ++i){
                registres.get(i).eliminarValor(index);
            }
        }
    }

    public ArrayList<ArrayList<String>> discretizar() {
        ArrayList<ArrayList<String>> cjtRegP = new ArrayList<ArrayList<String>>();

        for(int i = 0; i < registres.size(); ++i) {
            cjtRegP.add((registres.get(i).discretizar()));
        }
        return cjtRegP;
    }

}