package av14;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Day14Part1 {

    public String part1(int input) {

        int elf_1 = 0;
        int elf_2 = 1;
        List<Integer> scoreboard = new LinkedList<>();
        scoreboard.add(3);
        scoreboard.add(7);
        int i= 1;
        printScoreboard(i,elf_1,elf_2,scoreboard);

        while (scoreboard.size() < (10 + input) ) {
            int recipietElf1 = scoreboard.get(elf_1);
            int recipietElf2 = scoreboard.get(elf_2);

            //next recipe
            int total = recipietElf1 + recipietElf2;
            if (total >= 10) {
                scoreboard.add(Integer.valueOf(Character.toString(String.valueOf(total).charAt(0))));
                scoreboard.add(Integer.valueOf(Character.toString(String.valueOf(total).charAt(1))));
            } else {
                scoreboard.add(total);
            }

            //movent
            elf_1 = (elf_1 + recipietElf1 + 1) % scoreboard.size();
            elf_2 = (elf_2 + recipietElf2 + 1) % scoreboard.size();

            i++;
            //printScoreboard(i,elf_1,elf_2,scoreboard);
        }

        StringBuilder s = new StringBuilder(scoreboard.stream().map(String::valueOf).collect(Collectors.joining()));
        return s.substring(input);
    }

    private void printScoreboard(int iteration, int elf_1, int elf_2, List<Integer> scoreboard) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < scoreboard.size(); i++) {
            Integer score = scoreboard.get(i);
            if (elf_1 == i) {
                sb.append("(").append(score).append(")");
            } else if (elf_2 == i) {
                sb.append("[").append(score).append("]");
            } else {
                sb.append(" ").append(score).append(" ");
            }
        }
        System.out.println(iteration + ": " + sb);
    }

    public static void main(String[] args) {
        Day14Part1 day14Part1 = new Day14Part1();
        System.out.println(day14Part1.part1(9));
        System.out.println(day14Part1.part1(5));
        System.out.println(day14Part1.part1(18));
        System.out.println(day14Part1.part1(2018));
        System.out.println(day14Part1.part1(540561));
    }
}
