package av09;

import java.util.ArrayDeque;
import java.util.Arrays;

public class Day9 {

    public int part1(int numPlayers, int end) {
        CircleDeque<Integer> circle = new CircleDeque<>();
        circle.addFirst(0);
        int[] points = new int[numPlayers];
        for (int i = 1; i <= end; i++) {
            if (i % 23 == 0) {
                circle.rotate(-7);
                int pointsCalculation = i + circle.pop();
                points[i % numPlayers] += pointsCalculation;

            } else {
                circle.rotate(2);
                circle.addLast(i);
            }
        }
        return Arrays.stream(points).max().getAsInt();
    }


    public long part2(int numPlayers, int end) {
        CircleDeque<Integer> circle = new CircleDeque<>();
        circle.addFirst(0);
        long[] points = new long[numPlayers];
        for (int i = 1; i <= end; i++) {
            if (i % 23 == 0) {
                circle.rotate(-7);
                int pointsCalculation = i + circle.pop();
                points[i % numPlayers] += pointsCalculation;

            } else {
                circle.rotate(2);
                circle.addLast(i);
            }
        }
        return Arrays.stream(points).max().getAsLong();
    }


    public static void main(String[] args) {
        Day9 d = new Day9();
        System.out.println(d.part1(424, 71144));
        System.out.println(d.part2(424, 71144 * 100));
    }


    class CircleDeque<T> extends ArrayDeque<T> {
        void rotate(int num) {
            if (num == 0) return;
            if (num > 0) {
                for (int i = 0; i < num; i++) {
                    T t = this.removeLast();
                    this.addFirst(t);
                }
            } else {
                for (int i = 0; i < Math.abs(num) - 1; i++) {
                    T t = this.remove();
                    this.addLast(t);
                }
            }

        }
    }
}
