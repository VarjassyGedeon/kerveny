package com.example.demo.Szak;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/szak")
public class SzakController {

    private final SzakRepository repository;

    public SzakController(SzakRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Szak> getAll() {
        return repository.findAll();
    }

    @PostMapping
    public Szak addSzak(@RequestBody Szak szak) {
        return repository.save(szak);
    }
}
