package fonts.domain;

public abstract class Atribut {

    private String nom;

    public Atribut(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return this.nom;
    }
    
    public  void setNom(String n) {
        this.nom = n;
    }
}