package com.example.demo.Tantargy;

import org.apache.commons.text.similarity.CosineSimilarity;
import org.apache.commons.text.similarity.JaccardSimilarity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TantargySimilarityService {

    private final TantargyRepository repository;
    private final CosineSimilarity cosine = new CosineSimilarity();

    public TantargySimilarityService(TantargyRepository repository) {
        this.repository = repository;
    }

    public List<Map<String, Object>> findSimilar(String kod, double threshold) {
        Optional<Tantargy> targetOpt = repository.findById(kod);
        if (targetOpt.isEmpty()) return List.of();

        Tantargy target = targetOpt.get();
        String desc1 = Optional.ofNullable(target.getDescription()).orElse("").toLowerCase();

        return repository.findAll().stream()
                .filter(t -> !t.getKod().equals(kod))
                .map(t -> {
                    String desc2 = Optional.ofNullable(t.getDescription()).orElse("").toLowerCase();
                    double score = new JaccardSimilarity().apply(desc1, desc2);
                    Map<String, Object> result = new HashMap<>();
                    result.put("targetKod", t.getKod());
                    result.put("targetNev", t.getNev());
                    result.put("similarity", score * 100); // store as Double
                    return result;
                })
                .filter(m -> (Double) m.get("similarity") >= threshold)
                .sorted((a, b) -> Double.compare(
                        (Double) b.get("similarity"),
                        (Double) a.get("similarity")
                ))
                .collect(Collectors.toList());
    }
}
