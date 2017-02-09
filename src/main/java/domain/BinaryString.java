package domain;

import java.util.Arrays;

public class BinaryString {

	private static char[] HEX_LOOKUP = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};

	private final byte[] bytes;

	public BinaryString(byte[] bytes) {
		assert(bytes != null);
		this.bytes = bytes;
	}

	public byte[] getRawBytes() {
		return bytes;
	}

	public BinaryString sbXor(byte b) {
		byte[] xor = new byte[bytes.length];
		for (int i=0; i < bytes.length; i++) {
			xor[i] = (byte)(bytes[i]^b);
		}
		return new BinaryString(xor);
	}

	public BinaryString xor(BinaryString bs) {
		if (bs.getRawBytes().length != bytes.length) {
			throw new IllegalArgumentException("binary strings must be same length");
		}
		byte[] xor = new byte[bytes.length];
		for (int i=0; i < bytes.length; i++) {
			xor[i] = (byte)(bytes[i]^bs.bytes[i]);
		}
		return new BinaryString(xor);
	}

	public String toAscii() {
		char[] chars = new char[bytes.length];
		for (int i=0; i < bytes.length; i++) {
			chars[i] = (char) (bytes[i] & 0xFF);
		}
		return new String(chars);
	}

	public HexString toHex() {
		char[] hexChar = new char[bytes.length*2];
		for (int i=0; i < hexChar.length; i++) {
			//logical and by '0xFF' ensures that we get back an unsigned int
			//if i is even, then we want to create the char for the first left half of the byte
			if (i % 2 == 0) {
				hexChar[i] = HEX_LOOKUP[(bytes[i/2]>>4&0xFF)];
			//else, we're creating the character for the last half of the byte
			} else {
				hexChar[i] = HEX_LOOKUP[((bytes[i/2]<<4&0xFF)>>4)];
			}
		}
		return new HexString(new String(hexChar));
	}

	public String toBitString() {
		StringBuilder builder = new StringBuilder();
		for (int i=0; i < bytes.length; i++)
		{
			for (int j=7; j >=0; j--)
			{
				builder.append(((bytes[i] >> j) & 1));
			}
			builder.append(" ");
		}
		return builder.toString();
	}
}