package com.example.source.claseTabele;

public class Utilizator implements Cloneable {
    private Integer id;
    private Integer id_cont;
    private String departament;
    private String adresa;
    private String cnp;
    private String nume;
    private String prenume;
    private String telefon;
    private String email;
    private String iban;
    private String data_angajarii;
    private String rol;

    private Medic medic;
    private AsistentMedical asistentMedical;

    public Utilizator(Integer id, String departament, String adresa, String cnp, String nume, String prenume, String telefon, String email, String iban, String data_angajarii, String rol) {
        this.id = id;
        this.departament = departament;
        this.adresa = adresa;
        this.cnp = cnp;
        this.nume = nume;
        this.prenume = prenume;
        this.telefon = telefon;
        this.email = email;
        this.iban = iban;
        this.data_angajarii = data_angajarii;
        this.rol = rol;
        this.medic = null;
        this.asistentMedical = null;
    }


    public Utilizator(int id, int id_cont, String departament, String adresa, String cnp, String nume, String prenume, String telefon, String email, String iban, String data_angajarii, String rol) {
        this.id = id;
        this.id_cont = id_cont;
        this.departament = departament;
        this.adresa = adresa;
        this.cnp = cnp;
        this.nume = nume;
        this.prenume = prenume;
        this.telefon = telefon;
        this.email = email;
        this.iban = iban;
        this.data_angajarii = data_angajarii;
        this.rol = rol;
    }

    public void setMedic(Medic medic) {
        this.medic = medic;
    }

    public void setAsistentMedical(AsistentMedical asistentMedical) {
        this.asistentMedical = asistentMedical;
    }

    public Integer getId() {
        return id;
    }

    public String getDepartament() {
        return departament;
    }

    public String getAdresa() {
        return adresa;
    }

    public String getCnp() {
        return cnp;
    }

    public String getNume() {
        return nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public String getTelefon() {
        return telefon;
    }

    public String getEmail() {
        return email;
    }

    public String getIban() {
        return iban;
    }

    public String getData_angajarii() {
        return data_angajarii;
    }

    public String getRol() {
        return rol;
    }

    public Integer getId_cont() {
        return id_cont;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setId_cont(Integer id_cont) {
        this.id_cont = id_cont;
    }

    public void setDepartament(String departament) {
        this.departament = departament;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public void setData_angajarii(String data_angajarii) {
        this.data_angajarii = data_angajarii;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "Utilizator{" +
                "id=" + id +
                ", departament='" + departament + '\'' +
                ", adresa='" + adresa + '\'' +
                ", cnp='" + cnp + '\'' +
                ", nume='" + nume + '\'' +
                ", prenume='" + prenume + '\'' +
                ", telefon='" + telefon + '\'' +
                ", email='" + email + '\'' +
                ", iban='" + iban + '\'' +
                ", data_angajarii='" + data_angajarii + '\'' +
                ", rol='" + rol + '\'' +
                '}';
    }

    @Override
    public Utilizator clone() {
        try {
            Utilizator clone = (Utilizator) super.clone();
            clone.setId(id);
            clone.setAdresa(adresa);
            clone.setDepartament(departament);
            clone.setCnp(cnp);
            clone.setIban(iban);
            clone.setData_angajarii(data_angajarii);
            clone.setNume(nume);
            clone.setPrenume(prenume);
            clone.setRol(rol);
            clone.setTelefon(telefon);
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
