package ee.kristokukk.tahetuvastus.controller;


import ee.kristokukk.tahetuvastus.entity.ACountHistory;
import ee.kristokukk.tahetuvastus.entity.Word;
import ee.kristokukk.tahetuvastus.repository.ACountHistoryRepository;
import ee.kristokukk.tahetuvastus.repository.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/words")
public class WordController {

    @Autowired
    private WordRepository wordRepository;

    @Autowired
    private ACountHistoryRepository aCountHistoryRepository;

    @PostMapping
    public Word addWord(@RequestBody Word word) {
        if (word.getWord() == null || word.getWord().trim().isEmpty()) {
            throw new RuntimeException("Sõna ei tohi olla tühi.");
        }

        if (word.getWord().length() < 2) {
            throw new RuntimeException("Sõna peab olema vähemalt 2 tähemärki pikk.");
        }

        return wordRepository.save(word);
    }

    @GetMapping
    public List<Word> getAllWords() {
        return wordRepository.findAll();
    }

    @GetMapping("/a-count")
    public int getTotalACount() {
        int total = wordRepository.findAll().stream()
                .map(Word::getWord)
                .mapToInt(this::countAs)
                .sum();

        ACountHistory history = new ACountHistory();
        history.setCount(total);
        aCountHistoryRepository.save(history);

        return total;
    }

    @GetMapping("/a-count-history")
    public List<ACountHistory> getACountHistory() {
        return aCountHistoryRepository.findAll();
    }

    @GetMapping("/words-with-a")
    public int getWordsWithA() {
        return (int) wordRepository.findAll().stream()
                .map(Word::getWord)
                .filter(this::containsA)
                .count();
    }

    @GetMapping("/a-average")
    public double getAAverage() {
        int totalAs = wordRepository.findAll().stream()
                .map(Word::getWord)
                .mapToInt(this::countAs)
                .sum();

        int totalLetters = wordRepository.findAll().stream()
                .map(Word::getWord)
                .mapToInt(String::length)
                .sum();

        if (totalLetters == 0) {
            return 0.0;
        }

        return (double) totalAs / totalLetters;
    }

    @PutMapping("/replace-letter/{index}")
    public List<Word> replaceLetterWithA(@PathVariable int index) {
        if (index < 1) {
            throw new RuntimeException("Positsioon peab olema vähemalt 1.");
        }

        List<Word> words = wordRepository.findAll();

        for (Word word : words) {
            String text = word.getWord();

            if (text.length() >= index) {
                char[] chars = text.toCharArray();
                chars[index - 1] = 'a';
                word.setWord(new String(chars));
                wordRepository.save(word);
            }
        }

        return wordRepository.findAll();
    }

    private int countAs(String text) {
        int count = 0;
        for (char c : text.toCharArray()) {
            if (c == 'a' || c == 'A') {
                count++;
            }
        }
        return count;
    }

    private boolean containsA(String text) {
        for (char c : text.toCharArray()) {
            if (c == 'a' || c == 'A') {
                return true;
            }
        }
        return false;
    }
}