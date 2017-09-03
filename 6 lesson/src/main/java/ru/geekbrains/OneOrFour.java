package ru.geekbrains;

import java.util.Arrays;

public class OneOrFour {

    public boolean oneOrFour(int[] arr) {
        final int sizeArr = arr.length;

        for (int i = 0; i < sizeArr; i++) {
            if (arr[i] != 1 && arr[i] != 4) {
                return false;
            }
        }
        Arrays.sort(arr);
        return arr[0] != arr[sizeArr - 1];
    }
}
