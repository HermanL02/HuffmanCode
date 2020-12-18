package Assignment4;
/**Herman Liang
 * B00837314
 * Class: HuffmanDemo.java
 * 
 * READ THIS FIRST:
 * The PDF indicates that the only path we need to input in ENCODING PART is the path of INPUT FILE. 
 * However, I think to make it more regulated. During the Encode Part, there are three inputs. 
 * The first input is the path of the input. 
 * The second input is the path of the Huffman.txt file. 
 * The third input is the path of the Encoded.txt file. 
 * 
 * Samething for DECODE METHOD
 * The first input is the path of the Encoded.txt
 * The second input is the path of the Huffman.txt
 * The third input is the path of the Decoded.txt
 * 
 * Just want to make the work more precise
 */
import java.io.IOException;

public class HuffmanDemo {
	public static void main(String[]args) throws IOException {
		Huffman.encode();
		Huffman.decode();	
	}
	
	
}
