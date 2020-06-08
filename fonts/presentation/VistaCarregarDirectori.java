package fonts.presentation;

import javax.swing.*;

public class VistaCarregarDirectori {

    private final CtrlPresentacio cp;

    public VistaCarregarDirectori(CtrlPresentacio cp) {
        this.cp = cp;
    }

    public String chooseDirectoryToUse () {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Escollir directori");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile().getAbsolutePath();
        }
        return null;
    }

    public String chooseDirectoryToSave() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Selecciona on guardar");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int userSelection = chooser.showSaveDialog(null);
        if(userSelection == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile().getAbsolutePath();
        }
        return null;
    }
}
