package com.example.demo.Kapcsolat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface KapcsolatRepository extends JpaRepository<Kapcsolat, Long> {

    @Query("SELECT COUNT(k) > 0 FROM Kapcsolat k " +
            "WHERE (k.targy1.kod = :kod1 AND k.targy2.kod = :kod2) " +
            "   OR (k.targy1.kod = :kod2 AND k.targy2.kod = :kod1)")
    boolean existsByTantargyPair(@Param("kod1") String kod1, @Param("kod2") String kod2);
}