package com.example.demo.Kerveny;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/kerveny")
@CrossOrigin(origins = "*")
public class KervenyController {

    private final KervenyService service;

    public KervenyController(KervenyService service) {
        this.service = service;
    }

    @GetMapping
    public List<Kerveny> getAllKerveny() {
        return service.getAllKerveny();
    }

    @PostMapping("/matcha")
    public Kerveny getMostMatching(@RequestBody List<KervenyPar> inputPairs) {
        return service.findMostMatchingKerveny(inputPairs);
    }

    @PostMapping("/match")
    public ResponseEntity<?> findBestMatch(@RequestBody List<KervenyParInputDTO> pairs) {
        try {
            System.out.println("Bejövő párok száma: " + pairs.size());
            for (KervenyParInputDTO p : pairs) {
                System.out.println("Pár: " + p.getKod1() + " - " + p.getKod2());
            }

            Kerveny match = service.findMostMatchingKervenyFromInput(pairs);

            if (match == null) {
                return ResponseEntity.status(404).body("Nincs egyező kérvény");
            }

            return ResponseEntity.ok(match);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Szerver hiba" + e.getMessage());
        }
    }

}
