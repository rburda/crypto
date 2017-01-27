import java.util.Arrays;

/**
 Overview:

 base64 is represented by taking the base 10 value of a bit string that is 6 bits long. Using that value, we look
 up the appropriate character to represent it. The possible characters are:
 ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/
 so if a bit string was: 000001 we would use 'A' or conversely, if we had a string of 111111 we would use /

 The algorithm to convert hex to base-64 is the following:
 1) convert hex string to binary (ex: 6d (hex) == 0110 1101 (binary)
 2) traverse over the resulting bit string (not respecting byte boundaries) 6 bits at a time
 (starting from the most significant byte)
 3) for each 6 bits, calculate its binary value and use the above lookup to determine the appropriate character.


 Example: Input (in hex) '6d'

 bit value lookup
 128 64 32 16 8 4 2 1
 0   0  0  0  0 0 0 0

 1) Convert Hex To Binary:
 0x6d in base10 				== 6(16^1) + 13(16^0) == 96 + 13 = 109
 109 base10 in binary 	== 0110 1101


 2) Traverse bit string 6 bits at a time:
 Take first 6 bits of the byte (from the left). If there are not enough bits, borrow from the next
 byte. To do this we first left shift off any bytes that were used for a previous iteration, then shift to the
 right the number of bytes necessary to put the remaining bits in their correct position. If we need to borrow bits
 from the next byte, they will be placed on the right end of the new bit string for a total of 6 bytes.

 For the first byte, we don't shift left any bits because we didn't use any from a previous iteration
 0110 1101 (after left shift)

 We shift right two bits since we only will be using 6 out of the 8 bits for the first character in the base64
 string
 0001 1011 (after right shift)

 3) calculate the binary value and lookup in base_64 table
 get the int value (in base 10)
 27
 look at the above BASE_64 string and get the char at the 27th index
 b

 for the second character, we do the same thing
 0110 1101 (hex)
 offset = 6 (since we used 6 bits for the previous character)
 0100 0000 (after left shift)
 we shift the bytes over two spots because we leave 4 spots open on the right for bits from the next byte
 (if it exists -- which in our case it doesn't)
 0001 0000 (after right shift)
 Q

 final output: bQ
 */
public class ConvertHexToBase64 {

  private static String BASE_64 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

  public static void main(String args[]) {
    //our input
    String hex = "49276d206b696c6c696e6720796f757220627261696e206c696b65206120706f69736f6e6f7573206d757368726f6f6d";
    //String hex ="6d";

    //1) convert hex string to binary
    char[] hexChar = hex.toCharArray();
    byte[] bytes = new byte[(int) Math.ceil((double)hexChar.length / (double)2)];
    int cnt = 0;
    for (int i = bytes.length; i > 0; i--) {
      int idx = Math.max(0, hex.length()-(cnt*2)-2);
      String hexByte = String.valueOf(Arrays.copyOfRange(hexChar, idx, idx + 2));
      bytes[i-1] = (byte)(Integer.parseInt(hexByte, 16) & 0xFF);
      cnt++;
    }


    //sanity check: show what the bit string is of the hex string converted to binary
    for (int i=0; i < bytes.length; i++)
    {
      for (int j=7; j >=0; j--)
      {
        System.out.print((bytes[i] >> j) & 1);
      }
      System.out.print(" ");
    }
    System.out.println("\n**");

    //2) iterate over the bit string 6 bits at a time
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

    //output the final result
    System.out.println(String.valueOf(base64));
  }
}
