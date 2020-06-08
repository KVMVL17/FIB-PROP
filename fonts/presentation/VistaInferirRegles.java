package fonts.presentation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class VistaInferirRegles extends JFrame{

    private final CtrlPresentacio cp;
    private JPanel rootPanel;
    private JList<String> list1;
    private JButton inferirReglesButton;
    private JButton validarReglesButton;
    private JButton ajudaButton;
    private JTextField supp;
    private JTextField conf;
    private JButton guardarButton;
    private JButton modificarButton;
    private JButton eliminarButton;
    private JButton acabarButton;
    private JLabel LabelSup;
    private DefaultListModel<String> model;


    public VistaInferirRegles(CtrlPresentacio cp) {
        this.cp = cp;

        model = new DefaultListModel<>();
        add(rootPanel);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        inferirReglesButton.addActionListener(actionEvent -> inferirRegles());
        validarReglesButton.addActionListener(actionEvent -> validarRegles());
        guardarButton.addActionListener(actionEvent -> guardarRegles());
        eliminarButton.addActionListener(actionEvent -> eliminarRegles());
        modificarButton.addActionListener(actionEvent -> modificarRegles());
        acabarButton.addActionListener(actionEvent -> System.exit(0));
        ajudaButton.addActionListener(actionEvent -> cp.obreManual());
    }

    private void eliminarRegles() {

        int index = list1.getSelectedIndex();
        if (index ==-1) JOptionPane.showMessageDialog(null, "Cap regla seleccionada");
        else if (JOptionPane.showConfirmDialog(null, "Segur que voleu eliminar aquesta regla?") ==  JOptionPane.YES_OPTION){
            cp.eliminarRegles(index);
            model.removeElementAt(index);
        }
    }

    private void modificarRegles() {
        int index = list1.getSelectedIndex();
        if (index ==-1) JOptionPane.showMessageDialog(null, "Cap regla seleccionada");
        else {
            String[] regla = cp.mostraReglesAssociacio().get(index).split("}, Suport:");
            cp.syncVIR_VMR(regla[0] + "}", index);
        }

    }

    private void guardarRegles() {
        cp.guardarRA(supp.getText(), conf.getText());
        JOptionPane.showMessageDialog(null, "S'ha guardat en " + cp.getPath() + "/regles" + supp.getText() + "_" + conf.getText() + ".txt");
    }

    private void validarRegles() {
        cp.validarRegles();
        model.clear();
        afegirAtributsAJList();
        eliminarButton.setVisible(true);
        modificarButton.setVisible(true);
    }

    private void afegirAtributsAJList() {
        model = new DefaultListModel<>();
        list1.setModel(model);
        list1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list1.setLayoutOrientation(JList.VERTICAL);

        ArrayList<String> ras = cp.mostraReglesAssociacio();
        if(ras.isEmpty()) JOptionPane.showMessageDialog(null, "No s'ha trobat cap regla d'associació amb els paràmetres indicats");
        for(int i=0; i<ras.size();++i) {
            model.addElement(ras.get(i));
        }
    }

    private void inferirRegles() {
        double sup;
        double con;
        try {
            sup = Double.parseDouble(supp.getText());
            con = Double.parseDouble(conf.getText());
            cp.aplicarAlgorisme(sup, con);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Introdueix numeros");
        }
        afegirAtributsAJList();
    }

    public void actualizarRegles(ArrayList<String> ante, int index) {
        model.set(index, cp.mostraReglesAssociacio().get(index));

    }

    public void activar() {
        setVisible(true);
    }

    public void desactivar() {
        dispose();
    }
}