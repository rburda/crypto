import domain.BinaryString;

public class TestHammingDistance {

	public static void main(String args[]) {
		String x = "this is a test";
		String y = "wokka wokka!!!";

		BinaryString bsX = new BinaryString(x.getBytes());
		BinaryString bsY = new BinaryString(y.getBytes());

		System.out.println(bsX.toBitString());
		System.out.println(bsY.toBitString());
		System.out.println("hamming distance: " + bsX.hammingDistance(bsY));
	}
}
