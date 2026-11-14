package com.krakedev.taller_jwt.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.krakedev.taller_jwt.entities.Libro;
import com.krakedev.taller_jwt.repository.LibroRepository;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    public List<Libro> listarLibros() {
        return libroRepository.findAll();
    }

    public Optional<Libro> obtenerPorId(Integer id) {
        return libroRepository.findById(id);
    }

    public Libro guardarLibro(String titulo, String autor, MultipartFile archivo) throws IOException {
        Libro libro = new Libro();
        libro.setTitulo(titulo);
        libro.setAutor(autor);
        
        if (archivo != null && !archivo.isEmpty()) {
            libro.setMimeType(archivo.getContentType());
            libro.setFoto(archivo.getBytes());
        }
        
        return libroRepository.save(libro);
    }

    public Libro actualizarLibro(Integer id, String titulo, String autor, MultipartFile archivo) throws IOException {
        Optional<Libro> libroOpt = libroRepository.findById(id);
        if (libroOpt.isPresent()) {
            Libro libroExistente = libroOpt.get();
            libroExistente.setTitulo(titulo);
            libroExistente.setAutor(autor);

            if (archivo != null && !archivo.isEmpty()) {
                libroExistente.setMimeType(archivo.getContentType());
                libroExistente.setFoto(archivo.getBytes());
            }

            return libroRepository.save(libroExistente);
        }
        throw new RuntimeException("Libro no encontrado con ID: " + id);
    }

    public boolean eliminarLibro(Integer id) {
        if (libroRepository.existsById(id)) {
            libroRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
