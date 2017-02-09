import domain.BinaryString;

public class RepeatingKeyXOR {

	public static void main (String args[]) {
		String input = "Burning 'em, if you ain't quick and nimble\nI go crazy when I hear a cymbal";

		System.out.println("repeatingKeyXOR == " +
			new BinaryString(input.getBytes()).repeatingKeyXor(new BinaryString("ICE".getBytes())).toHex().toString());
	}
}
