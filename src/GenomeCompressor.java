/******************************************************************************
 *  Compilation:  javac GenomeCompressor.java
 *  Execution:    java GenomeCompressor - < input.txt   (compress)
 *  Execution:    java GenomeCompressor + < input.txt   (expand)
 *  Dependencies: BinaryIn.java BinaryOut.java
 *  Data files:   genomeTest.txt
 *                virus.txt
 *
 *  Compress or expand a genomic sequence using a 2-bit code.
 ******************************************************************************/

/**
 *  The {@code GenomeCompressor} class provides static methods for compressing
 *  and expanding a genomic sequence using a 2-bit code.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 *  @author Zach Blick
 */
public class GenomeCompressor {

    /**
     * Reads a sequence of 8-bit extended ASCII characters over the alphabet
     * { A, C, T, G } from standard input; compresses and writes the results to standard output.
     */
    private static boolean[][] map = new boolean[85][2];
    private static char[] map2 = new char[4];



    public static void compress() {
        // 'A' --> 00
        // 'C' --> 01
        map['C'][1] = true;
        // 'G' --> 10
        map['G'][0] = true;
        // 'T' --> 11
        map['T'][0] = true;
        map['T'][1] = true;

        // Read in 32 bits (4 chars)
        String s = BinaryStdIn.readString();
        while (s.isEmpty()) {
            char c = s.charAt(0);
            BinaryStdOut.write(map[c][0]);
            BinaryStdOut.write(map[c][1]);
            s = s.substring(1);
        }
        BinaryStdOut.close();
    }

    /**
     * Reads a binary sequence from standard input; expands and writes the results to standard output.
     */
    public static void expand() {
        map2[0] = 'A';
        map2[1] = 'C';
        map2[2] = 'G';
        map2[3] = 'T';

        while (!BinaryStdIn.isEmpty()) {
            int i = BinaryStdIn.readInt(2);
            BinaryStdOut.write(map2[i]);
        }
        BinaryStdOut.close();
    }


    /**
     * Main, when invoked at the command line, calls {@code compress()} if the command-line
     * argument is "-" an {@code expand()} if it is "+".
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {

        if      (args[0].equals("-")) compress();
        else if (args[0].equals("+")) expand();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}