package domain;

import java.util.Arrays;

public class HexString {

	private final char[] hex;

	public HexString(String s) {
		hex = s.toCharArray();
	}

	public BinaryString toBinary() {
		byte[] bytes = new byte[(int) Math.ceil((double)hex.length / (double)2)];
		int cnt = 0;
		for (int i = bytes.length; i > 0; i--) {
			int idx = Math.max(0, hex.length-(cnt*2)-2);
			String hexByte = String.valueOf(Arrays.copyOfRange(hex, idx, idx + 2));
			bytes[i-1] = (byte)(Integer.parseInt(hexByte, 16) & 0xFF);
			cnt++;
		}
		return new BinaryString(bytes);
	}

	@Override
	public String toString() {
		return String.valueOf(hex);
	}
}