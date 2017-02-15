import domain.BinaryString;

public class TestBitShift {

	public static void main(String args[]) {
		BinaryString bs  = new BinaryString(new byte[] {(byte)0xB4});

		byte x = (byte)0xB4;
		byte y = (byte)0x2;
		System.out.println("'2'         = " + BinaryString.toBitString(y));
		System.out.println("'x'         = " + BinaryString.toBitString(x));
		System.out.println("'x>>>2'     = " + BinaryString.toBitString((byte)(x>>>2)));
		System.out.println("'x>>>2&0xFF = " + BinaryString.toBitString((byte)((x&0xFF)>>>2)));
	}
}
