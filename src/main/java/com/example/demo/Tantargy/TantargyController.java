package com.example.demo.Tantargy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/tantargy")
public class TantargyController {
    private final TantargyService tantargyService;

    @Autowired
    public TantargyController(TantargyService tantargyService) {
        this.tantargyService = tantargyService;
    }

    @GetMapping
    public List<Tantargy> getTantargy(){
        return tantargyService.getTantargy();
    }

    @PostMapping
    public void addTantargy(@RequestBody Tantargy tantargy) {
        tantargyService.addTantargy(tantargy);
    }

}
