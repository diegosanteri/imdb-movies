package com.imdb.repository;

import com.imdb.domain.MovieDomain;
import com.imdb.textsearch.InvertedIndex;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class MovieRepository {

    private Map<String, MovieDomain> movies = new HashMap<>();
    private final InvertedIndex invertedIndex = new InvertedIndex();

    public void saveAll(List<MovieDomain> movies) {
        this.movies = movies.stream()
                .map(movie -> {
                    invertedIndex.indexString(movie.getId(), movie.getName());
                    invertedIndex.indexString(movie.getId(), movie.getDirector());
                    movie.getStars().forEach(star ->  invertedIndex.indexString(movie.getId(), star));
                    return movie;
                })
                .collect(Collectors.toMap(t->t.getId(), t -> t));
    }

    public List<MovieDomain> search(String search) {

       return invertedIndex.search(Arrays.asList(search.split("\\W+").clone()))
                .stream()
                .map(movies::get)
                .collect(Collectors.toList());
    }
}
