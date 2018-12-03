package av02;

import util.FileIO;

import java.util.*;

public class Day02 {

    public static long calculateChecksum(List<String> lines) {

        //nºCharacter in word  / counts of words that happen
        Map<Integer, Integer> occurs = new LinkedHashMap<>();


        lines.stream().forEach(s -> {
            Set<Integer> countsChars = countCharacters(s);

            for (Integer numChars : countsChars) {
                if (occurs.containsKey(numChars)) {
                    occurs.put(numChars, (occurs.get(numChars) + 1));
                } else {
                    occurs.put(numChars, 1);
                }
            }
        });

        System.out.println("Map occurs: " + occurs);
        long checksum = 1;
        for (Integer occur:  occurs.values()) {
            checksum *= occur.intValue();
        }

        return checksum;
    }


    private static Set<Integer> countCharacters(String word) {
        Map<Character, Integer> map = new LinkedHashMap<>();
        for (int i = 0; i < word.length(); i++) {
            Character c = word.charAt(i);

            //check if has been processed
            if (map.containsKey(c)) {
                continue;
            }

            int count = 1;
            for (int j = i + 1; j < word.length(); j++) {
                if (c.equals(word.charAt(j))) {
                    count++;
                }
            }

            if (count > 1) {
                map.put(c, count);
            }
        }


        Set<Integer> result = new HashSet<>();
        map.entrySet().stream()
                .forEach(entry -> {
                    result.add(entry.getValue());
                } );

        System.out.println("counts Chars for " + word  + ": " + map + "- Reduced to: " + result);

        return result;
    }


    public static void main(String[] args) {
        System.out.println(calculateChecksum(FileIO.getFileAsList("src/main/java/av02/input.txt")));
    }

}
