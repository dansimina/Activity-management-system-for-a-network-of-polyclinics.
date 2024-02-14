package com.example.source.claseTabele;

import javafx.collections.ObservableList;

import java.util.HashSet;

public class Raport {
    static final int MAX_ISTORIC = 5000;
    static final int MAX_SIMPTOME = 1000;
    static final int MAX_DIAGNOSTIC = 200;
    static final int MAX_RECOMANDARI = 5000;
    static final int MAX_INVESTIGATII = 500;
    private int id;
    private int id_programare;
    private int id_medic;
    private Integer id_asistent;
    private String nume_medic_recomandare;
    private String prenume_medic_recomandare;
    private String istoric;
    private String simptome;
    private String diagnostic;
    private String recomandari;
    private boolean parafa;

    private ObservableList<Serviciu> serviciiRaport;

    public Raport(int id, int id_programare, int id_medic, int id_asistent, String nume_medic_recomandare, String prenume_medic_recomandare, String istoric, String simptome, String diagnostic, String recomandari, boolean parafa) {
        this.id = id;
        this.id_programare = id_programare;
        this.id_medic = id_medic;
        this.id_asistent = id_asistent;
        this.nume_medic_recomandare = nume_medic_recomandare;
        this.prenume_medic_recomandare = prenume_medic_recomandare;
        this.istoric = istoric;
        this.simptome = simptome;
        this.diagnostic = diagnostic;
        this.recomandari = recomandari;
        this.parafa = parafa;
    }

    public Raport(int id, int id_programare, int id_medic, String nume_medic_recomandare, String prenume_medic_recomandare, String istoric, String simptome, String diagnostic, String recomandari, boolean parafa) {
        this.id = id;
        this.id_programare = id_programare;
        this.id_medic = id_medic;
        this.nume_medic_recomandare = nume_medic_recomandare;
        this.prenume_medic_recomandare = prenume_medic_recomandare;
        this.istoric = istoric;
        this.simptome = simptome;
        this.diagnostic = diagnostic;
        this.recomandari = recomandari;
        this.parafa = parafa;
    }

    public void addServicii(Serviciu serviciu) {
        serviciiRaport.add(serviciu);
    }

    public int getId() {
        return id;
    }

    public int getId_programare() {
        return id_programare;
    }

    public int getId_medic() {
        return id_medic;
    }

    public int getId_asistent() {
        return id_asistent;
    }

    public String getNume_medic_recomandare() {
        return nume_medic_recomandare;
    }

    public String getPrenume_medic_recomandare() {
        return prenume_medic_recomandare;
    }

    public String getIstoric() {
        return istoric;
    }

    public String getSimptome() {
        return simptome;
    }

    public String getDiagnostic() {
        return diagnostic;
    }

    public String getRecomandari() {
        return recomandari;
    }

    public boolean isParafa() {
        return parafa;
    }

    public ObservableList<Serviciu> getServiciiRaport() {
        return (ObservableList<Serviciu>) serviciiRaport;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setId_programare(int id_programare) {
        this.id_programare = id_programare;
    }

    public void setId_medic(int id_medic) {
        this.id_medic = id_medic;
    }

    public void setId_asistent(int id_asistent) {
        this.id_asistent = id_asistent;
    }

    public void setNume_medic_recomandare(String nume_medic_recomandare) {
        if (nume_medic_recomandare.isEmpty())
            this.nume_medic_recomandare = " ";
        else
            this.nume_medic_recomandare = nume_medic_recomandare;
    }

    public void setPrenume_medic_recomandare(String prenume_medic_recomandare) {
        if (prenume_medic_recomandare.isEmpty())
            this.prenume_medic_recomandare = " ";
        else
            this.prenume_medic_recomandare = prenume_medic_recomandare;
    }

    public void setIstoric(String istoric) {
        if (istoric.isEmpty())
            this.istoric = " ";
        else
            this.istoric = istoric;
    }

    public void setSimptome(String simptome) {
        if (simptome.isEmpty())
            this.simptome = " ";
        else
            this.simptome = simptome;
    }

    public void setDiagnostic(String diagnostic) {
        if (diagnostic.isEmpty())
            this.diagnostic = " ";
        else
            this.diagnostic = diagnostic;
    }

    public void setRecomandari(String recomandari) {
        if (recomandari.isEmpty())
            this.recomandari = " ";
        else
            this.recomandari = recomandari;
    }

    public void setParafa(boolean parafa) {
        this.parafa = parafa;
    }

    public void setServiciiRaport(ObservableList<Serviciu> serviciiRaport) {
        this.serviciiRaport = serviciiRaport;
    }

    @Override
    public String toString() {
        return "Raport{" +
                "id=" + id +
                ", id_programare=" + id_programare +
                ", id_medic=" + id_medic +
                ", id_asistent=" + id_asistent +
                ", nume_medic_recomandare='" + nume_medic_recomandare + '\'' +
                ", prenume_medic_recomandare='" + prenume_medic_recomandare + '\'' +
                ", istoric='" + istoric + '\'' +
                ", simptome='" + simptome + '\'' +
                ", diagnostic='" + diagnostic + '\'' +
                ", recomandari='" + recomandari + '\'' +
                ", parafa=" + parafa +
                '}';
    }
}
