package com.example.source.claseTabele;

import java.sql.Time;

public class Serviciu {
    private int id;
    private String nume_serviciu;
    private Double pret;
    private Time durata;
    private String investigatii;

    public Serviciu(int id, String nume_serviciu, Double pret, Time durata) {
        this.id = id;
        this.nume_serviciu = nume_serviciu;
        this.pret = pret;
        this.durata = durata;
    }

    public Serviciu(int id, String nume_serviciu, Double pret, Time durata, String investigatii) {
        this.id = id;
        this.nume_serviciu = nume_serviciu;
        this.pret = pret;
        this.durata = durata;
        this.investigatii = investigatii;
    }

    public int getId() {
        return id;
    }

    public String getNume_serviciu() {
        return nume_serviciu;
    }

    public Double getPret() {
        return pret;
    }

    public Time getDurata() {
        return durata;
    }

    public String getInvestigatii() {
        return investigatii;
    }

    public void setInvestigatii(String investigatii) {
        this.investigatii = investigatii;
    }

    public void setNume_serviciu(String nume_serviciu) {
        this.nume_serviciu = nume_serviciu;
    }

    public void setPret(Double pret) {
        this.pret = pret;
    }

    public void setDurata(Time durata) {
        this.durata = durata;
    }

    @Override
    public String toString() {
        return "Serviciu{" +
                "id=" + id +
                ", nume_serviciu='" + nume_serviciu + '\'' +
                ", pret=" + pret +
                ", durata=" + durata +
                ", investigatii='" + investigatii + '\'' +
                '}';
    }
}
