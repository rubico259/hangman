package hangman;

import java.util.ArrayList;
import java.util.List;

public class HangmanServer {

    private String wordToGuess = "review";
    private StringBuilder word;

    public HangmanServer() {
        this.word = new StringBuilder(wordToGuess.replaceAll("[a-z]", "_"));
    }

    public Response start() {
        return new Response(word.toString(), false);
    }

    public Response guess(String letter) {
        if (wordToGuess.contains(letter)) {

            return new Response(generateWord(letter), true);
        } else {
            return new Response(word.toString(), false);
        }
    }

    private String generateWord(String letter) {
        List<Integer> list = new ArrayList<>();
        if (!word.toString().contains(letter)) {
            int index = wordToGuess.indexOf(letter);
            while (index >= 0) {
                list.add(index);
                index = wordToGuess.indexOf(letter, index + 1);
            }
            for (Integer i : list) {
                word.setCharAt(i, letter.charAt(0));
            }
        }
        return word.toString();
    }
}
