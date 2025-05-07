package org.example;

import gui.MainFrame;
import simulation.MySimulation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileReader;

public class Main {
    public static void main(String[] args) {
        new MainFrame();
//
//        MySimulation ms = new MySimulation();
//
//        String line;
//        try (BufferedReader br = new BufferedReader(new FileReader("load.csv"))) {
//            while ((line = br.readLine()) != null) {
//                // Rozdeľ riadok podľa stredníka
//                String[] parts = line.split(";");
//                if (parts.length != 4) {
//                    System.err.println("Preskočím neplatný riadok: " + line);
//                    continue;
//                }
//
//                try {
//                    int a = Integer.parseInt(parts[0].trim());
//                    int b = Integer.parseInt(parts[1].trim());
//                    int c = Integer.parseInt(parts[2].trim());
//                    int wp = Integer.parseInt(parts[3].trim());
//
//                    System.out.printf("Načítané hodnoty: %d, %d, %d, %d%n", a, b, c, wp);
//                    ms.settings(a, b, c, wp);
//                    ms.simulate(1000, (double) 249 * 8 * 60 * 60 * 1000);
//
//                } catch (NumberFormatException e) {
//                    System.err.println("Neplatné číslo v riadku: " + line);
//                }
//            }
//        } catch (IOException e) {
//            System.err.println("Chyba pri čítaní súboru: " + e.getMessage());
//        }
    }
}