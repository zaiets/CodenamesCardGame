package service;


import utils.WordContainer;
import utils.WordsReader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WordGenerator {
    private final static int MAX_COUNT = 25;
    private static List<String> vocabulary;

    public void setVocabularyFromInnerData(WordContainer container) {
        vocabulary = WordsReader.read(container);
    }

    public boolean setVocabularyFromFile(File file) {
        List<String> vocabulary = WordsReader.read(file);
        if (vocabulary != null) {
            WordGenerator.vocabulary = vocabulary;
            return true;
        } else {
            return false;
        }
    }

    public List<String> giveMeWordsToPlay() {
        if (vocabulary == null) return null;
        List<String> gameWords = new ArrayList<>();
        Random randIndex = new Random();
        List<Integer> alreadyInsertedIndexes = new ArrayList<>();
        int i = 0;
        while (i < MAX_COUNT) {
            Integer index;
            do {
                index = Math.abs(randIndex.nextInt(vocabulary.size()));
            } while (alreadyInsertedIndexes.contains(index));
            gameWords.add(vocabulary.get(index));
            alreadyInsertedIndexes.add(index);
            i++;
        }
        return gameWords;
    }
}
