package av01;

import util.FileIO;

import java.util.function.Function;

public class Day01 {

    public static int calculateFrequency (String pathFile) {

        Function<String, Integer> sum = Integer::valueOf;
        int result = FileIO.performIntActionOnLine(pathFile, sum);
        return result;

    }


    public static void main(String[] args) {
        System.out.println(calculateFrequency(("src/main/java/av01/example.txt")));
        System.out.println(calculateFrequency(("src/main/java/av01/input.txt")));

    }


}
