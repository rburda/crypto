package domain;

import java.util.HashMap;
import java.util.Map;

public class EnglishScorer {

	private static final Map<Character, Integer> CHARACTER_SCORE;

	static {
		CHARACTER_SCORE = new HashMap<>();
		CHARACTER_SCORE.put('e', 26);
		CHARACTER_SCORE.put('t', 25);
		CHARACTER_SCORE.put('a', 24);
		CHARACTER_SCORE.put('i', 23);
		CHARACTER_SCORE.put('o', 22);
		CHARACTER_SCORE.put('n', 21);
		CHARACTER_SCORE.put('s', 20);
		CHARACTER_SCORE.put('h', 19);
		CHARACTER_SCORE.put('r', 18);
		CHARACTER_SCORE.put('d', 17);
		CHARACTER_SCORE.put('l', 16);
		CHARACTER_SCORE.put('c', 15);
		CHARACTER_SCORE.put('u', 14);
		CHARACTER_SCORE.put('m', 13);
		CHARACTER_SCORE.put('w', 12);
		CHARACTER_SCORE.put('f', 11);
		CHARACTER_SCORE.put('g', 10);
		CHARACTER_SCORE.put('y', 9);
		CHARACTER_SCORE.put('p', 8);
		CHARACTER_SCORE.put('b', 7);
		CHARACTER_SCORE.put('v', 6);
		CHARACTER_SCORE.put('k', 5);
		CHARACTER_SCORE.put('j', 4);
		CHARACTER_SCORE.put('x', 3);
		CHARACTER_SCORE.put('q', 2);
		CHARACTER_SCORE.put('z', 1);
	}

	private final String text;

	public EnglishScorer(String t) {
		this.text = t;
	}

	public int calculateScore() {
		int score = 0;
		for (char c: text.toCharArray()) {
			Integer s = CHARACTER_SCORE.get(Character.toLowerCase(c));
			if (s == null) {
				if (Character.isDigit(c)) {
					s = 0;
				} else if (Character.isSpaceChar(c)) {
					s = 1;
				} else {
					s = -1;
				}
			}
			score+=s;
		}
		return score;
	}
}
