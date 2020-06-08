package fonts.domain;

import java.util.ArrayList;

public class AtributCategoric extends Atribut {

    private ArrayList<String> _valors;

    public AtributCategoric(String nom, ArrayList<String> valors) {
       super(nom);
       _valors = valors;
   }

    public ArrayList<String> getValors() {
        return _valors;
    }

    public void modificarValor(int index, String nou_valor) {
       _valors.set(index, nou_valor);
    }

    public ArrayList<String> getPosiblesValors() {
        return _valors;
    }
}
