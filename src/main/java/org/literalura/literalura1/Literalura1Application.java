package org.literalura.literalura1;

import org.literalura.literalura1.controllers.Controlador;
import org.literalura.literalura1.repositories.AutorRepository;
import org.literalura.literalura1.repositories.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Literalura1Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Literalura1Application.class, args);
    }

    @Autowired
    LibroRepository libroRepository;
    @Autowired
    AutorRepository autorRepository;
    @Override
    public void run(String... args) throws Exception {
        var principal= new Controlador(libroRepository,autorRepository);
        principal.mostrarMenu();
    }
}
