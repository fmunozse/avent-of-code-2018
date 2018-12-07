package av06;

import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Day6Test {

    Day6 d = new Day6();

    @Test
    public void part1() {
        List l = asList("1, 1",
                "1, 6",
                "8, 3",
                "3, 4",
                "5, 5",
                "8, 9");

        assertThat(d.part1(l, 10), is(17));
    }


}