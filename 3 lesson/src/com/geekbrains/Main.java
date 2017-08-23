package com.geekbrains;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class Main {

    private static final int NUM_OF_FILES = 5;
    private static final String FILE_EXT = ".txt";
    private static final String[] namesOfFiles = new String[NUM_OF_FILES];
    private static final String largeFileName = "5.txt";
    private static final String[] poem = {
            "Night, square, apothecary, lantern,\n" +
                    "Its meaningless and pallid light.\n",
            "Return a half a lifetime after - \n" +
                    "All will remain. A scapeless rite.\n",
            "Then die, then have a new beginning,\n" +
                    "And all will turn the same as ere:\n",
            "Night, rippled water's frigid grinning,\n" +
                    "Apothecary, lantern, square.\n",
            "Alexander Blok, October 10, 1912\n"
    };


    public static void main(String[] args) {
        createFiles();
        fillFiles(poem);

        readFile(namesOfFiles[0]);

        readFile(namesOfFiles);

        createAndFillLargeFile(largeFileName);

        new MyWriter("5.txt", 1800).readPage(3);
    }

    private static void readFile(String fileName) {
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(fileName)) ) {
            byte[] byteArray = new byte[in.available()];
            in.read(byteArray);
            System.out.println();
            for (byte b : byteArray) {
                System.out.print((char) b);
            }
        } catch (FileNotFoundException e) {
            System.out.println(String.format("Файл %s не найден.", fileName));
        } catch (IOException e) {
            System.out.println("Что-то пошло не так");
            e.printStackTrace();
        }
    }

    private static void readFile(String[] namesOfFiles) {
        ArrayList<FileInputStream> fileReaders = new ArrayList<>();
        for (int i = 0; i < namesOfFiles.length; i++) {
            try {
                fileReaders.add(new FileInputStream(namesOfFiles[i]));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        try(SequenceInputStream streams = new SequenceInputStream(Collections.enumeration(fileReaders))) {
            int b;
            while ((b = streams.read()) != -1) {
                System.out.print((char) b);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0, n = fileReaders.size(); i < n; i++) {
            try {
                fileReaders.get(i).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void createFiles() {
        File file = null;
        String fileName = null;
        try {
            for (int i = 0; i < NUM_OF_FILES; i++) {
                fileName = i + FILE_EXT;
                file = new File(fileName);
                if (!file.exists()) {
                    file.createNewFile();
                    System.out.println(String.format("Файл %s успешно создан.", fileName));
                } else {
                    System.out.println(String.format("Файл %s уже существует.", fileName));
                }
                namesOfFiles[i] = fileName;
            }
        } catch (IOException e) {
            System.out.println(String.format("Не удалось создать файл с именем %s.", fileName));
        }
    }

    private static void createAndFillLargeFile(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String str = "1234567891sfdgjhsoidvuhsviuh34fip9hsd9vy340f9jrg";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))){
            for (int i = 0; i < 1_000_000; i++) {
                writer.write(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void fillFiles(String[] values) {
        if (values.length != NUM_OF_FILES) {
            System.out.println("Неподходящий массив.");
            return;
        }
        String fileName = null;
        char[] charArray = null;
        for (int i = 0; i < NUM_OF_FILES; i++) {
            fileName = i + FILE_EXT;
            charArray = values[i].toCharArray();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                writer.write(charArray);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
