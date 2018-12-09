package av09;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Day9Test {

    @Test
    public void part1() {
        Day9 d = new Day9();
        assertThat(d.part1(10, 1618), is(8317));
        assertThat(d.part1(13, 7999), is(146373));
        assertThat(d.part1(17, 1104), is(2764));
        assertThat(d.part1(21, 6111), is(54718));
        assertThat(d.part1(30, 5807), is(37305));
    }
}