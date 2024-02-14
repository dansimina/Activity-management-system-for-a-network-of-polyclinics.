package com.example.source.claseTabele;

import java.sql.Date;
import java.sql.Time;

public class Programare {
    private int id;
    private int id_policlinica;
    private int id_angajat;
    private int id_pacient;
    private int id_medic;
    private Date _data;
    private Time ora_inceput;
    private Time ora_sfarsit;
    private boolean inregistrat;
    private String nume;
    private String prenume;

    public Programare(int id, int id_policlinica, int id_angajat, int id_pacient, int id_medic, Date _data, Time ora_inceput, Time ora_sfarsit, boolean inregistrat) {
        this.id = id;
        this.id_policlinica = id_policlinica;
        this.id_angajat = id_angajat;
        this.id_pacient = id_pacient;
        this.id_medic = id_medic;
        this._data = _data;
        this.ora_inceput = ora_inceput;
        this.ora_sfarsit = ora_sfarsit;
        this.inregistrat = inregistrat;
    }

    public Programare(int id, int id_policlinica, int id_angajat, int id_pacient, int id_medic, Date _data, Time ora_inceput, Time ora_sfarsit, boolean inregistrat, String nume, String prenume) {
        this.id = id;
        this.id_policlinica = id_policlinica;
        this.id_angajat = id_angajat;
        this.id_pacient = id_pacient;
        this.id_medic = id_medic;
        this._data = _data;
        this.ora_inceput = ora_inceput;
        this.ora_sfarsit = ora_sfarsit;
        this.inregistrat = inregistrat;
        this.nume = nume;
        this.prenume = prenume;
    }

    public int getId() {
        return id;
    }

    public int getId_policlinica() {
        return id_policlinica;
    }

    public int getId_angajat() {
        return id_angajat;
    }

    public int getId_pacient() {
        return id_pacient;
    }

    public int getId_medic() {
        return id_medic;
    }

    public Date get_data() {
        return _data;
    }

    public Time getOra_inceput() {
        return ora_inceput;
    }

    public Time getOra_sfarsit() {
        return ora_sfarsit;
    }

    public boolean isInregistrat() {
        return inregistrat;
    }

    public String getNume() {
        return nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setInregistrat(boolean inregistrat) {
        this.inregistrat = inregistrat;
    }

    @Override
    public String toString() {
        return "Programare{" +
                "id=" + id +
                ", id_policlinica=" + id_policlinica +
                ", id_angajat=" + id_angajat +
                ", id_pacient=" + id_pacient +
                ", id_medic=" + id_medic +
                ", _data=" + _data +
                ", ora_inceput=" + ora_inceput +
                ", ora_sfarsit=" + ora_sfarsit +
                ", inregistrat=" + inregistrat +
                ", nume='" + nume + '\'' +
                ", prenume='" + prenume + '\'' +
                '}';
    }
}
