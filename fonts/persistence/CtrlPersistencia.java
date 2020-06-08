package fonts.persistence;

import java.io.File;
import java.util.ArrayList;

public class CtrlPersistencia {

    private final FitxerDades fd;
    private final FitxerDadesPrep fp;
    private final FitxerRegles fr;

    public CtrlPersistencia() {
        fd = new FitxerDades();
        fp = new FitxerDadesPrep();
        fr = new FitxerRegles();
    }

    public String[] llegirAtributs(String filename) {
        return fd.llegirAtributs(filename);
    }

    public ArrayList<ArrayList<String>> llegirRegistres(String filename) {
        return fd.llegirRegistres(filename);
    }

    public ArrayList<ArrayList<String>> llegirProjecte(String filename) {
        return fd.llegirProjecte(filename);
    }

    public void guardarDadesPrepros(ArrayList<String> attrs, ArrayList<ArrayList<String>> Regs, String filename) {
        fp.guardarDadesPrepros(attrs, Regs, filename);
    }

    public void guardarDades(ArrayList<String> attrs, ArrayList<ArrayList<String>> Regs, String filename) {
        fd.guardarDades(attrs, Regs, filename);
    }

    public void guardarProjecte(int numAtrb, ArrayList<ArrayList<String>> atrbConfig, ArrayList<ArrayList<String>> CjtRegistres, ArrayList<String> attrs, String filename) {
        fd.guardarConfig(numAtrb, atrbConfig, CjtRegistres, filename);
        fd.guardarDades(attrs, CjtRegistres, filename);
    }

    public boolean crearDirectori(String path) {
        return new File(path).mkdirs();
    }

    public boolean existeixArchiu(String path) {
        return new File(path).exists();
    }

    public void guardarRA(ArrayList<String> ra, String filename, String supp, String conf) {
        fr.guardarRA(ra, filename, supp, conf);
    }

    public void obreManual() {
        FitxerManual fm = new FitxerManual();
        fm.obreManual();
    }
}
