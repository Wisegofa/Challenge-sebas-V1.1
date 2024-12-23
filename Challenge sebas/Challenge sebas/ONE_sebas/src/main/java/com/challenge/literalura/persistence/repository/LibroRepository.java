package com.challenge.literalura.persistence.repository;

import com.challenge.literalura.persistence.entity.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {

  // Devuelve un Optional<Libro> para manejar la ausencia del libro
  Optional<Libro> findByTitleIgnoreCase(String title);

  // Devuelve un boolean primitivo para saber si el libro existe por título
  boolean existsByTitleIgnoreCase(String title);

  // Método para verificar si un libro con un título específico y autor ya existe
  Optional<Libro> findByTitleIgnoreCaseAndAutorId(String title, Long autorId);

  // Otros métodos según tus necesidades
  List<Libro> findLibrosByLanguage(String language);
}
