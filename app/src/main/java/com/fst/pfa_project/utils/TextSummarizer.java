package com.fst.pfa_project.utils;

import java.util.*;

public class TextSummarizer {

    public static String summarize(String inputText) {
        if (inputText == null || inputText.isEmpty()) return "";

        String[] sentences = inputText.split("(?<=[.!?])\\s+");
        Map<String, Integer> wordFreq = new HashMap<>();

        // Comptage des mots
        for (String sentence : sentences) {
            for (String word : sentence.toLowerCase().replaceAll("[^a-zA-Z ]", "").split("\\s+")) {
                if (word.length() > 2) {
                    Integer count = wordFreq.containsKey(word) ? wordFreq.get(word) : 0;
                    wordFreq.put(word, count + 1);
                }
            }
        }

        // Score des phrases
        Map<String, Integer> sentenceScores = new HashMap<>();
        for (String sentence : sentences) {
            int score = 0;
            for (String word : sentence.toLowerCase().replaceAll("[^a-zA-Z ]", "").split("\\s+")) {
                Integer freq = wordFreq.containsKey(word) ? wordFreq.get(word) : 0;
                score += freq;
            }
            sentenceScores.put(sentence, score);
        }

        // Trier manuellement les phrases par score
        List<Map.Entry<String, Integer>> sorted = new ArrayList<>(sentenceScores.entrySet());
        Collections.sort(sorted, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue() - o1.getValue(); // ordre décroissant
            }
        });

        // Résumé avec les 200 meilleures phrases
        StringBuilder summary = new StringBuilder();
        int count = Math.min(200, sorted.size());
        for (int i = 0; i < count; i++) {
            summary.append(sorted.get(i).getKey()).append(" ");
        }

        return summary.toString().trim();
    }
}
