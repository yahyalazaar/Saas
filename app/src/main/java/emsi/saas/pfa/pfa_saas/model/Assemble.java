package emsi.saas.pfa.pfa_saas.model;

public class Assemble {
    private int id_assemble;
    private String nom_propriete;
    private String date_assemble;
    private int duree_assemble;

    public Assemble(int id_assemble, String nom_propriete, String date_assemble, int duree_assemble) {
        this.id_assemble = id_assemble;
        this.nom_propriete = nom_propriete;
        this.date_assemble = date_assemble;
        this.duree_assemble = duree_assemble;
    }

    public int getId_assemble() {
        return id_assemble;
    }

    public void setId_assemble(int id_assemble) {
        this.id_assemble = id_assemble;
    }

    public String getNom_propriete() {
        return nom_propriete;
    }

    public void setNom_propriete(String nom_propriete) {
        this.nom_propriete = nom_propriete;
    }

    public String getDate_assemble() {
        return date_assemble;
    }

    public void setDate_assemble(String date_assemble) {
        this.date_assemble = date_assemble;
    }

    public int getDuree_assemble() {
        return duree_assemble;
    }

    public void setDuree_assemble(int duree_assemble) {
        this.duree_assemble = duree_assemble;
    }
}

