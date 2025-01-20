package org.literalura.literalura1.dtos;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;

public record LibroDto(@JsonAlias("id") Integer id,
                       @JsonAlias("title")          String titulo,
                       @JsonAlias("subjects") List<String> tematica,
                       @JsonAlias("authors")        List<AutorDto> autores,
                       @JsonAlias("bookshelves")    List<String> categorias,
                       @JsonAlias("languages")      List<String> idiomas,
                       @JsonAlias("media_type")     String formato,
                       @JsonAlias("download_count")  Integer numerosDescaargas

) {
}