package com.challenge.literalura.application;

import com.challenge.literalura.persistence.entity.Autor;
import com.challenge.literalura.persistence.entity.Libro;
import com.challenge.literalura.service.CatalogoService;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {

  private final Scanner consola = new Scanner(System.in);
  private final CatalogoService catalogoService;

  public Principal(CatalogoService catalogoService) {
    this.catalogoService = catalogoService;
  }

  public void execMenu() {
    System.out.println("Menu de opciones");
    boolean salir = false;
    while (!salir) {
      try {
        int opcion = showMenu(consola);
        salir = execOptions(opcion);
      } catch (Exception e) {
        System.out.println("Ocurrio un error: " + e.getMessage());
      }
    }
  }

  private int showMenu(Scanner consola) {
    System.out.print("""
        *** Catalogo de libros - LiterAlura ***
        1. Buscar Libro por Titulo.
        2. Listar Libros registrados.
        3. Listar Autores Registrados.
        4. Listar Autores vivos en un determinado año.
        5. Listar Libros por idioma.
        6. Generando Estadisticas
        7. Top 10 libros más Descargados
        8. Buscar Autor por Nombre
        9. Buscar Autor fallecido por determinado año
        0. Salir
        Elige una opción:\s""");
    return Integer.parseInt(consola.nextLine());
  }

  private boolean execOptions(int opcion) {
    switch (opcion) {
      case 1 -> buscarLibroPorTitulo();
      case 2 -> listarLibrosRegistrados();
      case 3 -> listarAutoresRegistrados();
      case 4 -> listarAutoresVivosPorAnio();
      case 5 -> listarLibrosPorIdioma();
      case 6 -> generarEstadisticas();
      case 7 -> top10LibrosMasDescargados();
      case 8 -> buscarAutorPorNombre();
      case 9 -> buscarAutorFallecidoPorAnio();
      case 0 -> {
        System.out.println("Hasta Pronto");
        return true;
      }
      default -> System.out.println("Opción invalida, Elija otra opción: " + opcion);
    }
    return false;
  }

  private void mostrarResultados(String mensaje, List<?> lista) {
    System.out.println(mensaje);
    if (!lista.isEmpty()) {
      lista.forEach(System.out::println);
    } else {
      System.out.println("No hay resultados para esta búsqueda.");
    }
  }

  private void buscarLibroPorTitulo() {
    System.out.println("--Buscador de Libros por Titulo--");
    System.out.print("Introduzca el titulo a Buscar: ");
    String titulo = consola.nextLine();
    Optional<Libro> libro = catalogoService.getLibroByTitle(titulo);
    System.out.println("Titulo: " + titulo);
    libro.ifPresentOrElse(
            System.out::println,
            () -> System.out.println("No se encontró el libro con ese título.")
    );
  }

  private void listarLibrosRegistrados() {
    System.out.println("--Lista de Libros Registrados--");
    List<Libro> libros = catalogoService.getAllBooksRegistered();
    mostrarResultados("Libros registrados:", libros);
  }

  private void listarAutoresRegistrados() {
    System.out.println("--Lista de Autores Registrados--");
    List<Autor> autores = catalogoService.getAllAuthorRegistered();
    mostrarResultados("Autores registrados:", autores);
  }

  private void listarAutoresVivosPorAnio() {
    System.out.println("--Listar Autores Vivos por Año--");
    System.out.print("Ingrese el año: ");
    Integer anio = Integer.parseInt(consola.nextLine());
    List<Autor> autores = catalogoService.getAllAuthorLivesByYear(anio);
    mostrarResultados("Autores vivos en el año " + anio + ":", autores);
  }

  private void listarLibrosPorIdioma() {
    System.out.println("--Listar Libros por Idioma--");
    System.out.print("""
        Formatos Seleccionados:
        * en - Inglés
        * es - Español
        """);
    System.out.print("Ingrese el idioma a buscar: ");
    String idioma = consola.nextLine();
    List<Libro> libros = catalogoService.getAllBooksByLanguage(idioma);
    mostrarResultados("Libros en el idioma " + idioma + ":", libros);
  }

  private void generarEstadisticas() {
    System.out.println("--Generando Estadísticas--");
    List<Libro> libros = catalogoService.getAllBooksRegistered();
    IntSummaryStatistics estadisticas = libros.stream()
            .filter(l -> l.getDownloads() > 0)
            .collect(Collectors.summarizingInt(Libro::getDownloads));
    System.out.println("Promedio de descargas: " + estadisticas.getAverage());
    System.out.println("Mayor número de descargas: " + estadisticas.getMax());
    System.out.println("Menor número de descargas: " + estadisticas.getMin());
    System.out.println("Cantidad de libros registrados: " + estadisticas.getCount());
  }

  private void top10LibrosMasDescargados() {
    System.out.println("--Top 10 Libros Más Descargados--");
    List<Libro> libros = catalogoService.get10BooksByDownloads();
    mostrarResultados("Top 10 libros más descargados:", libros);
  }

  private void buscarAutorPorNombre() {
    System.out.println("--Buscar Autor por Nombre--");
    System.out.print("Ingrese el nombre a buscar: ");
    String nombre = consola.nextLine();
    Autor autor = catalogoService.getAutorByName(nombre);
    if (autor != null) {
      System.out.println(autor);
    } else {
      System.out.println("El autor no está registrado en la base de datos.");
    }
  }

  private void buscarAutorFallecidoPorAnio() {
    System.out.println("--Buscar Autor Fallecido por Año--");
    System.out.print("Ingrese el año: ");
    Integer anio = Integer.parseInt(consola.nextLine());
    List<Autor> autores = catalogoService.getAllAuthorDeadsByYear(anio);
    mostrarResultados("Autores fallecidos en el año " + anio + ":", autores);
  }
}
