package com.challenge.literalura.persistence.entity;

import com.challenge.literalura.persistence.dto.BookData;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Entity
@Getter
@AllArgsConstructor  // Constructor con parámetros
@NoArgsConstructor  // Constructor vacío generado por Lombok
@ToString(exclude = "autor")  // Evita la recursión infinita con `autor` en el `toString`
public class Libro {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;
  private String language;
  private Integer downloads;

  @ManyToOne
  @JoinColumn(name = "autor_id", nullable = false)
  private Autor autor;

  // Constructor con BookData y Autor, validación del idioma y de descargas
  public Libro(BookData bookData, Autor autor) {
    this.title = bookData.title();
    this.language = validateLanguage(bookData.languages());
    this.downloads = validateDownloads(bookData.downloads());
    this.autor = autor;
  }

  // Método privado para validar idioma
  private String validateLanguage(List<String> languages) {
    return (languages != null && !languages.isEmpty()) ? languages.get(0) : "Desconocido";
  }

  // Método privado para validar descargas
  private Integer validateDownloads(Integer downloads) {
    return (downloads != null && downloads >= 0) ? downloads : 0;
  }

  // Implementación manual de equals()
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Libro libro = (Libro) o;
    return Objects.equals(title, libro.title);  // Comparar solo por título
  }

  // Implementación manual de hashCode()
  @Override
  public int hashCode() {
    return Objects.hash(title);  // Generar hashCode solo por título
  }
}
