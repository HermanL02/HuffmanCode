package Assignment4;
/**Herman Liang
 * B00837314
 * Class: Huffman.java
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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
public class Huffman {

    /**
    Code
     provided from previous version and modified for 2020.
     * @throws IOException 
    */
    @SuppressWarnings("unchecked")
	public static void encode()throws IOException{
        // initialize Scanner to capture user input
        Scanner sc = new Scanner(System.in);

        // capture file information from user and read file
        System.out.print("Enter the filename to read from/encode: ");
        String f = sc.nextLine();

        // create File object and build text String
        File file = new File(f);
        Scanner input = new Scanner(file).useDelimiter("\\Z");
        String text = input.next();

        // close input file
        input.close();

        // initialize Array to hold frequencies (indices correspond to
        // ASCII values)
        int[] freq = new int[256];
        // concatenate/sanitize text String and create character Array
        // nice that \\s also consumes \n and \r
        // we can add the whitespace back in during the encoding phase

        char[] chars = text.replaceAll("\\s", "").toCharArray();
        
        // count character frequencies
        for(char c: chars)
            freq[c]++;


        //Your work starts here************************************8
        //A new arraylist is created to keep track of the pairs
        ArrayList<Pair> pairs = new ArrayList<Pair>();
        ArrayList<Pair> AllPairs = new ArrayList<Pair>();
        for(int i = 0; i<256; i++){
            if(freq[i]!=0){
                // this method of rounding is good enough
                Pair p = new Pair((char)i, Math.round(freq[i]*10000d/chars.length)/10000d);
                AllPairs.add(p);
                pairs.add(p);
            }
        }
        //System.out.println(pairs.size());

       //Apply the huffman algorithm here and build the tree ************************************
        Queue S = new LinkedList<BinaryTree<Pair>>();
        Queue T = new LinkedList<BinaryTree<Pair>>();
        for(int i = 0; i < 256; i++) {
        	Pair minPair = new Pair('0',0);
        	double minProb = 1.0;
        	int location = 0;
        	for(int j = 0; j < pairs.size(); j++) {
        		if(pairs.get(j).getProb()<minProb&&pairs.get(j).getProb()>0) {
        			minPair = pairs.get(j);
        			minProb = pairs.get(j).getProb();
        			location = j;
        		}
        	}
        	if(minProb == 1.0) {
        		break;
        	}
        	pairs.remove(location);
        	BinaryTree<Pair> p = new BinaryTree<Pair>();
        	p.makeRoot(minPair);
        	S.offer(p);
        }
        
        //Logic part
         
        while(true) {
        	if(S.size()==0) {
        		break;
        	}
        	BinaryTree<Pair> A = null;
    		BinaryTree<Pair> B = null;
    		
    		//The below part is not sure
    		if(S.size()==1) {
    			A = (BinaryTree<Pair>) S.poll();
    			BinaryTree<Pair> P = new BinaryTree<Pair>();
    			Pair n = new Pair(A.getData().getValue(),A.getData().getProb());
    			P.makeRoot(n);
    			T.offer(P);
    			break;
    		}
    		
    		//Combine S and T queue to get Huffman Tree
        	if(T.size()==0) {
        		A = (BinaryTree<Pair>) S.poll();
        		B = (BinaryTree<Pair>) S.poll();
        	}else if (T.size()==1){
        		A = (BinaryTree<Pair>) S.poll();
        		B =(BinaryTree<Pair>) T.poll();
        	}else {
        		if(((BinaryTree<Pair>) S.peek()).getData().getProb()<((BinaryTree<Pair>) T.peek()).getData().getProb()) {
        			A = (BinaryTree<Pair>) S.poll();
        		}else {
        			A =(BinaryTree<Pair>) T.poll();
        		}
        		if(((BinaryTree<Pair>) S.peek()).getData().getProb()<((BinaryTree<Pair>) T.peek()).getData().getProb()) {
        			B = (BinaryTree<Pair>) S.poll();
        		}else {
        			B =(BinaryTree<Pair>) T.poll();
        		}
        	}
        	
        	BinaryTree<Pair> P = new BinaryTree<Pair>();
        	Pair n = new Pair('⁂',A.getData().getProb()+B.getData().getProb());
        	P.makeRoot(n);   
        	P.setLeft(A);
        	P.setRight(B);
        	T.offer(P);
        	
        }
        //To combine all the BinaryTrees in T queue to Huffman Tree
        while(T.size()>1) {
        	BinaryTree<Pair> A = (BinaryTree<Pair>) T.poll();
        	BinaryTree<Pair> B = (BinaryTree<Pair>) T.poll();
        	BinaryTree<Pair> P = new BinaryTree<Pair>();
        	//(char)(A.getData().getValue()+B.getData().getValue())
        	Pair n = new Pair('⁂',A.getData().getProb()+B.getData().getProb());
        	P.makeRoot(n);
        	P.setLeft(A);
        	P.setRight(B);
        	T.offer(P);
        }
        BinaryTree<Pair> HuffmanTree = (BinaryTree<Pair>) T.poll();
        //Huffman Codes
        String[] codes = findEncoding(HuffmanTree);
        System.out.println("Enter the file name to store Huffman codes");
        String outfilename = sc.nextLine();
        System.out.println("Codes generated.Printing codes to Huffman.txt");
        //Create a PrintWriter to store Huffman code Table
        PrintWriter output = new PrintWriter(new FileWriter(outfilename));
        output.println("Symbol Prob HuffmanCode");
        output.println();
        HashMap<Character, String> CharToHuffman = new HashMap<Character,String>();
        for(int i = 0; i < AllPairs.size();i++) {
        	Pair temp = AllPairs.get(i);
        	output.print(temp.getValue()+" ");
        	output.print(temp.getProb()+" ");
        	output.println(codes[temp.getValue()]);
        	CharToHuffman.put(temp.getValue(),codes[temp.getValue()]);
        }
        /* Checking the array
         * for(int i = 0; i<codes.length;i++) {
        	System.out.println(codes[i]);
        	}
         */
        System.out.println("Enter the file name to store the encoded text:");
        String outfilename2 = sc.nextLine();
        System.out.println("Printing encoded text to Encoded.txt");
        PrintWriter output2 = new PrintWriter(new FileWriter(outfilename2));
        //Encoding Part
        	for(int i = 0; i < text.length();i++) {
        		if(text.charAt(i)!=' ') {
        			output2.print(CharToHuffman.get(text.charAt(i)));
        		}else {
        			output2.print(" ");
        		}
        	}
        output.close();
        output2.close();
        

    }


    public static void decode()throws IOException{
        // initialize Scanner to capture user input
        Scanner sc = new Scanner(System.in);

        // capture file information from user and read file
        System.out.print("Enter the filename to read from/decode: ");
        String f = sc.nextLine();

        // create File object and build text String
        File file = new File(f);
        Scanner input = new Scanner(file).useDelimiter("\\Z");
        String text = input.next();
        // ensure all text is consumed, avoiding false positive end of
        // input String
        input.useDelimiter("\\Z");
        //text += input.next();


        // close input file
        input.close();

        // capture file information from user and read file
        System.out.print("Enter the filename of document containing Huffman codes: ");
        f = sc.nextLine();

        // create File object and build text String
        file = new File(f);
        input = new Scanner(file).useDelimiter("\\Z");
        String codes = input.next();

        // close input file
        input.close();

        //Your work starts here********************************************
        HashMap<String,Character> HuffmanToChar = new HashMap<String,Character>();
        Scanner Is = new Scanner(codes);
        Is.nextLine();
        Is.nextLine();
        
        while(Is.hasNextLine()){
        	char c = Is.next().charAt(0);
        	Is.next();// consume/discard probability
        	String s = Is.next();
        	HuffmanToChar.put(s,c);
        }
        System.out.print("Enter the filename of decode.txt: ");
        String pt = sc.nextLine();
        PrintWriter ot = new PrintWriter(new FileWriter(pt));
        int pivot = 0;
        int pivot2 = 1;
        while(true) {
        	if(pivot == text.length()-1||pivot == text.length()) {
        		break;
        	}
        	if(pivot2-15>pivot &&pivot2==text.length()) {
        		break;
        	}
        	String temp = text.substring(pivot,pivot2);
        	if(temp.equals(" ")) {
        		ot.print(" ");
        		pivot++;
        		pivot2++;
        		continue;
        	}
        	if(HuffmanToChar.containsKey(temp)) {
        		ot.print(HuffmanToChar.get(temp));
        		pivot = pivot2;
        		pivot2 = pivot+1;
        		continue;
        	}else {
        		pivot2++;
        		continue;
        	}
        }
        System.out.println("printing decoded text to decoded.txt");
        ot.close();
        Is.close();
        input.close();



    }

    // the findEncoding helper method returns a String Array containing
    // Huffman codes for all characters in the Huffman Tree (characters not
    // present are represented by nulls)
    // this method was provided by Srini (Dr. Srini Sampalli). Two versions are below, one for Pairtree and one for BinaryTree


    private static String[] findEncoding(BinaryTree<Pair> bt){
        String[] result = new String[256];
        findEncoding(bt, result, "");
        return result;
    }


    private static void findEncoding(BinaryTree<Pair> bt, String[] a, String prefix){
        // test is node/tree is a leaf
        if (bt.getLeft()==null && bt.getRight()==null){
            a[bt.getData().getValue()] = prefix;
        }
        // recursive calls
        else{
            findEncoding(bt.getLeft(), a, prefix+"0");
            findEncoding(bt.getRight(), a, prefix+"1");
        }
    }

    /*
    private static String[] findEncoding(Peartree pt){
        // initialize String array with indices corresponding to ASCII values
        String[] result = new String[256];
        // first call from wrapper
        findEncoding(pt, result, "");
        return result;
    }

    private static void findEncoding(Peartree pt, String[] a, String prefix){
        // test is node/tree is a leaf
        if (pt.getLeft()==null && pt.getRight()==null){
            a[pt.getValue()] = prefix;
        }
        // recursive calls
        else{
            findEncoding(pt.getLeft(), a, prefix+"0");
            findEncoding(pt.getRight(), a, prefix+"1");
        }
    }
*/

}
