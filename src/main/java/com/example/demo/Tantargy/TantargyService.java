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
}