package com.example.source.claseTabele;

public class Cont implements Cloneable {
    private int id;
    private String nume_utilizator;
    private String parola;

    public Cont(int id, String nume_utilizator, String parola) {
        this.id = id;
        this.nume_utilizator = nume_utilizator;
        this.parola = parola;
    }

    public int getId() {
        return id;
    }

    public String getNume_utilizator() {
        return nume_utilizator;
    }

    public String getParola() {
        return parola;
    }

    public void setNume_utilizator(String nume_utilizator) {
        this.nume_utilizator = nume_utilizator;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    @Override
    public Cont clone() {
        try {
            Cont clone = (Cont) super.clone();
            clone.setParola(parola);
            clone.setNume_utilizator(nume_utilizator);
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
