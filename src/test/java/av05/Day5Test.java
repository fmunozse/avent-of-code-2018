package av05;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class Day5Test {

    @Test
    public void testPart1() {
        Day5 d = new Day5();
        assertThat(d.day5Part1("dabAcCaCBAcCcaDA"), is(10));
    }

    @Test
    public void testPart2() {
        Day5 d = new Day5();
        assertThat(d.day5Part2("dabAcCaCBAcCcaDA"), is(4));
    }

}