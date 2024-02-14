package com.example.source.claseTabele;

public class AsistentMedical implements Cloneable{
    private Integer id;
    private Integer id_angajat;
    private String tip;
    private String grad;

    public AsistentMedical(Integer id, String tip, String grad) {
        this.id = id;
        this.tip = tip;
        this.grad = grad;
    }

    public AsistentMedical(Integer id, Integer id_angajat, String tip, String grad) {
        this.id = id;
        this.id_angajat = id_angajat;
        this.tip = tip;
        this.grad = grad;
    }

    public Integer getId() {
        return id;
    }

    public String getTip() {
        return tip;
    }

    public String getGrad() {
        return grad;
    }

    public Integer getId_angajat() {
        return id_angajat;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public void setGrad(String grad) {
        this.grad = grad;
    }

    public void setId_angajat(Integer id_angajat) {
        this.id_angajat = id_angajat;
    }

    @Override
    public String toString() {
        return "AsistentMedical{" +
                "id=" + id +
                ", tip='" + tip + '\'' +
                ", grad='" + grad + '\'' +
                '}';
    }

    @Override
    public AsistentMedical clone() {
        try {
            AsistentMedical clone = (AsistentMedical) super.clone();
            clone.setId(id);
            clone.setId_angajat(id_angajat);
            clone.setTip(tip);
            clone.setGrad(grad);
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
