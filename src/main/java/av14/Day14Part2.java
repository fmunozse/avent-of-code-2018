package av14;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Day14Part2 {

    public int part2(int input) {

        int elf_1 = 0;
        int elf_2 = 1;
        StringBuilder scoreboard = new StringBuilder();
        scoreboard.append(3);
        scoreboard.append(7);
        int i= 1;
        printScoreboard(i,elf_1,elf_2,scoreboard);

        while (scoreboard.length() < (10 + input) ) {
            int recipietElf1 = Integer.valueOf(Character.toString(scoreboard.charAt(elf_1)));
            int recipietElf2 = Integer.valueOf(Character.toString(scoreboard.charAt(elf_2)));

            //next recipe
            int total = recipietElf1 + recipietElf2;
            if (total >= 10) {
                scoreboard.append(Integer.valueOf(Character.toString(String.valueOf(total).charAt(0))));
                scoreboard.append(Integer.valueOf(Character.toString(String.valueOf(total).charAt(1))));
            } else {
                scoreboard.append(total);
            }

            //movent
            elf_1 = (elf_1 + recipietElf1 + 1) % scoreboard.length();
            elf_2 = (elf_2 + recipietElf2 + 1) % scoreboard.length();

            i++;
            //printScoreboard(i,elf_1,elf_2,scoreboard);
        }

        return scoreboard.indexOf("540561");
    }

    private void printScoreboard(int iteration, int elf_1, int elf_2, StringBuilder scoreboard) {
        System.out.println(iteration + ": " + scoreboard);
    }

    public static void main(String[] args) {
        Day14Part2 day14Part2 = new Day14Part2();
        System.out.println(day14Part2.part2(30_000_000));
    }
}
