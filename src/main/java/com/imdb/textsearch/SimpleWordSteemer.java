package com.imdb.textsearch;

import java.util.ArrayList;
import java.util.List;

public class SimpleWordSteemer {

    private String word;
    public SimpleWordSteemer(String word) {
        this.word = word;
    }

    public List<String>parse() {
        List<String> words = new ArrayList<>();

        for(int i=0; i < word.length(); i++) {

            words.add(word.substring(0, i + 1));
        }

        return words;
    }
}
