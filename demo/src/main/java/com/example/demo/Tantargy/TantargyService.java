package com.example.demo.Tantargy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TantargyService {
    private final TantargyRepository repository;

    @Autowired
    public TantargyService(TantargyRepository repository) {
        this.repository = repository;
    }

    public List<Tantargy> getTantargy(){
        return repository.findAll();
    }

    public void addTantargy(Tantargy tantargy){
        repository.save(tantargy);
    }

    public Tantargy updateTantargy(String kod, Tantargy updated) {
        Tantargy existing = repository.findById(kod)
                .orElseThrow(() -> new RuntimeException("Tantárgy not found: " + kod));

        existing.setNev(updated.getNev());
        existing.setKredit(updated.getKredit());
        existing.setDescription(updated.getDescription());

        return repository.save(existing);
    }

    public void deleteTantargy(String kod) {
        repository.deleteById(kod);
    }
}