package com.GoldenMine;

import java.util.Arrays;
import java.util.Collections;
import org.junit.Test;

public class ListTest {
    @Test
    public void test() {
        Arrays.asList("A").remove(0);
    }

    @Test
    public void test2() {
        Collections.singletonList("A").remove(0);
    }

    @Test
    public void test3() {
        Arrays.asList("A").add("B");
    }

    @Test
    public void test4() {
        Collections.singletonList("A").add("B");
    }
}
