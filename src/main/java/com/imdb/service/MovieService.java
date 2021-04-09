package com.imdb.service;

import com.imdb.crawler.MovieCrawler;
import com.imdb.domain.MovieDomain;
import com.imdb.repository.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MovieService {

    private final MovieCrawler movieCrawler;
    private final MovieRepository movieRepository;

    public void importMovies() {
        var movies = movieCrawler.crawler();
        movieRepository.saveAll(movies);
    }

    public List<MovieDomain> search(String search) {
        return movieRepository.search(search);
    }
}
