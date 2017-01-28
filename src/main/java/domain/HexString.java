package domain;

import java.util.Arrays;

public class HexString {

	private final String hex;

	public HexString(String s) {
		hex = s;
	}

	public byte[] toBinary() {
		char[] hexChar = hex.toCharArray();
		byte[] bytes = new byte[(int) Math.ceil((double)hexChar.length / (double)2)];
		int cnt = 0;
		for (int i = bytes.length; i > 0; i--) {
			int idx = Math.max(0, hex.length()-(cnt*2)-2);
			String hexByte = String.valueOf(Arrays.copyOfRange(hexChar, idx, idx + 2));
			bytes[i-1] = (byte)(Integer.parseInt(hexByte, 16) & 0xFF);
			cnt++;
		}
		return bytes;
	}
}