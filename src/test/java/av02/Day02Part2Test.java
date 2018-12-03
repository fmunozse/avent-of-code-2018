package av02;

import org.junit.Test;

import java.util.List;

import static av02.Day02Part2.calculateSimilarWord;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Day02Part2Test {

    @Test
    public void test2() {
        List<String> list = asList("abcde",
                "fghij",
                "klmno",
                "pqrst",
                "fguij",
                "axcye",
                "wvxyz");

        assertThat(calculateSimilarWord(list), is("fgij"));

    }


}