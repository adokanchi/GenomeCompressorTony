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
    private static final int[] mapCompress = new int[85];
    private static final char[] mapExpand = new char[4];
    private static final int HEADER_SIZE = 8;
    private static final int CHAR_SIZE = 2;

    public static void compress() {
        mapCompress['A'] = 0b00;
        mapCompress['C'] = 0b01;
        mapCompress['G'] = 0b10;
        mapCompress['T'] = 0b11;

        String s = BinaryStdIn.readString();
        int n = s.length();

        // Header
        BinaryStdOut.write(n, HEADER_SIZE);

        while (!s.isEmpty()) {
            BinaryStdOut.write(mapCompress[s.charAt(0)], CHAR_SIZE);
            s = s.substring(1);
        }
        BinaryStdOut.close();
    }

    /**
     * Reads a binary sequence from standard input; expands and writes the results to standard output.
     */
    public static void expand() {
        mapExpand[0b00] = 'A';
        mapExpand[0b01] = 'C';
        mapExpand[0b10] = 'G';
        mapExpand[0b11] = 'T';

        int length = BinaryStdIn.readInt(HEADER_SIZE);

        for (int i = 0; i < length; i++) {
            int input = BinaryStdIn.readInt(CHAR_SIZE);
            BinaryStdOut.write(mapExpand[input]);
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