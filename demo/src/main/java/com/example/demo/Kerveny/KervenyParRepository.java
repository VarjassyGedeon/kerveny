package com.example.demo.Kerveny;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KervenyParRepository extends JpaRepository<Kerveny, String> {
}
