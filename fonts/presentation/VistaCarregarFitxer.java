package fonts.presentation;

import javax.swing.*;
public class VistaCarregarFitxer {

    private CtrlPresentacio cp;

    public VistaCarregarFitxer(CtrlPresentacio cp) {
        this.cp = cp;
    }

    public String chooseFile () {
        JFileChooser fileChooser = new JFileChooser();
        int returnVal = fileChooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().getAbsolutePath();
        }
        return null;
    }

}
