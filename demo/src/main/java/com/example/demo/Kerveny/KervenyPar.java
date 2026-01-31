package com.example.demo.Kerveny;

import com.example.demo.Tantargy.Tantargy;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "kerveny_par")
public class KervenyPar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "kerveny_id")
    @JsonBackReference
    private Kerveny kerveny;

    @ManyToOne
    @JoinColumn(name = "kod1", referencedColumnName = "kod", nullable = false)
    private Tantargy kod1;

    @ManyToOne
    @JoinColumn(name = "kod2", referencedColumnName = "kod", nullable = false)
    private Tantargy kod2;

    public KervenyPar() {
    }

    public KervenyPar(Kerveny kerveny, Tantargy kod1, Tantargy kod2) {
        this.kerveny = kerveny;
        this.kod1 = kod1;
        this.kod2 = kod2;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Kerveny getKerveny() {
        return kerveny;
    }

    public void setKerveny(Kerveny kerveny) {
        this.kerveny = kerveny;
    }

    public Tantargy getKod1() {
        return kod1;
    }

    public void setKod1(Tantargy kod1) {
        this.kod1 = kod1;
    }

    public Tantargy getKod2() {
        return kod2;
    }

    public void setKod2(Tantargy kod2) {
        this.kod2 = kod2;
    }

    @Override
    public String toString() {
        return "KervenyPar{" +
                "id=" + id +
                ", kerveny=" + kerveny.getId() +
                ", kod1=" + kod1.getKod() +
                ", kod2=" + kod2.getKod() +
                '}';
    }
}
