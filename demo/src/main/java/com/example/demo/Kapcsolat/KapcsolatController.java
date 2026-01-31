package com.example.demo.Kapcsolat;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.example.demo.Kapcsolat.KapcsolatDTO;

import java.util.List;

@RestController
@RequestMapping("/api/v1/kapcsolat")
public class KapcsolatController {

    private final KapcsolatService kapcsolatService;

    public KapcsolatController(KapcsolatService kapcsolatService) {
        this.kapcsolatService = kapcsolatService;
    }

    @GetMapping("/check")
    public boolean checkKapcsolat(@RequestParam String kod1, @RequestParam String kod2) {
        return kapcsolatService.kapcsolatLetezik(kod1, kod2);
    }

    @PostMapping("/checkSelected")
    public int checkSelectedPairs(@RequestBody List<TantargyPair> pairs) {
        return kapcsolatService.countCompatiblePairs(pairs);
    }

    @GetMapping
    public List<KapcsolatDTO> getAllKapcsolatok() {
        return kapcsolatService.getAllKapcsolatok();
    }


    @PostMapping
    public Kapcsolat addKapcsolat(@RequestBody KapcsolatDTO dto) {
        return kapcsolatService.addKapcsolat(dto.getTargy1Id(), dto.getTargy2Id());
    }

}
