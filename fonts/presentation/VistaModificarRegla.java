package fonts.presentation;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class VistaModificarRegla extends JFrame {
    private CtrlPresentacio cp;
    private JList list1;
    private JButton modificarAntecedentButton;
    private JButton afegirAntecedentButton;
    private JButton eliminarAntecedentButton;
    private JButton modificarReglaButton;
    private JButton cancelButton;
    private JPanel rootPanel;
    private JTextField Regla;
    private JTextField ReglaMod;
    private DefaultListModel<String> model;

    public VistaModificarRegla(CtrlPresentacio cp, String regla, int index) {
        this.cp = cp;

        model = new DefaultListModel<String>();
        add(rootPanel);
        setSize(700, 600);
        Regla.setText("Regla inicial: " + regla);
        ReglaMod.setText("Regla modificada: " + regla);
        setLocationRelativeTo(null);
        setTitle("Modificar regla");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        model = new DefaultListModel<>();
        list1.setModel(model);
        list1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list1.setLayoutOrientation(JList.VERTICAL);

        ArrayList<String> ras = cp.mostraAntecedentRegla(index);
        for(int i=0; i<ras.size();++i) {
            model.addElement(ras.get(i));
        }

        afegirAntecedentButton.addActionListener(actionEvent -> {
            afegirAntecedent();
            actualitzar(regla);
        });
        modificarAntecedentButton.addActionListener(actionEvent -> {
            modificarAntecedent();
            actualitzar(regla);
        });
        eliminarAntecedentButton.addActionListener(actionEvent ->{
            eliminarAntecedent();
            actualitzar(regla);
        });
        cancelButton.addActionListener(actionEvent -> cancel());
        modificarReglaButton.addActionListener(actionEvent -> modificarRegla(index));
    }

    public  void modificarRegla(int index) {
        ArrayList<String> ante= new ArrayList<>();
        for(int i =0; i< model.size(); ++i){
            ante.add(model.getElementAt(i));
        }
        cp.actualitzarRegla(ante,index);
        dispose();

    }
    public  void eliminarAntecedent() {
        int index = list1.getSelectedIndex();
        if (index ==-1) JOptionPane.showMessageDialog(this, "Cap antecedent seleccionat");
        else if (JOptionPane.showConfirmDialog(this, "Segur que voleu eliminar aquest antecedent?") ==0){
            model.removeElementAt(index);
        }

    }

    public  void modificarAntecedent() {
        int index = list1.getSelectedIndex();
        if (index ==-1) JOptionPane.showMessageDialog(this, "Cap antecedent seleccionat");
        else{
            String[] atr = model.getElementAt(index).split(": ");
            String atribut = JOptionPane.showInputDialog("Introdueixi el nou valor de l'atribut " + atr[0]);
            if(atribut.isEmpty()) JOptionPane.showMessageDialog(this, "Introdueix un valor");
            else model.set(index, atr[0] + ": " +atribut);
        }
    }

    public  void afegirAntecedent() {
        JComboBox<String> atribut = new  JComboBox<String>();
        ArrayList<String> a = cp.mostraAtributs();
        for(String attr: a) {
            atribut.addItem(attr);
        }
        JTextField textvalor = new JTextField();
        Object[] fields = {"Selecciona atribut", atribut, "Introdueix valor atribut", textvalor};
        int b= JOptionPane.showConfirmDialog(this, fields, "Afegir antecedent", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if(b== JOptionPane.YES_OPTION) {
            String select = atribut.getItemAt(atribut.getSelectedIndex());
            String valor = textvalor.getText();
            if(valor.isEmpty()) JOptionPane.showMessageDialog(this, "Introdueix un valor");
            else model.addElement(select + ": " + valor);
        }
    }

    public  void actualitzar(String regla) {
        StringBuffer sb = new StringBuffer();
        sb.append("Regla modificada: {");
        if(model.size()>0) sb.append(model.getElementAt(0));
        for(int i =1; i< model.size(); ++i){
            sb.append(", ");
           sb.append(model.getElementAt(i));
        }
        sb.append("}");
        sb.append(regla.substring(regla.indexOf("->")));
        ReglaMod.setText(sb.toString());

    }
    public  void cancel() {
    dispose();
    }


    public  void activar() {
        this.setVisible(true);

    }



}
