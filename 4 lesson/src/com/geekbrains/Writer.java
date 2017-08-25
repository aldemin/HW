package com.geekbrains;

import sun.awt.windows.ThemeReader;

import java.io.*;

public class Writer {
    private static final String FILE_NAME = "exam.ple";

    public static void main(String[] args) {
        File file = new File(FILE_NAME);

        if (!file.exists()) try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {

            Thread firstThread = new Thread(() ->{
                for (int i = 0; i < 10; i++) {
                    try {
                        writer.write("First Thread " + i);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            Thread secondThread = new Thread(() ->{
                for (int i = 0; i < 10; i++) {
                    try {
                        writer.write("Second Thread " + i);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            Thread thirdThread = new Thread(() ->{
                for (int i = 0; i < 10; i++) {
                    try {
                        writer.write("Third Thread " + i);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            firstThread.start();
            secondThread.start();
            thirdThread.start();

            try {
                firstThread.join();
                secondThread.join();
                thirdThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
