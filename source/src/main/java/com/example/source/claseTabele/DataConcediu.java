package com.example.source.claseTabele;

import java.sql.Date;

public class DataConcediu {
    private Date data_inceput;
    private Date data_sfarsit;

    public DataConcediu(Date data_inceput, Date data_sfarsit) {
        this.data_inceput = data_inceput;
        this.data_sfarsit = data_sfarsit;
    }

    public Date getData_inceput() {
        return data_inceput;
    }

    public Date getData_sfarsit() {
        return data_sfarsit;
    }

    @Override
    public String toString() {
        return "DataConcediu{" +
                "data_inceput=" + data_inceput +
                ", data_sfarsit=" + data_sfarsit +
                '}';
    }
}
