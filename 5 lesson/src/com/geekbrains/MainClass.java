package com.geekbrains;

import java.util.concurrent.CyclicBarrier;

public class MainClass {
    public static final int CARS_COUNT = 4;
    private static final CyclicBarrier cbStartRace = new CyclicBarrier(CARS_COUNT, ()->{
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
    });
    private static final CyclicBarrier cbFinishRace = new CyclicBarrier(CARS_COUNT, ()->{
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
    });

    public static void main(String[] args) {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(new Road(60), new Tunnel(CARS_COUNT), new Road(40));
        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10), cbStartRace, cbFinishRace);
        }
        for (int i = 0; i < cars.length; i++) {
            new Thread(cars[i]).start();
        }
    }
}

