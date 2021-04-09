package com.imdb.crawler;

import com.imdb.domain.MovieDomain;
import org.apache.logging.log4j.util.Strings;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class MovieParser {

    private static final String NAME_CSS = ".lister-item-header > a";
    private static final String RANK_CSS = ".lister-item-index";
    private static final String YEAR_CSS = ".lister-item-year";
    private static final String CERTIFICATE_CSS = ".certificate";
    private static final String GENRE_CSS = ".genre";
    private static final String RATING_CSS = ".ratings-imdb-rating > strong";
    private static final String META_SCORE_CSS = ".metascore";
    private static final String DESCRIPTION_CSS = ".text-muted";
    private static final String RUNTIME_CSS = ".runtime";
    private static final String IMAGE_CSS = ".loadlate";
    private static final String VOTES_SECTION_CSS = ".sort-num_votes-visible";
    private static final String PARAGRAPH_HTML = "p";
    private static final String A_HTML = "a";
    private static final String SPAN_HTML = "span";
    private static final String SRC_ATTRIBUTE = "";
    private static final String DOT_REGEX = "\\.";
    private static final String LEFT_BRACKET = "(";
    private static final String RIGHT_BRACKET = ")";

    public MovieDomain parse(Element element) {

        MovieDomain movieDomain = new MovieDomain();

        String name = extractName(element);
        String director = extractDirector(element);
        movieDomain.setId(UUID.nameUUIDFromBytes((name + director).getBytes()).toString());
        movieDomain.setName(name);
        movieDomain.setRank(extractRank(element));
        movieDomain.setYear(extractYear(element));
        movieDomain.setCertificate(extractCertificate(element));
        movieDomain.setGenre(extractGenre(element));
        movieDomain.setRating(extractRating(element));
        movieDomain.setMetScore(extractMetaScore(element));
        movieDomain.setDescription(extractDescription(element));
        movieDomain.setDirector(director);
        movieDomain.setStars(extractStars(element));
        movieDomain.setRuntime(extractRuntime(element));
        movieDomain.setGross(extractGross(element));
        movieDomain.setVotes(extractVotes(element));

        return movieDomain;
    }

    private String extractName(Element element) {
        var elementHtml = element.select(NAME_CSS);
        final String text = elementHtml.text();
        return text;
    }

    private Integer extractRank(Element element) {
        var elementHtml = element.select(RANK_CSS);
        final String text = elementHtml.text().replaceFirst(DOT_REGEX, Strings.EMPTY);
        return Double.valueOf(text.replaceAll(",", ".")).intValue();
    }

    private Integer extractYear(Element element) {
        var elementHtml = element.select(YEAR_CSS);
        final String text = elementHtml.text().replace(LEFT_BRACKET, Strings.EMPTY).replace(RIGHT_BRACKET, Strings.EMPTY);
        return Integer.valueOf(text.replaceAll("\\D+",""));
    }

    private String extractCertificate(Element element) {
        var elementHtml = element.select(CERTIFICATE_CSS);
        final String text = elementHtml.text();
        return text;
    }

    private String extractGenre(Element element) {
        var elementHtml = element.select(GENRE_CSS);
        final String text = elementHtml.text();
        return text;
    }

    private String extractRating(Element element) {
        var elementHtml = element.select(RATING_CSS);
        final String text = elementHtml.text();
        return text;
    }

    private Double extractMetaScore(Element element) {
        var elementHtml = element.select(META_SCORE_CSS);
        final String text = elementHtml.text();
        return Double.parseDouble(text.isBlank() ? "0" : text);
    }

    private String extractDescription(Element element) {
        var elementHtml = element.select(DESCRIPTION_CSS);
        final String text = elementHtml.get(2).text();
        return text;
    }

    private String extractDirector(Element element) {
        var elementHtml = element.select(PARAGRAPH_HTML);
        final String text = elementHtml.get(2).select(A_HTML).get(0).text();
        return text;
    }

    private List<String> extractStars(Element element) {
        var elementHtml = element.select(PARAGRAPH_HTML);
        return elementHtml.get(2).select(A_HTML)
                .stream()
                .skip(1)
                .map(el -> el.text())
                .collect(Collectors.toList());
    }

    private String extractRuntime(Element element) {
        var elementHtml = element.select(RUNTIME_CSS);
        final String text = elementHtml.text();
        return text;
    }


    private String extractGross(Element element) {

        if(element.select(VOTES_SECTION_CSS).select(SPAN_HTML).size() >= 4) {
            var elementHtml = element.select(VOTES_SECTION_CSS).select(SPAN_HTML).get(4);
            final String text = elementHtml.text();
            return text;
        }
        return "";
    }

    private String extractVotes(Element element) {
        var elementHtml = element.select(VOTES_SECTION_CSS).select(SPAN_HTML).get(1);
        final String text = elementHtml.text();
        return text;
    }

    private String extractImage(Element element) {
        return element.select(IMAGE_CSS).attr(SRC_ATTRIBUTE);
    }
}
