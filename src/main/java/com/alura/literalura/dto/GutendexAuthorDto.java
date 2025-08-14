package com.alura.literalura.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class GutendexAuthorDto {
    @JsonProperty("name")
    private String name;

    @JsonProperty("birth_year")
    private Integer birthYear;

    @JsonProperty("death_year")
    private Integer deathYear;
}