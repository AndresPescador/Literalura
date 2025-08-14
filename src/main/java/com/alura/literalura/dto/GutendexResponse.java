package com.alura.literalura.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class GutendexResponse {
    private int count;
    private String next;
    private String previous;
    private List<GutendexBookDto> results;
}