package com.example.source.claseTabele;

public class ProgramFunctionare implements Cloneable {
    private int id;
    private String duminica;
    private String luni;
    private String marti;
    private String miercuri;
    private String joi;
    private String vineri;
    private String sambata;

    public ProgramFunctionare(int id, String duminica, String luni, String marti, String miercuri, String joi, String vineri, String sambata) {
        this.id = id;
        this.duminica = duminica;
        this.luni = luni;
        this.marti = marti;
        this.miercuri = miercuri;
        this.joi = joi;
        this.vineri = vineri;
        this.sambata = sambata;
    }

    public int getId() {
        return id;
    }

    public String getDuminica() {
        return duminica;
    }

    public String getLuni() {
        return luni;
    }

    public String getMarti() {
        return marti;
    }

    public String getMiercuri() {
        return miercuri;
    }

    public String getJoi() {
        return joi;
    }

    public String getVineri() {
        return vineri;
    }

    public String getSambata() {
        return sambata;
    }

    public void setDuminica(String duminica) {
        this.duminica = duminica;
    }

    public void setLuni(String luni) {
        this.luni = luni;
    }

    public void setMarti(String marti) {
        this.marti = marti;
    }

    public void setMiercuri(String miercuri) {
        this.miercuri = miercuri;
    }

    public void setJoi(String joi) {
        this.joi = joi;
    }

    public void setVineri(String vineri) {
        this.vineri = vineri;
    }

    public void setSambata(String sambata) {
        this.sambata = sambata;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ProgramFunctionare{" +
                "id=" + id + ", duminica='" + duminica + '\'' +
                ", luni='" + luni + '\'' + ", marti='" + marti + '\'' +
                ", miercuri='" + miercuri + '\'' + ", joi='" + joi + '\'' +
                ", vineri='" + vineri + '\'' + ", sambata='" + sambata + '\'' +
                '}';
    }

    @Override
    public ProgramFunctionare clone() {
        try {
            ProgramFunctionare clone = (ProgramFunctionare) super.clone();
            clone.setId(id);
            clone.setDuminica(duminica);
            clone.setLuni(luni);
            clone.setMarti(marti);
            clone.setMiercuri(miercuri);
            clone.setJoi(joi);
            clone.setVineri(vineri);
            clone.setSambata(sambata);
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}