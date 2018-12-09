package av08;

import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class Day8Test {


    @Test
    public void part1() {
        String input = "2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2";

        Day8 d = new Day8(input);
        assertThat(d.part1(), is(138));
    }
}