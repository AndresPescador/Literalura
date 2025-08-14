package com.alura.literalura.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class GutendexBookDto {
    private int id;
    private String title;

    @JsonProperty("authors")
    private List<GutendexAuthorDto> authors;

    @JsonProperty("languages")
    private List<String> languages;

    @JsonProperty("download_count")
    private int downloadCount;
}