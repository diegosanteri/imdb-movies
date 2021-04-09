package com.imdb.bootstrap;

import com.imdb.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DataInitializer {

    @Autowired
    private MovieService movieService;

    @PostConstruct
    public void setup () {
        movieService.importMovies();
    }
}
