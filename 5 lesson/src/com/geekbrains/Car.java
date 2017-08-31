package com.geekbrains;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicBoolean;

public class Car implements Runnable {
    private static AtomicBoolean isWin = new AtomicBoolean(true);
    private static int CARS_COUNT;
    private final CyclicBarrier cbStartRace;
    private final CyclicBarrier cbFinishRace;
    static {
        CARS_COUNT = 0;
    }
    private Race race;
    private int speed;
    private String name;
    public String getName() {
        return name;
    }
    public int getSpeed() {
        return speed;
    }

    public Car(Race race, int speed, CyclicBarrier cbStartRace, CyclicBarrier cbFinishRace) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
        this.cbStartRace = cbStartRace;
        this.cbFinishRace = cbFinishRace;
    }
    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int)(Math.random() * 800));
            System.out.println(this.name + " готов");
            cbStartRace.await();

            for (int i = 0; i < race.getStages().size(); i++) {
                race.getStages().get(i).go(this);
            }

            if (isWin.get()) {
                System.out.println(this.name + "!!!!!!WIN!!!!!");
                isWin.set(false);
            }
            cbFinishRace.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
