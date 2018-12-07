package av06;

import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Day6Part2Test {

    Day6Part2 d = new Day6Part2();

    @Test
    public void part2() {
        List l = asList("1, 1",
                "1, 6",
                "8, 3",
                "3, 4",
                "5, 5",
                "8, 9");

        assertThat(d.part2(l, 10, 32), is(16));
    }


}