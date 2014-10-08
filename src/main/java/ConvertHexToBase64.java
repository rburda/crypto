import java.util.Arrays;

public class ConvertHexToBase64 {

  private static String BASE_64 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

  public static void main(String args[]) {
    String hex = "6f6f6d";
    char[] hexChar = hex.toCharArray();
    byte[] bytes = new byte[(int) Math.ceil((double)hexChar.length / (double)2)];
    int cnt = 0;
    for (int i = bytes.length; i > 0; i--) {
      int idx = Math.max(0, hex.length()-(cnt*2)-2);
      String hexByte = String.valueOf(Arrays.copyOfRange(hexChar, idx, idx + 2));
      bytes[i-1] = (byte)(Integer.parseInt(hexByte, 16) & 0xFF);
      cnt++;
    }

    for (int i=0; i < bytes.length; i++)
    {
      for (int j=7; j >=0; j--)
      {
        System.out.print((bytes[i] >> j) & 1);
      }
      System.out.print(" ");
    }
    System.out.println("\n**");

    int extra = ((bytes.length * 8) % 6) > 0 ? 1 : 0;
    char[] base64 = new char[(bytes.length * 8 / 6) + extra];
    for (int i=0; i < bytes.length; i++)
    {
      int numBits = 6*i;
      int byteIdx = ((6*i)/8);
      int offset = (numBits < 8 ? numBits : (numBits % 8));
      int numBitsFromNextByte = Math.max(0, ((offset + 6) - 8));
      int base64Idx = Integer.valueOf( bytes[byteIdx] >> (8 - (6 - numBitsFromNextByte)));
      if (numBitsFromNextByte > 0 && i < bytes.length-1)
        base64Idx += Integer.valueOf (bytes[byteIdx] & numBitsFromNextByte);
      base64[i] = BASE_64.charAt(base64Idx);
    }

   System.out.println(String.valueOf(base64));
  }

}
