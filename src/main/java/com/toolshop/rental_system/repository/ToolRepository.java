package com.toolshop.rental_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.toolshop.rental_system.model.Tool;

@Repository
public interface ToolRepository extends JpaRepository<Tool, Long> {
    Tool findOneByToolCode(String code);
}
