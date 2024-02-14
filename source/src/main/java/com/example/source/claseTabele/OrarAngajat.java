package com.example.source.claseTabele;

import java.sql.Time;

public class OrarAngajat {
    private Integer zi;
    private Time start;
    private Time end;
    private String interval;

    public OrarAngajat(Integer zi, String interval) {
        this.zi = zi;
        this.interval = interval;
        String[] t = interval.split("-");
        start = Time.valueOf(t[0]);
        end = Time.valueOf(t[1]);
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public Integer getZi() {
        return zi;
    }

    public long getDiferenta() {
        long ora = (Time.valueOf("01:00:00").getTime() - Time.valueOf("00:00:00").getTime());
        return (end.getTime() - start.getTime()) / ora;
    }

    public String getInterval() {
        return interval;
    }

    @Override
    public String toString() {
        return "OrarAngajat{" +
                "zi=" + zi +
                ", start=" + start +
                ", end=" + end +
                ", interval='" + interval + '\'' +
                '}';
    }
}
