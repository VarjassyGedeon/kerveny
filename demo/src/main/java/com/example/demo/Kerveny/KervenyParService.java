package com.example.demo.Kerveny;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KervenyParService {

    private final KervenyParRepository repository;

    public KervenyParService(KervenyParRepository repository) {
        this.repository = repository;
    }

    public List<Kerveny> getAllKerveny() {
        return repository.findAll();
    }
}
