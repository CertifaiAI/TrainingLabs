package global.skymind.solution.nd4j;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

public class Ex5_ScalarOperations {
    public static final String BLACK_BOLD = "\033[1;30m";
    public static final String BLUE_BOLD = "\033[1;34m";
    public static final String ANSI_RESET = "\u001B[0m";

    public static void main(String[] args) {
        int nRows = 3;
        int nColumns = 5;
        INDArray myArray = Nd4j.rand(123, new long[]{nRows,nColumns});
        System.out.println(BLACK_BOLD + "Default array" + ANSI_RESET);
        System.out.println(myArray);

        //add operation
        INDArray addArray = myArray.add(1);
        System.out.println(BLACK_BOLD + "\nAdd 1 to array" + ANSI_RESET);
        System.out.println(BLUE_BOLD + "myArray.add(1)" + ANSI_RESET);
        System.out.println(addArray);

        //add operation inplace
        myArray.addi(1);
        System.out.println(BLACK_BOLD + "\nAdd 1 to array inplace" + ANSI_RESET);
        System.out.println(BLUE_BOLD + "myArray.addi(1)" + ANSI_RESET);
        System.out.println(myArray);

        //add array to array
        INDArray randomArray = Nd4j.rand(3,5);
        INDArray addArraytoArray = myArray.add(randomArray);
        System.out.println(BLACK_BOLD + "\nAdd random array to array (array have to be in same size)" + ANSI_RESET);
        System.out.println(BLUE_BOLD + "myArray.add(randomArray)" + ANSI_RESET);
        System.out.println(addArraytoArray);

        // try out myArray.sub, myArray.div, myArray.mul
    }
}
