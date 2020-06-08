package fonts.presentation;

import fonts.domain.Interval;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Objects;

public class VistaPopUp extends JDialog {

    private final CtrlPresentacio cp;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox<String> option;
    private JLabel nomAtribut;
    private JButton afegirButton;
    private JTextField lower;
    private JTextField upper;
    private JTextField assig;
    private JLabel lowerLabel;
    private JLabel upperLabel;
    private JLabel assigLabel;
    private ArrayList<String> ints = new ArrayList<String>();
    private ArrayList<String> vals = new ArrayList<>();



    public VistaPopUp(CtrlPresentacio cp) {
        this.cp = cp;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        pack();

        setSize(600, 450);
        setLocationRelativeTo(null);

        option.addItem("NUMERIC"); option.addItem("CATEGORIC"); option.addItem("CONDICIONAL");

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        option.addActionListener(actionEvent -> canviarDisseny());
        afegirButton.addActionListener(actionEvent -> afegirEspecific());
    }

    public void setNom(String nom) {
        nomAtribut.setText(nom);
    }

    public void activar() {
        setVisible(true);
    }

    private void canviarDisseny() {
        afegirButton.setVisible(true);
        if(Objects.equals(option.getSelectedItem(), "NUMERIC")) {
            lower.setVisible(true); lowerLabel.setVisible(true);
            assig.setVisible(true); assigLabel.setVisible(true);
            upper.setVisible(true); upperLabel.setVisible(true);
            upperLabel.setText("Limit superior");
            afegirButton.setText("Afegir interval");
        }
        else if(Objects.equals(option.getSelectedItem(), "CATEGORIC")) {
            lower.setVisible(false); lowerLabel.setVisible(false);
            assig.setVisible(false); assigLabel.setVisible(false);
            upper.setVisible(true); upperLabel.setVisible(true);
            upperLabel.setText("Possible valor");
            afegirButton.setText("Afegir possible valor");
        }
        else {
            lower.setVisible(false); lowerLabel.setVisible(false);
            assig.setVisible(false); assigLabel.setVisible(false);
            upper.setVisible(false); upperLabel.setVisible(false);
            afegirButton.setVisible(false);
        }
    }

    private void afegirEspecific() {
        if(campInvalid())
            JOptionPane.showMessageDialog(this, "Introdueix els camps necessaris");
        else {
            if (Objects.equals(option.getSelectedItem(), "NUMERIC")) {
                double l = Double.parseDouble(lower.getText());
                double u = Double.parseDouble(upper.getText());
                if (u < l)
                    JOptionPane.showMessageDialog(this, "Introdueix un interval vàlid");
                else {
                    ints.add(lower.getText());
                    ints.add(upper.getText());
                    ints.add(assig.getText());
                    JOptionPane.showMessageDialog(this, "Interval afegit correctament");
                    lower.setText(upper.getText());
                    upper.setText("");
                    assig.setText("");
                }
            }
            else if (Objects.equals(option.getSelectedItem(), "CATEGORIC")) {
                JOptionPane.showMessageDialog(this, "Valor afegit correctament");
                vals.add(upper.getText());
                upper.setText("");
            }
        }
    }

    private boolean campInvalid() {
        if(option.getSelectedItem().equals("NUMERIC"))
            return lower.getText().equals("") || upper.getText().equals("") || assig.getText().equals("");
        else
            return upper.getText().equals("");
    }

    private void onOK() {
        String opt = (String) option.getSelectedItem();

        if(opt.equals("NUMERIC")) {
            if(ints.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Introdueix al menys un interval valid");
                this.activar();
            }
            else {
                cp.afegirAtributDecimal(nomAtribut.getText(), ints);
                lower.setText(""); upper.setText(""); assig.setText("");
                dispose();
            }
        }
        else if(opt.equals("CATEGORIC")) {
                cp.afegirAtributCategoric(nomAtribut.getText(), vals);
                upper.setText("");
                dispose();
        }
        else {
            cp.afegirAtributBoolea(nomAtribut.getText());
            dispose();
        }

        ints = new ArrayList<String>();
        vals = new ArrayList<>();

        //dispose();
    }

    private void onCancel() {
        dispose();
    }

}