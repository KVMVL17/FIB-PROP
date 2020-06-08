package fonts.domain;

import java.util.ArrayList;

public class Registre{

    private ArrayList<Valor> valors;

    public Registre(){
        this.valors = new ArrayList<Valor>();
    }

    public ArrayList<Valor> getRegistre() {
        return valors;
    }

    public void afegirValor(String valor, Atribut a){
        if(a instanceof AtributNumeric) {
            valors.add(new ValorNumeric(valor, (AtributNumeric) a));
        }
        else if(a instanceof AtributCategoric) {
            valors.add(new ValorCategoric(valor, (AtributCategoric) a));
        }
        else {
            valors.add(new ValorBoolea(valor, (AtributBoolea) a));
        }
    }

    public void modificarValor(String nouValor, int index) {
        valors.get(index).setValor(nouValor);
    }

    public void eliminarValor(int index){
        valors.remove(index);
    }

    public ArrayList<String> discretizar() {
        ArrayList<String> arr = new ArrayList<>();
        for(int i = 0; i < valors.size(); ++i) {
            arr.add(valors.get(i).discretitzar());
        }
        return arr;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("");
        for(int i = 0; i < valors.size(); ++i) {
            s.append(i+1 + " - " + valors.get(i).getValor()).append(" ");
        }
        return s.toString();
    }
}