package fonts.presentation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Vector;

public class VistaGestionarRegistres extends JFrame{
    private CtrlPresentacio cp;
    private JPanel root;
    private JTable taula;
    private JButton back;
    private JButton save;
    private JButton next;
    private JTextField textReg;
    private JButton afegeix;
    private JButton update;
    private JButton delete;
    private JButton prepros;
    private JLabel LabelRegistre;
    private JLabel Titol;
    private JButton ajudaButton;
    private DefaultTableModel model;
    private boolean preproSave;

    public VistaGestionarRegistres(CtrlPresentacio cp){
        preproSave = false;
        add(root);
        setTitle("Gestionar Registres");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.cp = cp;

        afegeix.addActionListener(actionEvent -> afegirRegistre(textReg.getText()));
        back.addActionListener(actionEvent -> cp.syncVGR_VGA());
        update.addActionListener(actionEvent -> modificarRegistre(textReg.getText(),taula.getSelectedRow()));
        delete.addActionListener(actionEvent -> eliminarRegistre(taula.getSelectedRow()));
        prepros.addActionListener(actionEvent -> mostraPrepros());
        taula.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Vector aux = model.getDataVector().get(taula.getSelectedRow());
                String aux2 = String.join(",",aux);
                textReg.setText(aux2);
            }
        });
        save.addActionListener(actionEvent -> guardar());
        next.addActionListener(actionEvent -> cp.syncVGR_VIR());
        ajudaButton.addActionListener(actionEvent -> cp.obreManual());
    }

    private void eliminarRegistre(int row) {
        if(row == -1) JOptionPane.showMessageDialog(this, "Selecciona un registre");
        else {
            cp.eliminarRegistre(row);
            textReg.setText("");
            model.removeRow(row);
        }
    }

    private void modificarRegistre(String text, int index) {
        if(index == -1) JOptionPane.showMessageDialog(this, "Selecciona un registre");
        else {
            ArrayList<String> aux = new ArrayList<>(Arrays.asList(text.split(",")));
            cp.modificarRegistre(aux, index);
            textReg.setText("");
            afegirRegistresAJTable();
        }
    }

    private void afegirRegistre(String text) {
        if(text.isEmpty()) JOptionPane.showMessageDialog(this, "Introdueix valors");
        else {
            ArrayList<String> aux = new ArrayList<>(Arrays.asList(text.split(",")));
            cp.afegirRegistre(aux);
            textReg.setText("");
            Object[] a = text.split(",");
            model.addRow(a);
        }
    }

    private void mostraPrepros() {

        preproSave = true;
        String[] a = cp.mostraAtributs().toArray(String[]::new);
        taula.setModel(new DefaultTableModel(null, a));
        if(a.length > 15) taula.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        update.setVisible(false);
        afegeix.setVisible(false);
        delete.setVisible(false);
        textReg.setVisible(false);
        LabelRegistre.setVisible(false);
        Titol.setText("Dades preprocessades");

        //Carrgar els registres si en hi ha
        model = (DefaultTableModel) taula.getModel();
        ArrayList<ArrayList<String>> aux = cp.getRegistresPrepros();
        for(ArrayList<String> r : aux){
            model.addRow(r.toArray());
        }
    }

    public void activar() {
        preproSave = false;
        afegirRegistresAJTable();
        update.setVisible(true);
        afegeix.setVisible(true);
        delete.setVisible(true);
        textReg.setVisible(true);
        LabelRegistre.setVisible(true);
        textReg.setText("");
        Titol.setText("Gestionar Registres");

        setVisible(true);
    }

    private void afegirRegistresAJTable(){
        //Colocar els atributs als noms de les columnes.
        String[] a = cp.mostraAtributs().toArray(String[]::new);
        taula.setModel(new DefaultTableModel(null, a));
        if(a.length > 15) taula.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        //Carrgar els registres si en hi ha
        model = (DefaultTableModel) taula.getModel();
        ArrayList<ArrayList<String>> regs = cp.getRegistres();
        for(ArrayList<String> r : regs){
            model.addRow(r.toArray());
        }
    }

    public void guardar(){

        if(preproSave) {
            JOptionPane.showMessageDialog(this, "S'ha guardat en " + cp.getPath() + "/prep.csv");
            cp.guardarDades(preproSave);
        }
        else {
            cp.guardarProjecte();
            JOptionPane.showMessageDialog(this, "S'ha guardat " + cp.getPath() + "/config.csv i " + cp.getPath() + "/dades.csv");
        }
    }

    public void desactivar(){
        setVisible(false);
    }
}
