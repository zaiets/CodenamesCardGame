package service;


import utils.WordContainer;
import utils.WordsReader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WordGenerator {
    private final static int MAX_COUNT = 25;

    public List<String> listOfWords(WordContainer container) {
        List<String> words = WordsReader.read(container);
        return listOfWords(words);
    }

    public List<String> listOfWords(File file) {
        List<String> words = WordsReader.read(file);
        if (words != null) {
            return listOfWords(words);
        } else {
            return null;
        }
    }

    private List<String> listOfWords(List<String> words) {
        List<String> gameWords = new ArrayList<>();
        Random randIndex = new Random();
        List<Integer> alreadyInsertedIndexes = new ArrayList<>();
        int i = 0;
        while (i < MAX_COUNT) {
            Integer index;
            do {
                index = Math.abs(randIndex.nextInt(words.size()));
            } while (alreadyInsertedIndexes.contains(index));
            gameWords.add(words.get(index));
            alreadyInsertedIndexes.add(index);
            i++;
        }
        return gameWords;
    }
}
