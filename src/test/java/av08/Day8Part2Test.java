package av08;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Day8Part2Test {


    @Test
    public void part1() {
        String input = "2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2";

        Day8Part2 d = new Day8Part2(input);
        assertThat(d.part2(), is(66));
    }
}