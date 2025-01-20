package org.literalura.literalura1.dtos;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AutorDto(

    @JsonAlias("birth_year") Integer anioNacimiento,
    @JsonAlias("death_year") Integer fechaMuerte,
    @JsonAlias("name") String nombre){
}

