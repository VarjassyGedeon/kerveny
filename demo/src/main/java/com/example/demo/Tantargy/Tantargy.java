package com.example.demo.Tantargy;

import com.example.demo.Szak.Szak;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tantargyak")
public class Tantargy {

    @Id
    private String kod;
    private String nev;
    private int kredit;

    @Column(columnDefinition = "TEXT") // allows long descriptions
    private String description;

    public Tantargy(){
    }

    public Tantargy(String kod, String nev, int kredit, String description) {
        this.kod = kod;
        this.nev = nev;
        this.kredit = kredit;
        this.description = description;
    }

    @ManyToMany
    @JoinTable(
            name = "tantargy_szak",
            joinColumns = @JoinColumn(name = "tantargy_kod"),
            inverseJoinColumns = @JoinColumn(name = "szak_id")
    )
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Set<Szak> szak = new HashSet<>();

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

    public Set<Szak> getSzak() { return szak; }
    public void setSzak(Set<Szak> szakok) { this.szak = szakok; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    @Override
    public String toString() {
        return "Tantargy{" +
                "kod='" + kod + '\'' +
                ", nev='" + nev + '\'' +
                ", kredit=" + kredit +
                '}';
    }
}
