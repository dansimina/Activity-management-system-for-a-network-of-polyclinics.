package com.example.source.claseTabele;

import java.sql.Time;

public class IntervalOrar {
    private Time ora_inceput;
    private Time ora_sfarsit;

    private long diferenta;

    public IntervalOrar() {
    }

    public IntervalOrar(Time ora_inceput, Time ora_sfarsit) {
        this.ora_inceput = ora_inceput;
        this.ora_sfarsit = ora_sfarsit;
        diferenta = ora_sfarsit.getTime() - ora_inceput.getTime();
    }

    public Time getOra_inceput() {
        return ora_inceput;
    }

    public Time getOra_sfarsit() {
        return ora_sfarsit;
    }
    public long getDiferenta(){
        return diferenta;
    }
    public void setOra_inceput(Time ora_inceput) {
        this.ora_inceput = ora_inceput;
    }

    public void setOra_sfarsit(Time ora_sfarsit) {
        this.ora_sfarsit = ora_sfarsit;
    }

    @Override
    public String toString() {
        return "IntervalOrar{" +
                "ora_inceput=" + ora_inceput +
                ", ora_sfarsit=" + ora_sfarsit +
                '}';
    }
}
