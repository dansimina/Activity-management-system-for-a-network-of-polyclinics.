package com.example.source.claseTabele;

import java.sql.Date;
import java.time.LocalDate;

public class Bon {
    private int id;
    private int id_raport;
    private int id_angajat;
    private double total;
    private LocalDate data_emitere;

    public Bon(int id, int id_raport, int id_angajat, double total, LocalDate data_emitere) {
        this.id = id;
        this.id_raport = id_raport;
        this.id_angajat = id_angajat;
        this.total = total;
        this.data_emitere = data_emitere;
    }

    public Bon(int id_raport, int id_angajat, double total, LocalDate data_emitere) {
        this.id_raport = id_raport;
        this.id_angajat = id_angajat;
        this.total = total;
        this.data_emitere = data_emitere;
    }

    public int getId() {
        return id;
    }

    public int getId_raport() {
        return id_raport;
    }

    public int getId_angajat() {
        return id_angajat;
    }

    public double getTotal() {
        return total;
    }

    public LocalDate getData_emitere() {
        return data_emitere;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setId_raport(int id_raport) {
        this.id_raport = id_raport;
    }

    public void setId_angajat(int id_angajat) {
        this.id_angajat = id_angajat;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setData_emitere(LocalDate data_emitere) {
        this.data_emitere = data_emitere;
    }

    @Override
    public String toString() {
        return "Bon{" +
                "id=" + id +
                ", id_raport=" + id_raport +
                ", id_angajat=" + id_angajat +
                ", total=" + total +
                ", data=" + data_emitere +
                '}';
    }
}
