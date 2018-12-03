package av02;

import util.FileIO;

import java.util.List;

public class Day02Part2 {

    public static String calculateSimilarWord(List<String> list) {

        String result = null;

        for (int i = 0; i < list.size(); i++) {
            String box = list.get(i);

            for (int j = i +1 ; j < list.size(); j++) {
                String word = checkEqualsBox(box, list.get(j));
                if (word != null)
                    return word;
            }
        }
        return result;
    }

    private static String checkEqualsBox (String box1, String box2) {
        int countDifferent = 0;
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < box1.length(); i++) {

            if (box1.charAt(i) != box2.charAt(i)) {
                countDifferent++;
                if (countDifferent > 1) {
                    return null;
                }
            } else {
                sb.append(box1.charAt(i));
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(calculateSimilarWord(FileIO.getFileAsList("src/main/java/av02/inputPart2.txt")));
    }

}
