package com.example.demo.Kapcsolat;

import com.example.demo.Tantargy.Tantargy;
import jakarta.persistence.*;

@Entity
@Table(name = "kapcsolat")
public class Kapcsolat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "targykod1", referencedColumnName = "kod")
    private Tantargy targy1;

    @ManyToOne
    @JoinColumn(name = "targykod2", referencedColumnName = "kod")
    private Tantargy targy2;

    public Kapcsolat() {}

    public Kapcsolat(Tantargy targy1, Tantargy targy2) {
        this.targy1 = targy1;
        this.targy2 = targy2;
    }

    public Long getId() {
        return id;
    }

    public Tantargy getTargy1() {
        return targy1;
    }

    public void setTargy1(Tantargy targy1) {
        this.targy1 = targy1;
    }

    public Tantargy getTargy2() {
        return targy2;
    }

    public void setTargy2(Tantargy targy2) {
        this.targy2 = targy2;
    }
}
