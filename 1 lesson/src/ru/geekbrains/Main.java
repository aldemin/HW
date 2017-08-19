package ru.geekbrains;

import java.util.ArrayList;

public class Main {

    public <T> T[] swap(T[] array, int firstIndex, int secondIndex) {
        T buffer = array[secondIndex];
        array[secondIndex] = array[firstIndex];
        array[firstIndex] = buffer;
        return array;
    }

    public <T> ArrayList<T> toList(T[] array) {
        ArrayList<T> list = new ArrayList<>();
        for (int i = 0, cnt = array.length; i < cnt; i++) {
            list.add(array[i]);
        }
        return list;
    }

    public interface Fruit {

        float getWeight();

    }

    public class Apple implements Fruit {
        private final float weight = 1.0f;

        @Override
        public float getWeight() {
            return this.weight;
        }
    }

    public class Orange implements Fruit {
        private final float weight = 1.5f;

        @Override
        public float getWeight() {
            return this.weight;
        }
    }

    public static class Box<T extends Fruit> {
        private final ArrayList<T> fruits = new ArrayList<>();

        public void add(T fruit) {
            this.fruits.add(fruit);
        }

        public float getWeight() {
            final int count = this.fruits.size();
            if (count != 0) {
                return this.fruits.get(0).getWeight() * count;
            }
            return 0f;
        }

        public boolean compare(Box box) {
            return Math.abs(this.getWeight() - this.getWeight()) < 0.0001f;
        }

        public void intersperse(Box<T> box) {
            for (int i = 0, cnt = fruits.size(); i < cnt; i++) {
                box.add(this.fruits.get(i));
            }
            this.fruits.removeAll(this.fruits);
        }
    }
}