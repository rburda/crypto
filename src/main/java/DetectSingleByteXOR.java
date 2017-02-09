import domain.HexString;

import java.io.File;
import java.util.Scanner;

public class DetectSingleByteXOR {

	public static void main(String args[]) throws Exception {
		String filename = (args.length > 0 ? args[0] : "detectSingleByteXOR.txt");

		Scanner s = new Scanner(new File(filename));
		SingleByteXORDecoder.Result bestResult = null;
		while (s.hasNextLine()) {
			SingleByteXORDecoder decoder = new SingleByteXORDecoder(new HexString(s.nextLine()));
			SingleByteXORDecoder.Result r = decoder.decode();
			if (bestResult == null || r.getScore() > bestResult.getScore()) {
				bestResult = r;
			}
		}
		System.out.println("best result");
		System.out.println("text: " + bestResult.getDecodedText() + ", score: " + bestResult.getScore());
	}
}