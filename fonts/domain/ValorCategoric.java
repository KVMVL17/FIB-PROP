package fonts.domain;

public class ValorCategoric extends Valor {

    private String valor;
    private AtributCategoric ac;

    public ValorCategoric(String valor, AtributCategoric ac){
        this.valor = valor;
        this.ac = ac;
    }

    @Override
    public String getValor() {
        return valor;
    }

    @Override
    public AtributCategoric getAtribut() {
        return ac;
    }

    public void setAtribut(AtributCategoric ac) {
        this.ac = ac;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    @Override
    public String discretitzar() {
        return valor;
    }

}
