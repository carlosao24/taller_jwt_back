package com.krakedev.taller_jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krakedev.taller_jwt.entities.Libro;

public interface LibroRepository extends JpaRepository<Libro, Integer>{

}
