package com.imdb.textsearch;

import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.stream.Collectors;

public class InvertedIndex {

    private static final List<String> stopwords = Arrays.asList("a", "able", "about",
            "across", "after", "all", "almost", "also", "am", "among", "an",
            "and", "any", "are", "as", "at", "be", "because", "been", "but",
            "by", "can", "cannot", "could", "dear", "did", "do", "does",
            "either", "else", "ever", "every", "for", "from", "get", "got",
            "had", "has", "have", "he", "her", "hers", "him", "his", "how",
            "however", "i", "if", "in", "into", "is", "it", "its", "just",
            "least", "let", "like", "likely", "may", "me", "might", "most",
            "must", "my", "neither", "no", "nor", "not", "of", "off", "often",
            "on", "only", "or", "other", "our", "own", "rather", "said", "say",
            "says", "she", "should", "since", "so", "some", "than", "that",
            "the", "their", "them", "then", "there", "these", "they", "this",
            "tis", "to", "too", "twas", "us", "wants", "was", "we", "were",
            "what", "when", "where", "which", "while", "who", "whom", "why",
            "will", "with", "would", "yet", "you", "your");

    Map<String, List<Tuple>> index = new HashMap<>();

    public void indexString(String id, String text) {

        for (String word : text.split("\\W+")) {
            var subWords = new SimpleWordSteemer(word.toLowerCase()).parse();

            for(var subWord : subWords) {
                if (stopwords.contains(subWord)) {
                    continue;
                }

                List<Tuple> idx = index.get(subWord);
                if (idx == null) {
                    idx = new LinkedList<>();
                    index.put(subWord, idx);
                }
                idx.add(new Tuple(subWord, id));
            }
        }
    }

    public Set<String> search(List<String> words) {

        var found = new ArrayList<List<String>>();
        for (String word : words) {
            String normalizedWord = word.toLowerCase();
            List<Tuple> idx = index.get(normalizedWord);
            if(idx != null) {
                found.add(idx.stream()
                        .map(id -> id.getId())
                        .collect(Collectors.toList()));
            }
        }

        var response = new ArrayList<String>();

        if(!found.isEmpty()) {
            response.addAll(found.remove(0));
            for(var item : found) {
                response.retainAll(item);
            }
        }

        return response.stream().collect(Collectors.toSet());
    }

    @Getter
    @Setter
    public static class Tuple {
        private String id;
        private String word;

        public Tuple(String word, String id) {
            this.id = id;
            this.word = word;
        }
    }
}
