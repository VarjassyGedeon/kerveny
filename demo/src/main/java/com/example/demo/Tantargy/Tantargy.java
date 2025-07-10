package com.example.demo.Tantargy;

import jakarta.persistence.*;

@Entity
@Table(name = "tantargyak")
public class Tantargy {

    @Id
    private String kod;
    private String nev;
    private int kredit;

    public Tantargy(){
    }

    public Tantargy(String kod, String nev, int kredit) {
        this.kod = kod;
        this.nev = nev;
        this.kredit = kredit;
    }

    public String getKod() {
        return kod;
    }

    public void setKod(String kod) {
        this.kod = kod;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public int getKredit() {
        return kredit;
    }

    public void setKredit(int kredit) {
        this.kredit = kredit;
    }

    @Override
    public String toString() {
        return "Tantargy{" +
                "kod='" + kod + '\'' +
                ", nev='" + nev + '\'' +
                ", kredit=" + kredit +
                '}';
    }
}
