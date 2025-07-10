package com.example.demo.Kapcsolat;

import java.util.stream.Collectors;
import com.example.demo.Tantargy.TantargyRepository;
import com.example.demo.Tantargy.Tantargy;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class KapcsolatService {

    private final TantargyRepository tantargyRepository;

    private final KapcsolatRepository kapcsolatRepository;

    public KapcsolatService(KapcsolatRepository kapcsolatRepository, TantargyRepository tantargyRepository) {
        this.kapcsolatRepository = kapcsolatRepository;
        this.tantargyRepository = tantargyRepository;
    }

    public List<KapcsolatDTO> getAllKapcsolatok() {
        List<Kapcsolat> kapcsolatok = kapcsolatRepository.findAll();
        return kapcsolatok.stream()
                .map(k -> new KapcsolatDTO(k.getTargy1().getKod(), k.getTargy2().getKod()))
                .collect(Collectors.toList());
    }

    public Kapcsolat addKapcsolat(String targy1Id, String targy2Id) {
        Tantargy t1 = tantargyRepository.findById(targy1Id).orElseThrow();
        Tantargy t2 = tantargyRepository.findById(targy2Id).orElseThrow();
        return kapcsolatRepository.save(new Kapcsolat(t1, t2));
    }

    public boolean kapcsolatLetezik(String kod1, String kod2) {
        return kapcsolatRepository.existsByTantargyPair(kod1, kod2);
    }

    public double compatibility(List<String> tantargyList1, List<String> tantargyList2){
        int count = tantargyList1.size();
        int compatible = 0;

        for(int i=0; i<tantargyList1.size(); i++){
            if (kapcsolatLetezik(tantargyList1.get(i),tantargyList2.get(i))){
                compatible++;
            }
        }

        return compatible/count*100;
    }

    public void compareToPrevious(List<String> tantargyList1, List<String> tantargyList2){
        int count = tantargyList1.size();

        for(int i=0; i<tantargyList1.size(); i++){

        }

    }

    public int countCompatiblePairs(List<TantargyPair> pairs) {
        int count = 0;
        for (TantargyPair pair : pairs) {
            if (kapcsolatRepository.existsByTantargyPair(pair.getKod1(), pair.getKod2())) {
                count++;
            }
        }
        return count;
    }
}