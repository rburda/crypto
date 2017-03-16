import domain.Base64String;
import domain.BinaryString;
import domain.EnglishScorer;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BreakRepeatingKeyXOR {

	public static void main (String args[]) throws Exception {
		Scanner s = new Scanner(new File((args.length > 0 ? args[0] : "breakRepeatingKeyXOR.txt")));
		BinaryString encrypted = new Base64String(concatLines(s)).toBinary();

		BreakRepeatingKeyXOR b = new BreakRepeatingKeyXOR(encrypted);
		Result bestResult = b.decrypt();
		System.out.println("chosen key: (size " + bestResult.key.length() + ") = " + bestResult.key + " \n" + bestResult.decryptedText);
	}

	private final BinaryString encrypted;

	private BreakRepeatingKeyXOR(BinaryString encrypted) throws Exception {
		this.encrypted = encrypted;
	}

	private Result decrypt() {
		List<Integer> probableKeySizes = determineProbableKeySizes(encrypted);

		Result bestResult = null;
		for (Integer keySize: probableKeySizes) {
			String key = decodeKey(keySize, encrypted);
			Result tempResult = decryptAndScore(encrypted, key);
			if (bestResult == null || tempResult.score > bestResult.score) {
				bestResult = tempResult;
			}
		}
		System.out.println("Most probable key sizes: " + probableKeySizes);
		return bestResult;
	}

	private Result decryptAndScore(BinaryString encrypted, String key) {
		String decryptionCandidate = encrypted.repeatingKeyXor(new BinaryString(key.getBytes())).toAscii();
		int score = new EnglishScorer(decryptionCandidate).calculateScore();
		return new Result(score, decryptionCandidate, key);
	}

	private String decodeKey(int keySize, BinaryString encrypted) {
		List<BinaryString> slices = encrypted.repeatedSlice(keySize);
		List<BinaryString> transposed = BinaryString.transposeByIndex(slices);

		char[] repeatingKey = new char[transposed.size()];
		for (int i=0; i < transposed.size(); i++) {
			repeatingKey[i] = new SingleByteXORDecoder(transposed.get(i)).decode().getXorChar();
		}
		return new String(repeatingKey);
	}

	private List<Integer> determineProbableKeySizes(BinaryString encrypted) {
		final int NUM_KEY_CANDIDATES = 8;
		List<Integer> mostLikelyKeySizes = new ArrayList<>(4);
		Map<Integer, Double> keySizeToDistance = new HashMap<>();

		for (int i=2; i <= 40; i++) {
			double normalized1 = normalizedHammingDistance(encrypted, i, 0);
			double normalized2 = normalizedHammingDistance(encrypted, i, i * 2);
			double averaged = ((normalized1 + normalized2) / 2);

			keySizeToDistance.put(i, averaged);
			int cnt=0;
			boolean inserted = false;
			while (cnt<NUM_KEY_CANDIDATES && !inserted) {
				if (mostLikelyKeySizes.size() <= cnt) {
					mostLikelyKeySizes.add(i);
					inserted = true;
				} else if (keySizeToDistance.get(mostLikelyKeySizes.get(cnt)) > averaged) {
					mostLikelyKeySizes.add(cnt, i);
					inserted = true;
				}
				cnt++;
			}
		}
		return mostLikelyKeySizes.subList(0,NUM_KEY_CANDIDATES);
	}

	private double normalizedHammingDistance(BinaryString encrypted, int keysize, int startingIdx) {

		BinaryString x = encrypted.slice(startingIdx, startingIdx+keysize);
		BinaryString y = encrypted.slice(startingIdx+keysize, startingIdx+(keysize*2));
		int hammingDistance = x.hammingDistance(y);
		return hammingDistance/(double)keysize;
	}

	private static String concatLines(Scanner file) {
		StringBuilder builder = new StringBuilder();
		file.forEachRemaining(builder::append);
		return builder.toString();
	}

	private static class Result {
		int score;
		String decryptedText;
		String key;

		private Result(int score, String dT, String k) {
			this.score = score;
			this.decryptedText = dT;
			this.key = k;
		}
	}
}