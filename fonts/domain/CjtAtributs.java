package fonts.domain;

import java.util.ArrayList;

public class CjtAtributs {

    private ArrayList<Atribut> _attrs;

    public CjtAtributs() {
        _attrs = new ArrayList<Atribut>();
    }

    public ArrayList<Atribut> getAtributs() {
        return _attrs;
    }

    public void afegirAtribut(Atribut attr) {
        _attrs.add(attr);
    }

    public void modificarNomAtribut(String nom, int index) {
        _attrs.get(index).setNom(nom);
    }

    public Atribut getAtribut(int index){
        return _attrs.get(index);
    }

    public void eliminarAtribut(int index) {
            _attrs.remove(index);
    }

    public ArrayList<String> getDades(int index) {
        ArrayList<String> aux = new ArrayList<>();
        Atribut a = _attrs.get(index);
        aux.add(a.getNom());
        if(a instanceof AtributBoolea){
            aux.add("Boolea");
            return aux;
        }
        else if(a instanceof  AtributCategoric){
            aux.add("Categoric");
            aux.addAll(((AtributCategoric) a).getPosiblesValors());
            return aux;
        }
        else if(a instanceof  AtributNumeric){
            aux.add("Numeric");
            aux.addAll(((AtributNumeric) a).getDades());
            return aux;
        }
        return aux;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for(Atribut atr: _attrs) s.append(atr).append(" ");
        return s.toString();
    }
}
