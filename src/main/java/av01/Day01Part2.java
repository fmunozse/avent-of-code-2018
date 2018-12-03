package av01;

import util.FileIO;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

public class Day01Part2 {

    public static List<Integer> parse(List<String> list) {
        return list.stream()
                .mapToInt(Integer::parseInt)
                .boxed()
                .collect(toList());
    }

    public static int firstReaches(List<String> list) {
        List<Integer> intInput = parse(list) ;

        int f = 0;
        Set<Integer> seen = new HashSet<>();
        while (true) {
            for (Integer each : intInput) {
                f += each;
                if (!seen.add(f)) {
                    return f;
                }
            }
        }

    }

    public static void main(String[] args) {
        System.out.println(firstReaches(FileIO.getFileAsList("src/main/java/av01/inputPart2.txt")));
    }

}
