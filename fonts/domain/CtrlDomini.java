package fonts.domain;

import java.util.ArrayList;
import java.util.Random;
import java.text.DecimalFormat;
import static java.lang.String.valueOf;

import fonts.persistence.CtrlPersistencia;


public class CtrlDomini {

    private final CjtAtributs _cjtA;
    private final CjtRegistres _cjtR;
    private final CtrlPersistencia cp;
    private CjtRA _cjtRA;
    private ArrayList<ArrayList<String>> _cjtR30;
    private ArrayList<ArrayList<String>> _cjtR70;

    public CtrlDomini() {
        _cjtA = new CjtAtributs();
        _cjtR = new CjtRegistres();
        _cjtR30 = new ArrayList<>();
        _cjtRA = new CjtRA();
        cp = new CtrlPersistencia();
    }

    public CjtRegistres get_cjtR() {
        return _cjtR;
    }

    //  ---GESTIONAR ATRIBUTS---


    public void crearAtributNumeric(String nomAtribut, ArrayList<String> ints) {
        ArrayList<Interval> intervals = new ArrayList<>();
        for(int i=0; i < ints.size(); i+=3){
            //[0] interval lower, [1] interval upper, [2] assignacio, [3] interval2 lower, etc.
            intervals.add(new Interval(Double.parseDouble(ints.get(i)),
                    Double.parseDouble(ints.get(i+1)), ints.get(i+2)));
        }
        _cjtA.afegirAtribut(new AtributNumeric(nomAtribut, intervals));
    }

    public void crearAtributCategoric(String nomAtribut, ArrayList<String> vals) {
        _cjtA.afegirAtribut(new AtributCategoric(nomAtribut, vals));
    }

    public void crearAtributBoolea(String nomAtribut) {
        _cjtA.afegirAtribut(new AtributBoolea(nomAtribut));
    }

    public void eliminarAtribut(int index) {
        _cjtA.eliminarAtribut(index);
        _cjtR.eliminarColumna(index);
    }

    public void modificarNomAtribut(String nom, int index) {
        _cjtA.modificarNomAtribut(nom, index);
    }

    public void afegirInterval(int index, ArrayList<String> interv, boolean numeric) {
        Atribut a = _cjtA.getAtribut(index);
        if(numeric) ((AtributNumeric)a).afegirInterval(interv);
        else ((AtributCategoric)a).getValors().addAll(interv);
    }

    public void modificarInterval(int index, int indexInterv, ArrayList<String> interv, boolean numeric) {
        Atribut a = _cjtA.getAtribut(index);
        if(numeric) ((AtributNumeric)a).modificarInterval(indexInterv, interv);
        else ((AtributCategoric)a).modificarValor(indexInterv, interv.get(0));
    }

    public void eliminarInterval(int indexAtrb, int indexInterv, boolean numeric) {
        Atribut a = _cjtA.getAtribut(indexAtrb);
        if(numeric) ((AtributNumeric)a).getIntervals().remove(indexInterv);
        else ((AtributCategoric)a).getValors().remove(indexInterv);
    }

    public ArrayList<String> getDadesAtribut(int index){
        return _cjtA.getDades(index);
    }

    //  ---GESTIONAR REGISTROS ---

    public void crearRegistre(ArrayList<String> nou) {
        Registre b = new Registre();
        for(int i = 0; i < nou.size(); ++i) {
            b.afegirValor(nou.get(i), _cjtA.getAtributs().get(i));
        }
        _cjtR.afegirRegistre(b);
    }

    public void modificarRegistre(ArrayList<String> reg, int index) {
        Registre r = _cjtR.getRegistres().get(index);
        for (int i = 0; i < reg.size(); ++i) {
            if(i < r.getRegistre().size())
                r.modificarValor(reg.get(i),i);
            else
                r.afegirValor(reg.get(i), _cjtA.getAtributs().get(i));
        }
    }

    public void eliminarRegistre(int index) {
        if(index>=_cjtR.getRegistres().size())
            _cjtR.eliminarRegistre(index-_cjtR.getRegistres().size());
        else
            _cjtR.eliminarRegistre(index);
    }

    // ---GESTIONAR REGLAS---//



    public void inferirRegles() {
        _cjtR70 = _cjtR.discretizar();
        ArrayList<String> atrb = retornaCjtAtributs();

        for(ArrayList<String> fila: _cjtR70){
            for(int i = 0; i < fila.size(); ++i){
                fila.set(i, (atrb.get(i) + ": " + fila.get(i)));
            }
        }
        //Separar el cjt de regles en 70% i 30%
        _cjtR30 = new ArrayList<>();
        int count = 0;
        while(count < (_cjtR70.size()*0.3)){
            Random rand = new Random();
            int rand1 = rand.nextInt(_cjtR70.size());
            _cjtR30.add(_cjtR70.get(rand1));
            _cjtR70.remove(rand1);
            ++count;
        }
    }

    public void aplicarAlgorisme(double suport, double conf){
        Apriori ap = new Apriori(suport, conf);
        ap.setDades(_cjtR70);
        _cjtRA = ap.aplicarAlgorisme();
    }

    public void eliminarRegla(int index) {
        _cjtRA.eliminarRegla(index);
    }

    public void modificarAntecedent(int index, ArrayList<String> ante) {
        _cjtRA.modificarAntecedent(index, ante, _cjtR30);
    }

    public ArrayList<String> retornaAntecedentRegla(int index) {
        return _cjtRA.getRegles().get(index).getAntecedent();
    }

    public void validarRegles() {
        _cjtRA.validarRegles(_cjtR30);
    }

    // ---CARGAR DATOS DESDE FICHEROS---

    public String[] carregarAtributs(String filename) {
        return cp.llegirAtributs(filename);
    }

    public void carregarRegistres(String filename){
        ArrayList<ArrayList<String>> regs = cp.llegirRegistres(filename);
        for (ArrayList<String> reg : regs) {
            crearRegistre(reg);
        }
    }

    public void carregarProjecte(String filename){
        ArrayList<ArrayList<String>> dadesConfig = cp.llegirProjecte(filename);
        int numAtrib = Integer.parseInt(dadesConfig.get(0).get(0));
        dadesConfig.remove(0);
        for(int i = 0; i < numAtrib; i++){
            ArrayList<String> dadesAtribut = dadesConfig.get(i);
            if(dadesAtribut.get(1).equals("Boolea")){
                crearAtributBoolea(dadesAtribut.get(0));
            }
            else if(dadesAtribut.get(1).equals("Categoric")){
                String auxNom = dadesAtribut.get(0);
                dadesAtribut.remove(0);
                dadesAtribut.remove(0);
                crearAtributCategoric(auxNom, dadesAtribut);
            }
            else if(dadesAtribut.get(1).equals("Numeric")){
                String auxNom = dadesAtribut.get(0);
                dadesAtribut.remove(0); //esborar el nom
                dadesAtribut.remove(0); //esborar el tipus
                crearAtributNumeric(auxNom, dadesAtribut);
            }
        }
        for (int i = numAtrib; i < dadesConfig.size(); ++i) {
            crearRegistre(dadesConfig.get(i));
        }

    }

    public ArrayList<String> retornaCjtAtributs() {
        ArrayList<String> attrs = new ArrayList<>();
        for(int i = 0; i < _cjtA.getAtributs().size(); ++i) {
            attrs.add(_cjtA.getAtributs().get(i).getNom());
        }
        return attrs;
    }

    public ArrayList<ArrayList<String>> retornaCjtRegistres() {
        ArrayList<ArrayList<String>> regs = new ArrayList<>();
        for(Registre Raux : _cjtR.getRegistres()) {
            ArrayList<String> row = new ArrayList<>();
            for(Valor v : Raux.getRegistre()){
                row.add(v.getValor());
            }
            regs.add(row);
        }
        return regs;
    }

   public ArrayList<String> retornaCjtRA() {
        ArrayList<String> ras = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("#.00");
        for(RA ra : _cjtRA.getRegles()) {
            StringBuffer sb = new StringBuffer();
            sb.append("{" + ra.getAntecedent().get(0));
            for(int i=1; i<ra.getAntecedent().size();++i){
                sb.append(", ");
                sb.append(ra.getAntecedent().get(i));
            }
            sb.append("} -> {" + ra.getConsequent() + "}, Suport:" + valueOf(df.format(ra.getSuport())) + "%, Confiança:" +(df.format(ra.getConf())) + "%}" );

            String regla= sb.toString();

            ras.add(regla);
        }
        return ras;
    }

    public boolean crearDirectori(String path) {
        return cp.crearDirectori(path);
    }

    public void guardarDades(boolean prepos, String nameFile) {
        if(prepos)
            cp.guardarDadesPrepros(retornaCjtAtributs(),_cjtR.discretizar(), nameFile);
        else
            cp.guardarDades(retornaCjtAtributs(),retornaCjtRegistres(), nameFile);
    }

    public void guardarProjecte(String nameFile){
        ArrayList<ArrayList<String>> atrbConfig = new ArrayList<>();
        int numAtrb = _cjtA.getAtributs().size();
        for(int i = 0; i < numAtrb; ++i){
            atrbConfig.add(getDadesAtribut(i));
        }
        cp.guardarProjecte(numAtrb, atrbConfig, retornaCjtRegistres(), retornaCjtAtributs(), nameFile);
    }

    public boolean noExisteixArchiu(String path) {
        return !cp.existeixArchiu(path);
    }

    public void guardarRA(String filename, String supp, String conf) {
        cp.guardarRA(retornaCjtRA(), filename, supp, conf);
    }

    public void obreManual() {
        cp.obreManual();
    }
}
