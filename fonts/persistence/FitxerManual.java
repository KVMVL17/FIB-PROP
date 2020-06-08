package fonts.persistence;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class FitxerManual {

    public void obreManual() {
        if (Desktop.isDesktopSupported()) {
            try {
                File file = new File("../docs/manual.pdf");
                Desktop.getDesktop().open(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
