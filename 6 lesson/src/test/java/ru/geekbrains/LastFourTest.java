package ru.geekbrains;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(value = Parameterized.class)
public class LastFourTest {
    private static LastFour lastFour;
    private int[] expectedArray;
    private int[] originalArray;

    public LastFourTest(int[] expectedArray, int[] originalArray) {
        this.expectedArray = expectedArray;
        this.originalArray = originalArray;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(
                new Object[][] {
                        {new int[]{5,6,7,8,9,10},
                                new int[]{1,2,3,4,5,6,7,8,9,10}},
                        {new int[]{1},
                                new int[]{4,4,4,6,7,8,4,1}},
                        {new int[]{1,1,1,1},
                                new int[]{4,1,1,1,1}},
                        {new int[]{},
                                new int[]{4,4,4,4,4}},
                        {new int[]{6},
                                new int[]{4,5,4,6,4,6,4,6,4,6}},
                }
        );
    };

    @BeforeClass
    public static void setUp() {
        lastFour = new LastFour();
    }

    @Test
    public void getArr(){
        int[] actualArray = lastFour.getArr(originalArray);
        assertArrayEquals(expectedArray, actualArray);
    }

    @Test(expected = RuntimeException.class)
    public void getArrException() {
        lastFour.getArr(expectedArray);
    }

}