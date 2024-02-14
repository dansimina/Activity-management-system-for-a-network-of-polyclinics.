package com.example.source.claseTabele;

public class Specialitati {
    private Integer id;
    private Integer id_medic;
    private String nume_specialitate;
    private String grad;

    public Specialitati(Integer id, String nume_specialitate, String grad) {
        this.id = id;
        this.nume_specialitate = nume_specialitate;
        this.grad = grad;
    }

    public Specialitati(Integer id, Integer id_medic, String nume_specialitate, String grad) {
        this.id = id;
        this.id_medic = id_medic;
        this.nume_specialitate = nume_specialitate;
        this.grad = grad;
    }

    public Integer getId() {
        return id;
    }

    public String getNume_specialitate() {
        return nume_specialitate;
    }

    public String getGrad() {
        return grad;
    }

    public Integer getId_medic() {
        return id_medic;
    }

    @Override
    public String toString() {
        return "Specialitati{" +
                "id=" + id +
                ", id_medic=" + id_medic +
                ", nume_specialitate='" + nume_specialitate + '\'' +
                ", grad='" + grad + '\'' +
                '}';
    }
}
