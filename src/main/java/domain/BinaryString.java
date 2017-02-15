package domain;

import java.util.Arrays;

public class BinaryString {

	private static String BASE_64_LOOKUP = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
	private static char[] HEX_LOOKUP = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};

	private final byte[] bytes;

	public BinaryString(byte[] bytes) {
		assert(bytes != null);
		this.bytes = bytes;
	}

	public byte[] getRawBytes() {
		return bytes;
	}

	public BinaryString singleByteXor(byte b) {
		return repeatingKeyXor(new BinaryString(new byte[]{b}));
	}

	public BinaryString fixedXor(BinaryString bs) {
		if (bs.getRawBytes().length != bytes.length) {
			throw new IllegalArgumentException("binary strings must be same length");
		}
		return repeatingKeyXor(bs);
	}

	public BinaryString repeatingKeyXor(BinaryString bs) {
		byte[] xor = new byte[bytes.length];
		byte[] repeater = bs.getRawBytes();
		for (int i=0; i < bytes.length; i++) {
			xor[i] = (byte)(bytes[i]^repeater[i % repeater.length]);
		}
		return new BinaryString(xor);
	}

	public BinaryString slice(int end) {
		return slice(0, end);
	}

	public BinaryString slice(int beg, int end) {
		if (beg < 0 || end > bytes.length || beg > end) {
			throw new IllegalArgumentException();
		}
		return new BinaryString(Arrays.copyOfRange(bytes, beg, end));
	}

	/**
	 * the number of differing bits
	 * @param bs
	 * @return
	 */
	public int hammingDistance(BinaryString bs) {
		int distance = 0;
		byte[] other = bs.getRawBytes();
		int length = (bytes.length > other.length ? bytes.length : other.length);
		for (int i=0; i < length; i++) {
			for (int j=7; j >=0; j--) {
				if (bytes.length > i && other.length > i) {
					byte bx = (byte)((bytes[i] >> j)&1);
					byte by = (byte)((other[i] >> j)&1);
					distance+=(bx ^ by);
				} else {
					distance++;
				}
			}
		}
		return distance;
	}

	public String toAscii() {
		char[] chars = new char[bytes.length];
		for (int i=0; i < bytes.length; i++) {
			chars[i] = (char) (bytes[i] & 0xFF);
		}
		return new String(chars);
	}

	public Base64String toBase64() {
		int extra = ((bytes.length * 8) % 6) > 0 ? 1 : 0;
		char[] base64 = new char[(bytes.length * 8 / 6) + extra];
		for (int i=0; i < base64.length; i++)
		{
			int startingBit = 6*i;
			int byteIdx = (startingBit/8);
			int offset = (startingBit % 8);
			int numBitsFromNextByte = Math.max(0, ((offset + 6) - 8));
			int shiftLeft = offset;
			int shiftRight = shiftLeft + (8-(offset+6));
			int tmp = ((bytes[byteIdx] << shiftLeft) & 0xFF); //logical and by '0xFF' gives an unsigned int
			tmp = ((tmp&0xFF) >>> shiftRight);
			if (numBitsFromNextByte > 0 && byteIdx < bytes.length-1)
				tmp += (bytes[byteIdx+1] >>> (8-numBitsFromNextByte));

			//3) calculate the base_64 character by converting the bits to base10 and then do a simple lookup
			base64[i] = BASE_64_LOOKUP.charAt(tmp);
		}
		return new Base64String(String.valueOf(base64));
	}

	public HexString toHex() {
		char[] hexChar = new char[bytes.length*2];
		for (int i=0; i < hexChar.length; i++) {
			//logical and by '0xFF' ensures that we get back an unsigned int
			//if i is even, then we want to create the char for the first left half of the byte
			if (i % 2 == 0) {
				hexChar[i] = HEX_LOOKUP[((bytes[i/2]&0xFF)>>>4)];
			//else, we're creating the character for the last half of the byte
			} else {
				hexChar[i] = HEX_LOOKUP[(((bytes[i/2]<<4)&0xFF)>>>4)];
			}
		}
		return new HexString(new String(hexChar));
	}

	public String toBitString() {
		return toBitString(bytes);
	}

	public static String toBitString(byte[] b) {
		StringBuilder builder = new StringBuilder();
		for (int i=0; i < b.length; i++) {
			builder.append(toBitString(b[i])).append(" ");
		}
		return builder.toString();
	}

	public static String toBitString(byte b) {
		StringBuilder builder = new StringBuilder();
		for (int j=7; j >=0; j--) {
			builder.append(((b >>> j) & 1));
		}
		return builder.toString();
	}

	@Override
	public String toString() {
		return toBitString();
	}
}