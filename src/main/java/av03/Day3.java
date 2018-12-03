package av03;

import util.FileIO;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3 {

    public static int part1(List<String> l, int max) {
        String[][] table = new String[max][max];
        for (String command : l) {
            Pattern pattern = Pattern.compile("#(\\d+) @ (\\d+),(\\d+): (\\d+)x(\\d+)");
            Matcher matcher = pattern.matcher(command);
            matcher.find();
            String id = matcher.group(1);
            int leftMargin = Integer.valueOf(matcher.group(2));
            int topMargin = Integer.valueOf(matcher.group(3));
            int wide = Integer.valueOf(matcher.group(4));
            int tall = Integer.valueOf(matcher.group(5));

            for (int i = leftMargin; i < (leftMargin + wide); i++) {
                for (int j = topMargin; j < (topMargin + tall); j++) {
                    table[i][j] = table[i][j] == null ? id : "X";
                }
            }
        }

        printTable(table, max);

        int cnt = 0;
        for (int i = 0; i < max; i++) {
            for (int j = 0; j < max; j++) {
                cnt += table[i][j] != "X" ? 0 : 1;
            }
        }

        return cnt;
    }


    public static String part2(List<String> l, int max) {
        String[][] table = new String[max][max];
        Set<String> elements = new HashSet<>();

        for (String command : l) {
            Pattern pattern = Pattern.compile("#(\\d+) @ (\\d+),(\\d+): (\\d+)x(\\d+)");
            Matcher matcher = pattern.matcher(command);
            matcher.find();
            String id = matcher.group(1);
            int leftMargin = Integer.valueOf(matcher.group(2));
            int topMargin = Integer.valueOf(matcher.group(3));
            int wide = Integer.valueOf(matcher.group(4));
            int tall = Integer.valueOf(matcher.group(5));

            boolean collision = false;
            for (int i = leftMargin; i < (leftMargin + wide); i++) {
                for (int j = topMargin; j < (topMargin + tall); j++) {

                    if (table[i][j] == null) {
                        table[i][j] = id;

                    } else {
                        elements.remove(table[i][j]);
                        table[i][j] = "X";
                        collision = true;
                    }
                }
            }

            if (!collision) {
                elements.add(id);
            }
        }

        printTable(table, max);
        System.out.println(elements);
        return elements.iterator().next();
    }


    private static void printTable(String[][] table, int max) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < max; i++) {
            for (int j = 0; j < max; j++) {
                sb.append(table[i][j] == null ? " . " : " " + table[i][j] + " ");
            }
            sb.append("\n");
        }

        System.out.println(sb.toString());
    }


    public static void main(String[] args) {
        //System.out.println(part1(FileIO.getFileAsList("src/main/java/av03/inputPart1.txt"),1000));
        System.out.println(part2(FileIO.getFileAsList("src/main/java/av03/inputPart2.txt"), 1000));
    }
}
