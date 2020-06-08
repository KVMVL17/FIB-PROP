package fonts.persistence;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class FitxerRegles {

    public void guardarRA(ArrayList<String> ras, String filename, String supp, String conf) {
        FileWriter file = null;
        PrintWriter pw = null;
        try {
            file = new FileWriter(filename + "/regles" + supp + "_" + conf + ".txt");
            pw = new PrintWriter(file, true);
            for (String ra : ras) {
                pw.println(ra);
            }
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (file != null) {
                    file.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
