package av05;

import util.FileIO;

import java.util.Arrays;

public class Day5 {



    public int day5Part2 (String polymer) {

        String[][] patterns = new String[26][2];
        for (int i = 0; i < patterns.length; i++) {
            patterns[i][0] = Character.toString((char)(i + 'a'));
            patterns[i][1] = Character.toString((char)(i + 'A'));
            System.out.println(patterns[i][0] + " - " + patterns[i][1]);
        }

        int[] reduced = new int[26];
        for (int i = 0; i < patterns.length; i++) {
            String polymerReduced = polymer.replaceAll(patterns[i][0] + "|" + patterns[i][1], "");
            System.out.println(patterns[i][0] + " - " + patterns[i][1] + " ::: " + polymerReduced);
            reduced[i]= day5Part1(polymerReduced);
        }

        return Arrays.stream(reduced).min().getAsInt();
    }

        
    public int day5Part1(String polymer) {
        System.out.println(polymer + " <- Init");
        StringBuilder in = new StringBuilder();
        in.append(polymer);
        boolean removed = true;
        while (removed) {
            for (int i = 0; i < in.length() - 1; i++) {
                if ( (in.charAt(i) ^ in.charAt(i + 1)) == 32) {
                    in.delete(i, i + 2);
                    removed = true;
                    break;
                }
                removed = false;
            }
        }
        System.out.println(in.toString() + " <- end");
        return in.toString().length();
    }


    public static void main(String[] args) {
        Day5 day5 = new Day5();
        //System.out.println(day5.day5Part1(FileIO.getFileAsList("src/main/java/av05/input.txt").get(0)));
        System.out.println(day5.day5Part2(FileIO.getFileAsList("src/main/java/av05/input.txt").get(0)));
    }
}
