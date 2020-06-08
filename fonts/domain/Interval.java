package fonts.domain;

import java.util.ArrayList;

public class Interval {

    private double lower, upper;
    private String assig;

    public Interval(double lower, double upper, String assig) {
        this.lower = lower;
        this.upper = upper;
        this.assig = assig;
    }

    public ArrayList<String> getDades(){
        ArrayList<String> aux = new ArrayList<>();
        aux.add(String.valueOf(lower));
        aux.add(String.valueOf(upper));
        aux.add(assig);
        return aux;
    }
    public String getAssig() {
        return assig;
    }

    public double getLower() {
        return lower;
    }

    public double getUpper() {
        return upper;
    }

    public void setIntervalLow(double lower) {
        this.lower = lower;
    }

    public void setIntervalUp(double upper){
        this.upper = upper;
    }

    public void setAssig(String nou) {
        this.assig = nou;
    }
}
