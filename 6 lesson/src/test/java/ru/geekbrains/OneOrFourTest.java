package ru.geekbrains;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(value = Parameterized.class)
public class OneOrFourTest {
    private int[] arr;
    private boolean answer;
    private static OneOrFour oneOrFour;

    public OneOrFourTest(boolean answer, int[] arr) {
        this.arr = arr;
        this.answer = answer;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data(){
        return Arrays.asList(
                new Object[][]{
                        {false,
                                new int[]{1,2,3,4,5,6,7,8,9,10}},
                        {false,
                                new int[]{1,1,1,1,1,1,1,1,1,1,1}},
                        {false,
                                new int[]{1,2,1,1,4}},
                        {true,
                                new int[]{1,1,1,4,4,1,1,1,1}},
                        {true,
                                new int[]{1,1,1,1,4,4,4,4,4,4,1}},
                        {false,
                                new int[]{4,4,4,5,4,1,1,1,4}},
                        {true,
                                new int[]{4,4,1,4,4}},
                        {true,
                                new int[]{1,4,1,4,1,4,1}},
                        {true,
                                new int[]{4,1,4,1,4,1,4,1,4,1}}
                }
        );
    };

    @BeforeClass
    public static void setUp() {
        oneOrFour = new OneOrFour();
    }
    @Test
    public void oneOrFour() {
        assertSame(answer, oneOrFour.oneOrFour(arr));
    }

}