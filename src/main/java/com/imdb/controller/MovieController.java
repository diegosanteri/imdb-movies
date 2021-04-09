package com.imdb.controller;

import com.imdb.crawler.MovieCrawler;
import com.imdb.domain.MovieDomain;
import com.imdb.service.MovieService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
@AllArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @PostMapping
    public ResponseEntity importMovies() {
        movieService.importMovies();
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<MovieDomain>> search(@RequestParam("search")String search) {
        return ResponseEntity.ok(movieService.search(search));
    }
}
