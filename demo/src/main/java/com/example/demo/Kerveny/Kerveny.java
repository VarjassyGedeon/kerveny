package com.example.demo.Kerveny;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "kerveny")
public class Kerveny {

    @Id
    @Column(length = 10)
    private String id;

    @Column(length = 6, nullable = false)
    private String neptunkod;

    private Boolean elfogadva;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dontesIdopont;

    @OneToMany(mappedBy = "kerveny", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<KervenyPar> pairs;

    public Kerveny() {}

    public Kerveny(String id, String neptunkod, Boolean elfogadva, LocalDateTime dontesIdopont) {
        this.id = id;
        this.neptunkod = neptunkod;
        this.elfogadva = elfogadva;
        this.dontesIdopont = dontesIdopont;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNeptunkod() {
        return neptunkod;
    }

    public void setNeptunkod(String neptunkod) {
        this.neptunkod = neptunkod;
    }

    public Boolean getElfogadva() {
        return elfogadva;
    }

    public void setElfogadva(Boolean elfogadva) {
        this.elfogadva = elfogadva;
    }

    public LocalDateTime getDontesIdopont() {
        return dontesIdopont;
    }

    public void setDontesIdopont(LocalDateTime dontesIdopont) {
        this.dontesIdopont = dontesIdopont;
    }

    public List<KervenyPar> getPairs() {
        return pairs;
    }

    public void setPairs(List<KervenyPar> pairs) {
        this.pairs = pairs;
    }

    @Override
    public String toString() {
        return "Kerveny{" +
                "id='" + id + '\'' +
                ", neptunkod='" + neptunkod + '\'' +
                ", elfogadva=" + elfogadva +
                ", dontesIdopont=" + dontesIdopont +
                '}';
    }
}
