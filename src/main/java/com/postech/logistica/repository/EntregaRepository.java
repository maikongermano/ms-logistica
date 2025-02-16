package com.postech.logistica.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.postech.logistica.entity.Entrega;

public interface EntregaRepository extends JpaRepository<Entrega, Long> {
    List<Entrega> findByStatus(String status);
}
