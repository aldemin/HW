package com.geekbrains;

public class MFP {
    private  final Object printMonitor = new Object();
    private  final Object scanMonitor = new Object();

    public void print(int countOfDocs) {
        new Thread(() ->{
            synchronized (printMonitor) {
                for (int i = 0; i < countOfDocs; i++) {
                    try {
                        System.out.println("Печать...");
                        Thread.sleep(500);
                        System.out.println(String.format("Напечатано %s страниц/цы.", i + 1));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void scan(int countOfDocs) {
        new Thread(() ->{
            synchronized (scanMonitor) {
                for (int i = 0; i < countOfDocs; i++) {
                    {
                        try {
                            System.out.println("Сканирование...");
                            Thread.sleep(500);
                            System.out.println(String.format("Отсканированно %s страниц/цы.", i + 1));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }

    public static void main(String[] args) {
        MFP mfp = new MFP();

        mfp.scan(20);
        mfp.print(10);
    }
}
