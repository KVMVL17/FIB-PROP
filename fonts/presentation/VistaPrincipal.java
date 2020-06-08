package fonts.presentation;

import javax.swing.*;

public class VistaPrincipal extends JFrame{
    private final CtrlPresentacio cp;
    private JButton ajudaButton;
    private JButton sortirButton;
    private JButton crearUnNouProjecteButton;
    private JButton carregarProjecteExistentButton;
    private JPanel rootPanel;

    public VistaPrincipal(CtrlPresentacio cp) {
        this.cp = cp;
        // Esto utiliza el form que hemos creado al designer
        add(rootPanel);
        setTitle("Generador de Regles d'Associacio");
        setSize(600, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        sortirButton.addActionListener(actionEvent -> System.exit(0));
        carregarProjecteExistentButton.addActionListener(actionEvent -> cp.carregarProjecte());
        crearUnNouProjecteButton.addActionListener(actionEvent -> cp.syncVP_VM());
        ajudaButton.addActionListener(actionEvent -> cp.obreManual());
    }

    public void activar() {
        setVisible(true);
    }

    public void desactivar() {
        setVisible(false);
    }
}
