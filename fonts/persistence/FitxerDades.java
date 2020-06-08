package fonts.persistence;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class FitxerDades {

	public String[] llegirAtributs(String filename) {
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        String[] attrs = new String[0];
        
        try {
            br = new BufferedReader(new FileReader(filename));
            line = br.readLine();
            attrs = line.split(cvsSplitBy);

        } catch (Exception e) {
            System.out.println("Error a l'introduir les dades. Els atributs no s'han pogut crear.");
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return attrs;
    }

    public ArrayList<ArrayList<String>> llegirRegistres(String filename) {
        BufferedReader br = null;
        String cvsSplitBy = ",";
        ArrayList<ArrayList<String>> regs = new ArrayList<>();
        try {
            br = new BufferedReader(new FileReader(filename));
            br.readLine(); //Esto lee la primera linea
            String line = null; //Hace que la primera linea no valga de nada
            while((line = br.readLine()) != null) {
                String[] vals = line.split(cvsSplitBy);
                regs.add(new ArrayList<>(Arrays.asList(vals)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return regs;
    }

    public ArrayList<ArrayList<String>> llegirProjecte(String filename) {
        BufferedReader br = null;
        String cvsSplitBy = ",";
        ArrayList<ArrayList<String>> regs = new ArrayList<>();
        try {
            br = new BufferedReader(new FileReader(filename));
            String line = null;
            while((line = br.readLine()) != null) {
                String[] vals = line.split(cvsSplitBy);
                regs.add(new ArrayList<>(Arrays.asList(vals)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return regs;
    }

    public void guardarDades(ArrayList<String> attrs, ArrayList<ArrayList<String>> regs, String filename){
        FileWriter file = null;
        PrintWriter pw = null;
        try {
            file = new FileWriter(filename + "/dades.csv");
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

    public void guardarConfig(int numAtrb, ArrayList<ArrayList<String>> atrbConfig, ArrayList<ArrayList<String>> regs, String filename) {
        FileWriter file = null;
        PrintWriter pw = null;
        try {
            file = new FileWriter(filename + "/config.csv");
            pw = new PrintWriter(file, true);
            pw.println(numAtrb);
            for (ArrayList<String> reg : atrbConfig) {
                pw.println(String.join(",", reg));
            }
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
