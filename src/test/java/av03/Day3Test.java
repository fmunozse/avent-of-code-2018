package av03;

import org.junit.Test;

import java.util.List;

import static av03.Day3.part1;
import static av03.Day3.part2;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class Day3Test {

    @Test
    public void test1() {
        List<String> l = asList("#1 @ 1,3: 4x4",
                "#2 @ 3,1: 4x4",
                "#3 @ 5,5: 2x2");

        assertThat(part1(l, 10), is(4));
    }

    @Test
    public void test2() {
        List<String> l = asList("#1 @ 1,3: 4x4",
                "#2 @ 3,1: 4x4",
                "#3 @ 5,5: 2x2");

        assertThat(part2(l, 10), is("3"));
    }
}