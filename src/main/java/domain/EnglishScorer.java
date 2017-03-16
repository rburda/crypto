package domain;

import java.util.HashMap;
import java.util.Map;

public class EnglishScorer {

	private static final Map<Character, Integer> CHARACTER_SCORE;

	static {
		CHARACTER_SCORE = new HashMap<>();
		CHARACTER_SCORE.put('e', 52);
		CHARACTER_SCORE.put('t', 50);
		CHARACTER_SCORE.put('a', 48);
		CHARACTER_SCORE.put('i', 46);
		CHARACTER_SCORE.put('o', 44);
		CHARACTER_SCORE.put('n', 42);
		CHARACTER_SCORE.put('s', 40);
		CHARACTER_SCORE.put('h', 38);
		CHARACTER_SCORE.put('r', 36);
		CHARACTER_SCORE.put('d', 34);
		CHARACTER_SCORE.put('l', 32);
		CHARACTER_SCORE.put('c', 30);
		CHARACTER_SCORE.put('u', 28);
		CHARACTER_SCORE.put('m', 26);
		CHARACTER_SCORE.put('w', 24);
		CHARACTER_SCORE.put('f', 22);
		CHARACTER_SCORE.put('g', 20);
		CHARACTER_SCORE.put('y', 18);
		CHARACTER_SCORE.put('p', 16);
		CHARACTER_SCORE.put('b', 14);
		CHARACTER_SCORE.put('v', 12);
		CHARACTER_SCORE.put('k', 10);
		CHARACTER_SCORE.put('j', 8);
		CHARACTER_SCORE.put('x', 6);
		CHARACTER_SCORE.put('q', 4);
		CHARACTER_SCORE.put('z', 2);
		CHARACTER_SCORE.put(' ', 5);
		CHARACTER_SCORE.put('\'', 3);
		CHARACTER_SCORE.put('\n', 5);
		CHARACTER_SCORE.put(',', 2);
		CHARACTER_SCORE.put('.', 2);
		CHARACTER_SCORE.put('!', 2);
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
				if (c < 32 || c >= 126) {
					s = -10000; //non-printable char
				} else if (c >= 33 && c <= 64) {
					s = 1; //
				} else {
					s = -1;
				}
			}
			score+=s;
		}
		return score;
	}
}