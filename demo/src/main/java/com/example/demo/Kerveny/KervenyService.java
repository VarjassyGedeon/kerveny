package com.example.demo.Kerveny;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KervenyService {

    private final KervenyRepository repository;

    public KervenyService(KervenyRepository repository) {
        this.repository = repository;
    }

    public List<Kerveny> getAllKerveny() {
        return repository.findAll();
    }

    public Kerveny findMostMatchingKerveny(List<KervenyPar> inputPairs) {
        List<Kerveny> allKervenyes = repository.findAll();

        Kerveny bestMatch = null;
        int maxMatches = 0;
        int minSizeDifference = Integer.MAX_VALUE;

        for (Kerveny kerveny : allKervenyes) {
            List<KervenyPar> kervenyPairs = kerveny.getPairs();
            int matchCount = 0;

            for (KervenyPar inputPair : inputPairs) {
                for (KervenyPar dbPair : kervenyPairs) {
                    boolean matches =
                            (inputPair.getKod1().getKod().equals(dbPair.getKod1().getKod()) &&
                                    inputPair.getKod2().getKod().equals(dbPair.getKod2().getKod())) ||

                                    (inputPair.getKod1().getKod().equals(dbPair.getKod2().getKod()) &&
                                            inputPair.getKod2().getKod().equals(dbPair.getKod1().getKod()));

                    if (matches) {
                        matchCount++;
                        break;
                    }
                }
            }

            int sizeDifference = Math.abs(kervenyPairs.size() - inputPairs.size());

            if (matchCount > maxMatches ||
                    (matchCount == maxMatches && sizeDifference < minSizeDifference)) {
                maxMatches = matchCount;
                minSizeDifference = sizeDifference;
                bestMatch = kerveny;
            }
        }

        return bestMatch;
    }

    public Kerveny findMostMatchingKervenyFromInput(List<KervenyParInputDTO> inputPairs) {
        List<Kerveny> allKerveny = repository.findAll();
        Kerveny bestMatch = null;
        int maxMatches = 0;
        int minPairsSizeForTie = Integer.MAX_VALUE;

        for (Kerveny kerveny : allKerveny) {
            List<KervenyPar> dbPairs = kerveny.getPairs();
            int matchCount = 0;

            for (KervenyParInputDTO input : inputPairs) {
                String inputKod1 = input.getKod1();
                String inputKod2 = input.getKod2();

                for (KervenyPar db : dbPairs) {
                    if (db.getKod1() == null || db.getKod2() == null ||
                            db.getKod1().getKod() == null || db.getKod2().getKod() == null) {
                        continue;
                    }

                    String dbKod1 = db.getKod1().getKod();
                    String dbKod2 = db.getKod2().getKod();

                    boolean matches =
                            (inputKod1.equalsIgnoreCase(dbKod1) && inputKod2.equalsIgnoreCase(dbKod2)) ||
                                    (inputKod1.equalsIgnoreCase(dbKod2) && inputKod2.equalsIgnoreCase(dbKod1));

                    if (matches) {
                        matchCount++;
                        break;
                    }
                }
            }

            if (matchCount > maxMatches) {
                maxMatches = matchCount;
                bestMatch = kerveny;
                minPairsSizeForTie = dbPairs.size();
            } else if (matchCount == maxMatches && matchCount > 0) {
                if (dbPairs.size() < minPairsSizeForTie) {
                    bestMatch = kerveny;
                    minPairsSizeForTie = dbPairs.size();
                }
            }
        }

        return bestMatch;
    }


}
