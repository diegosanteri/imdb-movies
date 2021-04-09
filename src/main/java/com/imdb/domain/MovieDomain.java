package com.imdb.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MovieDomain {

    private String id;
    private String name;
    private Integer rank;
    private String rating;
    private Double metScore;
    private String description;
    private String certificate;
    private Integer year;
    private String runtime;
    private String genre;
    private String director;
    private List<String> stars;
    private String votes;
    private String gross;
}
