package av01;

import org.junit.Test;

import java.util.List;

import static av01.Day01Part2.firstReaches;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Day01Part2Test {

    @Test
    public void test2() {
        List<String> l = asList("+3", "+3", "+4", "-2", "-4");
        assertThat(firstReaches(l), is(10));
    }

    @Test
    public void test3() {
        List<String> l = asList("-6", "+3", "+8", "+5", "-6");
        assertThat(firstReaches(l), is(5));
    }

    @Test
    public void test4() {
        List<String> l = asList("+7", "+7", "-2", "-7", "-4");
        assertThat(firstReaches(l), is(14));
    }

}