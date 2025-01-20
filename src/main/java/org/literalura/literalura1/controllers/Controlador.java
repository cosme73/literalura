package org.literalura.literalura1.controllers;

import org.literalura.literalura.entities.Autor;
import org.literalura.literalura.entities.Libro;
import org.literalura.literalura.services.IAutorService;
import org.literalura.literalura.services.iLibroService;
import org.springframework.stereotype.Component;

import java.util.IntSummaryStatistics;
import java.util.List;

@Component
public class Controlador {
    private final iLibroService libroService;
    private final IAutorService autorService;
    private final Vista view;
    private final Inputs inputHandler;

    public Controlador(iLibroService libroService,
                       IAutorService autorService,
                       Vista view,
                       Inputs inputHandler) {
        this.libroService = libroService;
        this.autorService = autorService;
        this.view = view;
        this.inputHandler = inputHandler;
    }

    public void iniciar() {
        int opcion;
        do {
            view.mostrarLogo();
            view.mostrarMenu();
            opcion = inputHandler.leerOpcion();

            switch (opcion) {
                case 1 -> buscarLibroPorTitulo();
                case 2 -> listarLibrosRegistrados();
                case 3 -> listarAutoresRegistrados();
                case 4 -> listarAutoresVivosPorAnio();
                case 5 -> listarLibrosPorIdioma();
                case 6 -> buscarAutorPorNombre();
                case 7 -> listarAutoresPorRangoAnioNacimiento();
                case 8 -> mostrarTop10Libros();
                case 9 -> mostrarEstadisticas();
                case 0 -> System.out.println("Saliendo de la aplicación");
                default -> System.out.println("Opción no válida. Intente nuevamente.");
            }

            if (opcion != 0) {
                System.out.println("\nPresione ENTER para continuar...");
                inputHandler.leerTexto();
            }
        } while (opcion != 0);
    }

    private void buscarLibroPorTitulo() {
        view.mostrarTitulo("BUSCAR LIBRO POR TÍTULO");
        System.out.println("Ingrese el título del libro:");
        String titulo = inputHandler.leerTexto();

        Libro libro = libroService.buscarPorTitulo(titulo);
        if (libro != null) {
            view.mostrarLibro(libro);
        } else {
            System.out.println("Libro no encontrado.");
        }
    }

    private void listarLibrosRegistrados() {
        view.mostrarTitulo("LIBROS REGISTRADOS");
        List<Libro> libros = libroService.listarTodos();

        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
        } else {
            libros.forEach(view::mostrarLibro);
        }
    }

    private void listarAutoresRegistrados() {
        view.mostrarTitulo("AUTORES REGISTRADOS");
        List<Autor> autores = autorService.listarTodos();

        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados.");
        } else {
            autores.forEach(view::mostrarAutor);
        }
    }

    private void listarAutoresVivosPorAnio() {
        view.mostrarTitulo("AUTORES VIVOS EN UN AÑO DETERMINADO");
        System.out.println("Ingrese el año:");
        int anio = inputHandler.leerNumero();

        if (anio < 0) {
            System.out.println("Año inválido.");
            return;
        }

        List<Autor> autoresVivos = autorService.buscarVivosEnAnio(anio);
        if (autoresVivos.isEmpty()) {
            System.out.printf("No se encontraron autores vivos en el año %d.%n", anio);
        } else {
            System.out.printf("Autores que vivieron en %d:%n", anio);
            autoresVivos.forEach(view::mostrarAutor);
        }
    }

    private void listarLibrosPorIdioma() {
        view.mostrarTitulo("LIBROS POR IDIOMAS");
        System.out.println("""
                Ingrese las dos primeras iniciales del idioma (ejemplo):
                es [Español]    en [English]
                fr [Francés]    ch [Chino]
                """);

        String idioma = inputHandler.leerTexto().toLowerCase();

        if (idioma.length() != 2) {
            System.out.println("El código de idioma debe tener exactamente 2 letras.");
            return;
        }

        List<Libro> libros = libroService.buscarPorIdioma(idioma);
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados en ese idioma.");
        } else {
            libros.forEach(view::mostrarLibro);
        }
    }

    private void buscarAutorPorNombre() {
        view.mostrarTitulo("BUSCAR AUTOR POR NOMBRE");
        System.out.println("Ingrese el nombre del autor:");
        String nombre = inputHandler.leerTexto();

        List<Autor> autores = autorService.buscarPorNombre(nombre);
        if (autores.isEmpty()) {
            System.out.println("No se encontraron autores con ese nombre.");
        } else {
            autores.forEach(view::mostrarAutor);
        }
    }

    private void listarAutoresPorRangoAnioNacimiento() {
        view.mostrarTitulo("AUTORES POR RANGO DE AÑOS DE NACIMIENTO");
        System.out.println("Ingrese el año de nacimiento inicial:");
        int anioInicial = inputHandler.leerNumero();

        System.out.println("Ingrese el año de nacimiento final:");
        int anioFinal = inputHandler.leerNumero();

        if (anioInicial < 0 || anioFinal < 0 || anioFinal < anioInicial) {
            System.out.println("Rango de años inválido.");
            return;
        }

        List<Autor> autores = autorService.buscarPorRangoNacimiento(anioInicial, anioFinal);
        if (autores.isEmpty()) {
            System.out.printf("No se encontraron autores nacidos entre %d y %d.%n", anioInicial, anioFinal);
        } else {
            autores.forEach(view::mostrarAutor);
        }
    }

    private void mostrarTop10Libros() {
        view.mostrarTitulo("TOP 10 LIBROS MÁS DESCARGADOS");
        List<Libro> topLibros = libroService.obtenerTop10PorDescargas();

        if (topLibros.isEmpty()) {
            System.out.println("No hay libros registrados.");
        } else {
            topLibros.forEach(view::mostrarLibro);
        }
    }

    private void mostrarEstadisticas() {
        view.mostrarTitulo("ESTADÍSTICAS DE LIBROS");
        List<Libro> libros = libroService.listarTodos();

        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados para mostrar estadísticas.");
            return;
        }

        IntSummaryStatistics stats = libros.stream()
                .mapToInt(Libro::getNumerosDescaargas)
                .summaryStatistics();

        view.mostrarEstadisticas(
                stats.getSum(),
                stats.getAverage(),
                stats.getMin(),
                stats.getMax(),
                stats.getCount()
        );
    }
}