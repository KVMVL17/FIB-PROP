package fonts.presentation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class VistaGestionarAtributs extends JFrame {

    private final CtrlPresentacio cp;
    private JButton cancelarButton;
    private JTextField nomAtribut;
    private JList<String> list1;
    private JButton modificarButton;
    private JButton eliminarButton;
    private JButton crearButton;
    private JPanel rootPanel;
    private JButton nextButton;
    private JLabel Titol;
    private DefaultListModel<String> model;

    public VistaGestionarAtributs(CtrlPresentacio cp) {
        this.cp = cp;
        model = new DefaultListModel<>();
        add(rootPanel);

        setTitle("Gestionar Atributs");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        crearButton.addActionListener(actionEvent -> afegirAModel(nomAtribut.getText()));
        eliminarButton.addActionListener(actionEvent -> eliminarDeModel(list1.getSelectedIndex()));
        nextButton.addActionListener(actionEvent -> cp.syncVGA_VGR());
        modificarButton.addActionListener(actionEvent -> modificarAtrb(list1.getSelectedIndex()));
        cancelarButton.addActionListener(actionEvent -> cp.obreManual());
    }

    public void activar() {
        setVisible(true);
        afegirAtributsAJList();
    }

    private void afegirAModel(String nom) {
        if(nom.equals(""))
            JOptionPane.showMessageDialog(this, "Introdueix un nom d'atribut");
        else {
            cp.afegirAtribut(nom);
            cp.actualitzarVGA();
        }
    }

    private void eliminarDeModel(int index) {
        if(index == -1) JOptionPane.showMessageDialog(this, "Selecciona un atribut");
        if(index != -1) {
            model.remove(index);
            cp.eliminarAtribut(index);
        }

    }

    private void modificarAtrb(int index){
        if(index == -1) JOptionPane.showMessageDialog(this, "Selecciona un atribut");
        if(index != -1) {
            cp.sync_modificarAtribut(index);
        }
    }

    private void afegirAtributsAJList() {
        model = new DefaultListModel<>();
        list1.setModel(model);
        list1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list1.setLayoutOrientation(JList.HORIZONTAL_WRAP);

        ArrayList<String> a = cp.mostraAtributs();
        for(String attr: a) {
            model.addElement(attr);
        }
    }

    public void desactivar() {
        setVisible(false);
    }
}
