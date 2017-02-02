import domain.BinaryString;
import domain.HexString;

public class FixedXOR {

	public static void main (String args[]) {
		HexString s1 = new HexString("1c0111001f010100061a024b53535009181c");
		HexString s2 = new HexString("686974207468652062756c6c277320657965");

		BinaryString b1 = s1.toBinary();
		BinaryString b2 = s2.toBinary();

		System.out.println("b1  == " + b1.toBitString());
		System.out.println("b2  == " + b2.toBitString());

		BinaryString xor = b1.xor(b2);
		System.out.println("xor == " + xor.toBitString());

		System.out.println("result: " + xor.toHex());
	}
}
