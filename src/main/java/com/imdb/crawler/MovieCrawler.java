package com.imdb.crawler;

import com.imdb.domain.MovieDomain;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Component
public class MovieCrawler {

    private static final String MOVIE_DIV = ".mode-advanced";
    private static final String URL = "https://www.imdb.com/search/title/?groups=top_1000&sort=user_rating,desc&count=100&start=%d&ref_=adv_nxt";

    private final MovieParser movieParser;

    public List<MovieDomain> crawler() {

        int offset = 1;
        Elements elements;
        List<MovieDomain> response = new ArrayList<>();

        do {
            log.info("Starting fetch movie IMDB offset " +  offset);
            elements = getElementsFromImdb(offset);
            response.addAll(extractMoviesFromPageElements(elements));
            offset += 100;
        } while(elements.size() != 0);

        log.info(response.size() + " were imported");
        return response;
    }

    private Elements getElementsFromImdb(int offset) {
        try {
            Document document = Jsoup.connect(String.format(URL, offset)).get();
            Elements movieDivs = document.select(MOVIE_DIV);
            return movieDivs;
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    private List<MovieDomain> extractMoviesFromPageElements(Elements elements) {
        return elements.stream()
                .map(movieParser::parse)
                .collect(Collectors.toList());
    }
}
