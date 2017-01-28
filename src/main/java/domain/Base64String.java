package domain;

public class Base64String {

	private static String BASE_64 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

	private final BinaryString binary;

	public Base64String(BinaryString bs) {
		this.binary = bs;
	}

	public String toBase64() {
		byte[] bytes = binary.getRawBytes();
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
			int tmp = ((bytes[byteIdx] << shiftLeft) & 0xFF);
			tmp = ((tmp >> shiftRight) & 0xFF);
			if (numBitsFromNextByte > 0 && byteIdx < bytes.length-1)
				tmp += (bytes[byteIdx+1] >> (8-numBitsFromNextByte));

			//3) calculate the base_64 character by converting the bits to base10 and then do a simple lookup
			base64[i] = BASE_64.charAt(tmp);
		}
		return new String(base64);
	}
}