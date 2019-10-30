package global.skymind.training.nd4j;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.ops.transforms.Transforms;

public class Ex6_TransformOperations {
    public static final String BLACK_BOLD = "\033[1;30m";
    public static final String BLUE_BOLD = "\033[1;34m";
    public static final String ANSI_RESET = "\u001B[0m";

    public static void main(String[] args) {
        int nRows = 3;
        int nColumns = 5;
        INDArray myArray = Nd4j.rand(123, new long[]{nRows, nColumns});
        System.out.println(BLACK_BOLD + "Default array" + ANSI_RESET);
        System.out.println(myArray);

        //array log
//        INDArray logArray = Transforms.log(myArray);
//        System.out.println(BLACK_BOLD + "\nArray log transform" + ANSI_RESET);
//        System.out.println(BLUE_BOLD + "Transforms.log(myArray)" + ANSI_RESET);
//        System.out.println(logArray);

        //array absolute value
//        INDArray absArray = Transforms.abs(logArray);
//        System.out.println(BLACK_BOLD + "\nArray absolute transform" + ANSI_RESET);
//        System.out.println(BLUE_BOLD + "Transforms.abs(myArray)" + ANSI_RESET);
//        System.out.println(absArray);

        //Round up array
//        INDArray roundUpArray = Transforms.ceil(absArray);
//        System.out.println(BLACK_BOLD + "\nRound up array" + ANSI_RESET);
//        System.out.println(BLUE_BOLD + "Transforms.ceil(absArray)" + ANSI_RESET);
//        System.out.println(roundUpArray);

        //Array sigmoid function
//        INDArray sigmoidArray = Transforms.sigmoid(myArray);
//        System.out.println(BLACK_BOLD + "\nArray sigmoid function" + ANSI_RESET);
//        System.out.println(BLUE_BOLD + "Transforms.sigmoid(myArray)" + ANSI_RESET);
//        System.out.println(sigmoidArray);

        //Try out more Transform function
    }
}
