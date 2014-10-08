import java.util.Arrays;

public class ConvertHexToBase64 {

  private static String BASE_64 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

  public static void main(String args[]) {
    String hex = "49276d206b696c6c696e6720796f757220627261696e206c696b65206120706f69736f6e6f7573206d757368726f6f6d";
    char[] hexChar = hex.toCharArray();
    byte[] bytes = new byte[(int) Math.ceil((double)hexChar.length / (double)2)];
    int cnt = 0;
    for (int i = bytes.length; i > 0; i--) {
      int idx = Math.max(0, hex.length()-(cnt*2)-2);
      String hexByte = String.valueOf(Arrays.copyOfRange(hexChar, idx, idx + 2));
      bytes[i-1] = (byte)(Integer.parseInt(hexByte, 16) & 0xFF);
      cnt++;
    }

    /*
    for (int i=0; i < bytes.length; i++)
    {
      for (int j=7; j >=0; j--)
      {
        System.out.print((bytes[i] >> j) & 1);
      }
      System.out.print(" ");
    }
    System.out.println("\n**");
    */
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
      base64[i] = BASE_64.charAt(tmp);
    }
   System.out.println(String.valueOf(base64));
  }
}