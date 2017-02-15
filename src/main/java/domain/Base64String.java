package domain;

public class Base64String {

	private static String BASE_64_LOOKUP = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

	private final char[] base64Text;

	public Base64String(String s) {
		this.base64Text = s.toCharArray();
	}

	public BinaryString toBinary() {

		//for each char, add 6 bits to the bit string. keeping track of which byte(s) to add to
		//<00>011010 == 26
		//011010<00> == normalized
		//<000000>01 == right shifted (offset of 6)
		//1010<0000> == second byte left shifted
		//<0000>0110
		//1111
		int bitIdx = 0;
		byte[] bytes = new byte[(int)Math.ceil((6*base64Text.length)/8)];
		for (char c: base64Text) {
			int firstByteIdx = bitIdx/8;
			int firstByteOffset = bitIdx%8;
			byte byteToAdd = (byte)(BASE_64_LOOKUP.indexOf(c)&0xFF);
			byteToAdd = (byte)(byteToAdd<<2); //normalization

			//need to use '>>>' instead of '>>' (means logical shift instead of signed shift)
			//must convert to unsigned int prior to shift otherwise signed bits will be bit shifted
			byte firstByte = (byte)((byteToAdd&0xFF)>>>firstByteOffset);
			bytes[firstByteIdx] = (byte)(bytes[firstByteIdx]|firstByte);
			if (firstByteOffset > 2) {
				byte secondByte=(byte)((byteToAdd<<(8-firstByteOffset)));
				bytes[firstByteIdx+1]=secondByte;
			}
			bitIdx+=6;
		}
		return new BinaryString(bytes);
	}

	public String toString() {
		return String.valueOf(base64Text);
	}
}