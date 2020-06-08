package fonts.presentation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VistaMode extends JFrame{

    private JButton carregarDadesAutomaticButton;
    private JPanel rootPanel;
    private JButton carregarDadesManualButton;
    private JButton ajudaButton;
    private JButton sortirButton;

    public VistaMode(CtrlPresentacio cp) {
        add(rootPanel);

        setTitle("Escollir Mode");
        setSize(600, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        carregarDadesAutomaticButton.addActionListener(actionEvent -> cp.escullArxiu());
        carregarDadesManualButton.addActionListener(actionEvent -> cp.syncModeManual());
        sortirButton.addActionListener(actionEvent -> System.exit(0));
        ajudaButton.addActionListener(actionEvent -> cp.obreManual());
    }

    public void activar() {
        setVisible(true);
    }

    public void desactivar() {
        setVisible(false);
    }
}
