package ru.geekbrains;

import java.util.Arrays;

public class LastFour {

    public int[] getArr(int[] arr) {
        final int sizeArr = arr.length;
        int lastIndex = 0;
        boolean isArrCorrect = false;
        int sizeOfNewArr;
        int[] newArray;

        for (int i = 0; i < sizeArr; i++) {
            if (arr[i] == 4) {
                lastIndex = i;
                isArrCorrect = true;
            }
        }

        if (!isArrCorrect) {
            throw new RuntimeException();
        }

        sizeOfNewArr = sizeArr - lastIndex - 1;
        newArray = new int[sizeOfNewArr];

        for (int i = lastIndex + 1, j = 0; i < sizeArr; i++, j++) {
            newArray[j] = arr[i];
        }

        return newArray;
    }
}
