import domain.BinaryString;
import domain.EnglishScorer;
import domain.HexString;

public class SingleByteXORDecoder {

	public static void main (String args[]) {
		String input = "1b37373331363f78151b7f2b783431333d78397828372d363c78373e783a393b3736";
		Result r = new SingleByteXORDecoder(new HexString(input)).decode();
		System.out.println("xorChar == " + r.xorChar + ", score == " + r.score + ", best guess = " + r.decodedText);
	}

	private final BinaryString input;

	public SingleByteXORDecoder(HexString i) {
		this.input = i.toBinary();
	}

	public Result decode() {

		Result bestResult = null;

		//0-127 is the range of ascii chars
		for (int i=0; i < 128; i++) {
			String tempGuess = input.sbXor((byte)i).toAscii();
			EnglishScorer scorer = new EnglishScorer(tempGuess);
			int tempScore = scorer.calculateScore();
			if (bestResult == null || bestResult.score < tempScore) {
				bestResult = new Result(tempGuess, tempScore, (char)i);
			}
			//System.out.println("tempXorChar == " + (char)i + ", score == " + tempScore +", guess == " + tempGuess);
		}


		return bestResult;
	}

	public static class Result {
		private final String decodedText;
		private final int score;
		private final Character xorChar;

		private Result(String dT, int s, Character xor) {
			this.decodedText = dT;
			this.score = s;
			this.xorChar = xor;
		}

		public String getDecodedText() {
			return decodedText;
		}

		public int getScore() {
			return score;
		}

		public Character getXorChar() {
			return xorChar;
		}
	}
}
