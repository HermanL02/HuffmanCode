package Assignment4;




/**
This class describes a simple Pair class for storing character/double pairs. Used for A4 from previous version.
*/
/**Herman Liang
 * B00837314
 * Class: Pair.java
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
public class Pair implements Comparable<Pair>{
    // declare all required fields
    private char value;
    private double prob;

    /**
    Constructor 1
    All args accounted for
    */
    public Pair(char v, double p){
        value = v;
        prob = p;
    }

    public void setValue(char v){
        value = v;
    }

    public void setProb(double p){
        prob = p;
    }

    public char getValue(){
        return value;
    }

    public double getProb(){
        return prob;
    }

    /**
    The compareTo method overrides the compareTo method of the Comparable
    interface.
    */
    @Override
    public int compareTo(Pair p){
        return Double.compare(this.getProb(), p.getProb());
    }

    /**
    The toString method overrides the toString method of the Object class.
    */
    @Override
    public String toString(){
        return value+" : "+prob;
    }
}
