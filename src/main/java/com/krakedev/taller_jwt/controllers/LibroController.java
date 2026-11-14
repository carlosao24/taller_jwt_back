package com.krakedev.taller_jwt.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.krakedev.taller_jwt.entities.Libro;
import com.krakedev.taller_jwt.services.LibroService;

@RestController
@RequestMapping("/auth/items")
@CrossOrigin(origins = "*")
public class LibroController {

    @Autowired
    private LibroService libroService;

    @GetMapping
    public ResponseEntity<List<Libro>> listar() {
        List<Libro> libros = libroService.listarLibros();
        return ResponseEntity.ok(libros);
    }

    @PostMapping
    public ResponseEntity<?> crear(
            @RequestParam("titulo") String titulo,
            @RequestParam("autor") String autor,
            @RequestParam(value = "foto", required = false) MultipartFile foto) {
        try {
            Libro nuevoLibro = libroService.guardarLibro(titulo, autor, foto);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoLibro);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al guardar el libro: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(
            @PathVariable Integer id,
            @RequestParam("titulo") String titulo,
            @RequestParam("autor") String autor,
            @RequestParam(value = "foto", required = false) MultipartFile foto) {
        try {
            Libro libroActualizado = libroService.actualizarLibro(id, titulo, autor, foto);
            return ResponseEntity.ok(libroActualizado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar el libro: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        boolean eliminado = libroService.eliminarLibro(id);
        if (eliminado) {
            return ResponseEntity.ok().body("Libro eliminado exitosamente con ID: " + id);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Libro no encontrado");
        }
    }
}