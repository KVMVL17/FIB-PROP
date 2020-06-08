package fonts.domain;

import java.util.ArrayList;

public class ValorNumeric extends Valor {

    private double valor;
    private AtributNumeric an;

    public ValorNumeric(String valor, AtributNumeric an){
        this.valor = Double.parseDouble(valor);
        this.an = an;
    }

    @Override
    public String getValor() {
        return Double.toString(this.valor);
    }

    @Override
    public AtributNumeric getAtribut() {
        return an;
    }

    public void setAtribut(AtributNumeric an) {
        this.an = an;
    }

    public void setValor(String valor) {
        this.valor = Double.parseDouble(valor);
    }

    @Override
    public String discretitzar() {
        ArrayList<Interval> ints = an.getIntervals();
        for (Interval anInt : ints) {
            if (valor <= anInt.getUpper() && valor >= anInt.getLower())
                return anInt.getAssig();
        }
        return "NULL";
    }

}