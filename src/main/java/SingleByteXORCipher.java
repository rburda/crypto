import domain.BinaryString;
import domain.EnglishScorer;
import domain.HexString;

import java.util.HashMap;

public class SingleByteXORCipher {

	public static void main (String args[]) {
		String input = "1b37373331363f78151b7f2b783431333d78397828372d363c78373e783a393b3736";

		BinaryString binaryInput = new HexString(input).toBinary();

		String bestGuess = null;
		Character xorChar = null;
		int score = Integer.MIN_VALUE;

		//0-127 is the range of ascii chars
		for (int i=0; i < 128; i++) {
			byte[] singleCharByteArray = new byte[binaryInput.getRawBytes().length];
			for (int j=0; j < singleCharByteArray.length; j++) {
				singleCharByteArray[j] = (byte)i;
			}
			BinaryString singleCharBinaryString = new BinaryString(singleCharByteArray);

			String tempGuess = binaryInput.xor(singleCharBinaryString).toAscii();
			EnglishScorer scorer = new EnglishScorer(tempGuess);
			int tempScore = scorer.calculateScore();
			if (tempScore > score) {
				score = tempScore;
				xorChar = (char)i;
				bestGuess = tempGuess;
			}
			System.out.println(
				"tempXorChar == " + (char)i + ", score == " + tempScore +", guess == " + tempGuess);
		}
		System.out.println(
			"xorChar == " + xorChar + ", score == " + score + ", best guess = " + bestGuess);
	}
}
