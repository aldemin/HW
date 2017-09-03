package ru.geekbrains;

public class OneOrFour {

    public boolean oneOrFour(int[] arr) {
        final int sizeArr = arr.length;
        boolean oneExist = false;
        boolean fourExist = false;

        for (int i = 0; i < sizeArr; i++) {
            if (arr[i] != 1 && arr[i] != 4) {
                return false;
            }
            if (arr[i] == 1) {
                oneExist = true;
            } else {
                fourExist = true;
            }
        }
        if (oneExist && fourExist) {
            return true;
        } else {
            return false;
        }

    }
}
