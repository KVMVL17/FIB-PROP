package fonts.domain;

import java.util.ArrayList;

public class AtributNumeric extends Atribut {

    private ArrayList<Interval> _ints;

    public AtributNumeric(String nom, ArrayList<Interval> ints) {
        super(nom);
        _ints = ints;
    }

    public ArrayList<String> getDades() {
        ArrayList<String> aux = new ArrayList<>();
        for(Interval inter: _ints){
            aux.addAll(inter.getDades());
        }
        return aux;
    }

    public ArrayList<Interval> getIntervals() {
        return _ints;
    }

    public void afegirInterval(ArrayList<String> interv) {
        Interval nouInterv = new Interval(Double.parseDouble(interv.get(0)), Double.parseDouble(interv.get(1)), interv.get(2));
        _ints.add(nouInterv);
    }

    public void modificarInterval(int index, ArrayList<String> interv) {
        _ints.get(index).setIntervalLow(Double.parseDouble(interv.get(0)));
        _ints.get(index).setIntervalUp(Double.parseDouble(interv.get(1)));
        _ints.get(index).setAssig(interv.get(2));
    }

}
