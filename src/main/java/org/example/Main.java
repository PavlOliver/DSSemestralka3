package org.example;

import gui.MainFrame;
import simulation.MySimulation;

public class Main {
    public static void main(String[] args) {
//        new MainFrame();
//
        MySimulation ms = new MySimulation();

//        for (int a = 1; a < 5; a++) {
//            for (int b = 1; b < 5; b++) {
//                for (int c = 1; c < 15; c++) {
//                    for (int wp = 3; wp < 60; wp++) {
//                        System.out.println("------------------------");
//                        System.out.println("a: " + a);
//                        System.out.println("b: " + b);
//                        System.out.println("c: " + c);
//                        System.out.println("wp: " + wp);
//                        ms.settings(a, b, c, wp);
//                        ms.simulate(500, (double) 249 * 8 * 60 * 60 * 1000);
//                        System.out.println("------------------------");
//                    }
//                }
//            }
//        }
        ms.settings(6, 6, 38, 52);
        ms.simulate(1000, (double) 249 * 8 * 60 * 60 * 1000);
    }
}