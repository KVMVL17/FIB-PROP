package fonts.presentation;

import fonts.domain.CtrlDomini;

import javax.swing.*;
import java.util.ArrayList;

public class CtrlPresentacio {

    private final CtrlDomini cd;
    private final VistaPrincipal vp;
    private final VistaCarregarFitxer vcf;
    private final VistaMode vm;
    private final VistaGestionarAtributs vga;
    private final VistaCarregarDirectori vcd;
    private final VistaGestionarRegistres vgr;
    private final VistaInferirRegles vir;
    private String path;


    public CtrlPresentacio() {
        cd = new CtrlDomini();
        vp = new VistaPrincipal(this);
        vcf = new VistaCarregarFitxer(this);
        vm = new VistaMode(this);
        vga = new VistaGestionarAtributs(this);
        vcd = new VistaCarregarDirectori(this);
        vgr = new VistaGestionarRegistres(this);
        vir = new VistaInferirRegles(this);
    }

    public void inicialitzar() {
        vp.activar();
    }

    public void syncVP_VM() {
        path = vcd.chooseDirectoryToSave();
        if(path != null) {
            if(cd.crearDirectori(path)) {
                JOptionPane.showMessageDialog(null, "Directori creat amb èxit a " + path);
            }
            vp.desactivar();
            vm.activar();
        }
    }

    public void carregarProjecte() {
        path = vcd.chooseDirectoryToUse();
        if (path != null) {
            String config = path + "/config.csv";
            String data = path + "/dades.csv";
            if (cd.noExisteixArchiu(config) || cd.noExisteixArchiu(data))
                JOptionPane.showMessageDialog(null, "No hi ha el config.csv i dades.csv dins d'aquest directori");
            else {
                int select = JOptionPane.showConfirmDialog(null, "Vols canviar la configuració del projecte?",
                                                "Canviar configuració", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                if(select == JOptionPane.NO_OPTION) {
                    cd.carregarProjecte(config);
                    vga.activar();
                }
                else if(select == JOptionPane.YES_OPTION) {
                    vp.desactivar();
                    syncModeAutomatic(data);
                }
            }
        }
    }

    public void syncModeManual() {
        vm.desactivar();
        vga.activar();
    }

    public void escullArxiu() {
        String file = vcf.chooseFile();
        syncModeAutomatic(file);
    }

    public void syncModeAutomatic(String file) {
        if(file != null) {
            String[] attrs = cd.carregarAtributs(file);
            VistaPopUp vpop = new VistaPopUp(this);
            vm.desactivar();
            for (String attr : attrs) {
                vpop.setNom(attr);
                vpop.activar();
            }
            vga.activar();
            cd.carregarRegistres(file);
        }
    }

    public void sync_modificarAtribut(int index) {
        VistaModificarAtribut vma = new VistaModificarAtribut(this);
        vma.activar(index, cd.getDadesAtribut(index));
    }

    public void syncVGA_VGR() {
        vga.desactivar();
        vgr.activar();
    }

    public void syncVGR_VGA() {
        vgr.desactivar();
        vga.activar();
    }

    public void syncVGR_VIR() {
        vgr.desactivar();
        cd.inferirRegles();
        vir.activar();
    }
    public void syncVIR_VMR(String regla, int index) {
        VistaModificarRegla vmr = new VistaModificarRegla(this,regla,index);
        vmr.activar();
    }

    public void actualitzarVGA() {
        vga.desactivar();
        vga.activar();
    }

    //ATRIBUTS

    public void eliminarAtribut(int index) {
        cd.eliminarAtribut(index);
    }

    public void afegirAtribut(String nom) {
        VistaPopUp vpop = new VistaPopUp(this);
        vpop.setNom(nom);
        vpop.activar();
    }

    public ArrayList<String> mostraAtributs() {
        return cd.retornaCjtAtributs();
    }

    public void afegirAtributDecimal(String nom, ArrayList<String> ints) {
        cd.crearAtributNumeric(nom, ints);
    }

    public void afegirAtributCategoric(String nom, ArrayList<String> vals) {
        cd.crearAtributCategoric(nom, vals);
    }

    public void afegirAtributBoolea(String nom) {
        cd.crearAtributBoolea(nom);
    }

    public void modificarNomAtribut(String nouNom, int index){
        cd.modificarNomAtribut(nouNom, index);
    }

    public void afegirInterval(int index, ArrayList<String> interv, boolean numeric){
        cd.afegirInterval(index, interv, numeric);
    }

    public void eliminarInterval(int indexAtrb, int indexInterv, boolean numeric){
        cd.eliminarInterval(indexAtrb, indexInterv, numeric);
    }

    public void modificarInterval(int index, int indexInterv, ArrayList<String> interv, boolean numeric){
        cd.modificarInterval(index, indexInterv, interv, numeric);
    }

    //REGISTRES

    public ArrayList<ArrayList<String>> getRegistres() {
        return cd.retornaCjtRegistres();
    }

    public ArrayList<ArrayList<String>> getRegistresPrepros() {
        return cd.get_cjtR().discretizar();
    }

    public void afegirRegistre(ArrayList<String> regs){
        cd.crearRegistre(regs);
    }

    public void modificarRegistre(ArrayList<String> reg, int index){
        cd.modificarRegistre(reg,index);
    }

    public void eliminarRegistre(int index){ cd.eliminarRegistre(index);}

    public void guardarDades(boolean prepros) {
        cd.guardarDades(prepros, path);
    }

    //Inferir Regles

    public void aplicarAlgorisme(double sup, double conf) {
        cd.aplicarAlgorisme(sup, conf);
    }

    public ArrayList<String> mostraReglesAssociacio() {
        return cd.retornaCjtRA();
    }

    public String getPath() {
        return path;
    }

    public void validarRegles() {
        cd.validarRegles();
    }

    public void guardarRA(String supp, String conf) {
        cd.guardarRA(path, supp, conf);
    }

    public void actualitzarRegla(ArrayList<String> ante, int index) {
        cd.modificarAntecedent(index, ante);
        vir.actualizarRegles(ante,index);
    }

    public void eliminarRegles(int index) { cd.eliminarRegla(index); }

    public ArrayList<String> mostraAntecedentRegla(int index) { return cd.retornaAntecedentRegla(index); }

    public void obreManual() {
        cd.obreManual();
    }

    //Projecte

    public void guardarProjecte(){
        cd.guardarProjecte(path);
    }

}