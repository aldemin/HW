package com.geekbrains;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class MyWriter {
    private final String filePath;
    private final int sizeOfPage;
    private RandomAccessFile raf = null;

    public MyWriter(String filePath, int sizeOfPage) {
        this.filePath = filePath;
        this.sizeOfPage = sizeOfPage;
    }

    public void readPage(int page) {
        int topOfPage = (page - 1) * this.sizeOfPage;
        RandomAccessFile raf = getRaf();
        try {
            raf.seek(topOfPage);
        } catch (IOException e) {
            System.out.println("Что-то пошло не так.");
            e.printStackTrace();
        }

        int c;
        for (int i = 0; i < this.sizeOfPage; i++) {
            try {
                if ((c = raf.read()) != -1) {
                    System.out.print((char) c);
                } else {
                    System.out.println("Конец файла или такой страницы не существует.");
                    return;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private RandomAccessFile getRaf() {
        if (this.raf == null) {
            try {
                return new RandomAccessFile(filePath, "r");
            } catch (FileNotFoundException e) {
                System.out.printf(String.format("По пути \"%s\" нет нужного файла", filePath));
            }
        }
        return this.raf;
    }
}
