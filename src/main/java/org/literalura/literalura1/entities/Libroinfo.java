package org.literalura.literalura1.entities;

import com.fasterxml.jackson.annotation.JsonAlias;
import org.literalura.literalura.dtos.LibroDto;

import java.util.List;

public record Libroinfo(
        @JsonAlias("results") List<LibroDto> results
) {
}