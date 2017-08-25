package com.geekbrains;

import java.util.ArrayList;

public class ABC {
    private final ArrayList<Character> LETTERS = new ArrayList<Character>(3){{
            add('A');
            add('B');
            add('C');
    }};
    private int currentPosition = 0;

    public void printLetter(char letter) {
        new Thread(()->{
            synchronized (this) {
                for (int i = 0; i < 5; i++) {
                    while (this.LETTERS.get(currentPosition) != letter) {
                        try {
                            this.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println(letter);
                    if (++currentPosition == this.LETTERS.size()) {
                        currentPosition = 0;
                    }
                    this.notifyAll();
                }
            }
        }).start();
    }

    public static void main(String[] args) {
        ABC abc = new ABC();
        abc.printLetter('A');
        abc.printLetter('B');
        abc.printLetter('C');
    }

}
