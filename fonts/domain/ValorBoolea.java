package fonts.domain;

public class ValorBoolea extends Valor {

    private boolean valor;
    private AtributBoolea ab;

    public ValorBoolea(String valor, AtributBoolea ab){
        this.valor = (valor.equals("1"));
        this.ab = ab;
    }

    @Override
    public String getValor(){
        return (valor ? "1" : "0");
    }

    @Override
    public AtributBoolea getAtribut() {
        return ab;
    }

    public void setValor(String valor){
        this.valor = ((Integer.parseInt(valor)) == 1);
    }

    @Override
    public String discretitzar() {
        if(valor){
            return "True";
        }
        else return "False";
    }

}