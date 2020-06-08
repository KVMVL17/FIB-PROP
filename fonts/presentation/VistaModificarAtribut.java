package fonts.presentation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

public class VistaModificarAtribut extends JFrame{
    private CtrlPresentacio cp;
    private JPanel rootPanel;
    private JButton accept;
    private JTable taula;
    private JTextField textNom;
    private JLabel nomAtr;
    private JButton changeName;
    private JLabel tipoAtrb;
    private JLabel tipo;
    private JTextField textInterv;
    private JButton delete;
    private JButton update;
    private JButton afegir;
    private JLabel interLabel;
    private DefaultTableModel model;
    private boolean numeric;
    private int index;

    public VistaModificarAtribut(CtrlPresentacio cp){
        this.cp = cp;
        add(rootPanel);
        setTitle("Consultar i modificar atribut");
        setSize(450, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();

        afegir.addActionListener(actionEvent -> afegirInterval(textInterv.getText()));
        delete.addActionListener(actionEvent -> eliminarInterval(taula.getSelectedRow()));
        update.addActionListener(actionEvent -> modificarInterval(textInterv.getText(),taula.getSelectedRow()));
        changeName.addActionListener(actionEvent -> modificarNom(textNom.getText()));
        taula.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Vector aux = model.getDataVector().get(taula.getSelectedRow());
                String aux2 = String.join(",",aux);
                textInterv.setText(aux2);
            }
        });
        accept.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                cp.actualitzarVGA();
                dispose();
            }
        });
    }

    private void modificarNom(String nomNou) {
        cp.modificarNomAtribut(nomNou, index);
        JOptionPane.showMessageDialog(this, "S'ha canviat correctament.");
    }

    private void afegirInterval(String text){
        ArrayList<String> aux = new ArrayList<>(Arrays.asList(text.split(",")));
        for(int i = 0; i < aux.size(); ++i){
            if(aux.get(i).isEmpty()){
                JOptionPane.showMessageDialog(this, "Atenció: Faltan valors");
                return;
            }
        }
        if(numeric){
            if((aux.get(0).compareTo(aux.get(1))) > 0){
                JOptionPane.showMessageDialog(this, "Atenció: Intervals incorrectes");
                return;
            }
            else cp.afegirInterval(index, new ArrayList<String>(aux.subList(0,3)), numeric);
        }
        else cp.afegirInterval(index, new ArrayList<String>(aux.subList(0,1)), numeric);
        textInterv.setText("");
        Object[] a = text.split(",");
        model.addRow(a);
    }

    private void modificarInterval(String text, int indexInterv) {

        if (indexInterv == -1) JOptionPane.showMessageDialog(this, "Selecciona una fila");
        else {
            ArrayList<String> aux = new ArrayList<>(Arrays.asList(text.split(",")));
            for (int i = 0; i < aux.size(); ++i) {
                if (aux.get(i).isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Faltan valors");
                    return;
                }
            }
            if (numeric) {
                if ((aux.get(0).compareTo(aux.get(1))) > 0) {
                    JOptionPane.showMessageDialog(this, "Atenció: Intervals incorrectes");
                    return;
                } else cp.modificarInterval(index, indexInterv, new ArrayList<String>(aux.subList(0, 3)), numeric);
            }
            else cp.modificarInterval(index, indexInterv, new ArrayList<String>(aux.subList(0, 1)), numeric);
            textInterv.setText("");
            Object[] a = aux.toArray();
            int column = 0;
            for (Object cellValue : a) {
                model.setValueAt(cellValue, indexInterv, column);
                column++;
            }
        }
    }

    private void eliminarInterval(int row) {
        if(row == -1) JOptionPane.showMessageDialog(this, "Selecciona una fila");
        else {
            cp.eliminarInterval(index, row, numeric);
            textInterv.setText("");
            model.removeRow(row);
        }
    }

    public void activar(int index, ArrayList<String> dades){
        this.index = index;
        numeric = false;
        activarVista(dades);
        setVisible(true);
    }

    private void activarVista(ArrayList<String> dades) {
        textNom.setText(dades.get(0));
        String tipoaux = dades.get(1);
        dades.remove(0);
        dades.remove(0);
        tipo.setText(tipoaux);
        if(tipoaux.equals("Boolea")){
            taula.setVisible(false);
            afegir.setVisible(false);
            delete.setVisible(false);
            update.setVisible(false);
            interLabel.setVisible(false);
            textInterv.setVisible(false);

        }
        else if(tipoaux.equals("Categoric")){
            taula.setModel(new DefaultTableModel(null, new String[]{"Possibles valors"}));
            model = (DefaultTableModel) taula.getModel();
            for(String valor : dades){
                Object x[] = {valor};
                model.addRow(x);
            }
            interLabel.setText("Valor possible");
        }
        else{
            taula.setModel(new DefaultTableModel(null, new String[]{"Lower", "Upper", "Assignacio"}));
            model = (DefaultTableModel) taula.getModel();
            for(int i = 0; i < dades.size();){
                model.addRow(dades.subList(i, i+3).toArray());
                i += 3;
            }
            numeric = true;
        }
    }

}
