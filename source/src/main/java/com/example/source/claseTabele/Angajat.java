package com.example.source.claseTabele;

public class Angajat implements Cloneable{
    private int id;
    private int id_utilizator;
    private int id_policlinica;
    private String nume;
    private String prenume;
    private String functie;
    private int salariu_negociat;
    private int numar_ore;

    public Angajat(int id, int id_utilizator, int id_policlinica, String nume, String prenume, String functie, int salariu_negociat, int numar_ore) {
        this.id = id;
        this.id_utilizator = id_utilizator;
        this.id_policlinica = id_policlinica;
        this.nume = nume;
        this.prenume = prenume;
        this.functie = functie;
        this.salariu_negociat = salariu_negociat;
        this.numar_ore = numar_ore;
    }

    public int getId() {
        return id;
    }

    public int getId_utilizator() {
        return id_utilizator;
    }

    public String getNume() {
        return nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public String getFunctie() {
        return functie;
    }

    public int getId_policlinica() {
        return id_policlinica;
    }

    public int getSalariu_negociat() {
        return salariu_negociat;
    }

    public int getNumar_ore() {
        return numar_ore;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setId_utilizator(int id_utilizator) {
        this.id_utilizator = id_utilizator;
    }

    public void setId_policlinica(int id_policlinica) {
        this.id_policlinica = id_policlinica;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public void setFunctie(String functie) {
        this.functie = functie;
    }

    public void setSalariu_negociat(int salariu_negociat) {
        this.salariu_negociat = salariu_negociat;
    }

    public void setNumar_ore(int numar_ore) {
        this.numar_ore = numar_ore;
    }

    @Override
    public String toString() {
        return "Angajat{" +
                "id=" + id +
                ", id_utilizator=" + id_utilizator +
                ", id_policlinica=" + id_policlinica +
                ", nume='" + nume + '\'' +
                ", prenume='" + prenume + '\'' +
                ", functie='" + functie + '\'' +
                ", salariu_negociat=" + salariu_negociat +
                ", numar_ore=" + numar_ore +
                '}';
    }

    @Override
    public Angajat clone() {
        try {
            Angajat clone = (Angajat) super.clone();
            clone.setId(id);
            clone.setId_utilizator(id_utilizator);
            clone.setId_policlinica(id_policlinica);
            clone.setNume(nume);
            clone.setPrenume(prenume);
            clone.setFunctie(functie);
            clone.setSalariu_negociat(salariu_negociat);
            clone.setNumar_ore(numar_ore);
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
