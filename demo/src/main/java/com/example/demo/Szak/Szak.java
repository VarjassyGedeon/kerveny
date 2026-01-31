package com.example.demo.Szak;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import com.example.demo.Tantargy.Tantargy;

@Entity
@Table(name = "szak")
public class Szak {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nev;

    @ManyToMany(mappedBy = "szak")
    @JsonManagedReference("tantargy-szak")
    private Set<Tantargy> tantargyak = new HashSet<>();

    public Szak() {}

    public Szak(String nev) {
        this.nev = nev;
    }

    public Long getId() {
        return id;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public Set<Tantargy> getTantargyak() {
        return tantargyak;
    }

    public void setTantargyak(Set<Tantargy> tantargyak) {
        this.tantargyak = tantargyak;
    }
}
