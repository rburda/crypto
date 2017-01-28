package domain;

public class BinaryString {

	private final byte[] bytes;

	public BinaryString(byte[] bytes) {
		this.bytes = bytes;
	}

	public byte[] getRawBytes() {
		return bytes;
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