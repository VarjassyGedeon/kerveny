package com.example.demo.Kerveny;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/kervenypar")
@CrossOrigin(origins = "*")
public class KervenyParController {

    private final KervenyParService service;

    public KervenyParController(KervenyParService service) {
        this.service = service;
    }


    @GetMapping
    public List<Kerveny> getAllKerveny() {
        return service.getAllKerveny();
    }
}
