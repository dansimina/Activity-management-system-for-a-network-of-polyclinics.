package com.example.source.claseTabele;

public class Pacient {
    int id;
    String nume;
    String prenume;

    String dataProgramare;
    String oraProgramare;

    public Pacient(int id, String nume, String prenume) {
        this.id = id;
        this.nume = nume;
        this.prenume = prenume;
    }

    public Pacient(int id, String nume, String prenume, String dataProgramare, String oraProgramare) {
        this.id = id;
        this.nume = nume;
        this.prenume = prenume;
        this.dataProgramare = dataProgramare;
        this.oraProgramare = oraProgramare;
    }

    public int getId() {
        return id;
    }

    public String getNume() {
        return nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public String getDataProgramare() {
        return dataProgramare;
    }

    public String getOraProgramare() {
        return oraProgramare;
    }

    @Override
    public String toString() {
        return "Pacient{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", prenume='" + prenume + '\'' +
                '}';
    }
}
