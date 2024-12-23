package com.challenge.literalura.persistence.entity;

import com.challenge.literalura.persistence.dto.AutorData;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "libros")  // Generar toString automáticamente, excluyendo la relación circular
public class Autor {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private Integer birthDate;

  private Integer deathDate;

  @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)  // Usar LAZY para cargar los libros solo cuando sea necesario
  private List<Libro> libros;

  // Constructor a partir de AutorData
  public Autor(AutorData autorData) {
    this.name = autorData.name();
    this.birthDate = autorData.birthYear();
    this.deathDate = autorData.deathYear();
  }

  // Constructor con todos los parámetros para crear objetos Autor de manera más directa
  public Autor(String name, Integer birthDate, Integer deathDate, List<Libro> libros) {
    this.name = name;
    this.birthDate = birthDate;
    this.deathDate = deathDate;
    this.libros = libros;
  }
}

