package com.geekbrains;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {
        DB db = new DB();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String comm = null;

        db.connect();
        db.createTable();
        db.fillTable();

        while (true) {
            System.out.println("/cost prod_name - show product cost\n" +
                    "/changeCost prod_name new_prise - change cost\n" +
                    "/show first_cost second_cost - show range\n" +
                    "/exit - exit");

            try {
                comm = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            String[] tokens = comm.split(" ");
            if (tokens.length == 2 && tokens[0].equals("/cost")) {
                System.out.println(db.readPosByTitle(tokens[1]));
            } else if (tokens.length == 3 && tokens[0].equals("/changeCost")) {
                db.writeNewCost(tokens[1], Integer.parseInt(tokens[2]));
            } else if (tokens.length == 3 && tokens[0].equals("/show")) {
                System.out.println(db.readPosByCost(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2])));
            } else if (tokens.length == 1 && tokens[0].equals("/exit")) {
                break;
            } else {
                System.out.println("Try again");
            }
        }

        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        db.disconnect();
    }

}
