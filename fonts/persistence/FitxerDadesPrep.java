package fonts.persistence;

import java.io.*;
import java.util.ArrayList;

public class FitxerDadesPrep {

    public void guardarDadesPrepros(ArrayList<String> attrs, ArrayList<ArrayList<String>> regs, String filename){
        FileWriter file = null;
        PrintWriter pw = null;
        try {
            file = new FileWriter(filename + "/prep.csv");
            pw = new PrintWriter(file, true);
            pw.println(String.join(",", attrs));
            for (ArrayList<String> reg : regs) {
                pw.println(String.join(",", reg));
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
