package av02;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static av02.Day02.calculateChecksum;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class Day02Test {


    @Test
    public void test1() {
        List<String> list = asList("abcdef",
                "bababc",
                "abbcde",
                "abcccd",
                "aabcdd",
                "abcdee",
                "ababab");
        assertThat(calculateChecksum(list), is(12L));
    }


}