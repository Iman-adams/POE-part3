package org.example;

 // Everything got moved to the ApplicationRunner and is handled over there

public class Main {
    public static void main(String[] args) {
        // Start the application
        org.example.ApplicationRunner appRunner = new org.example.ApplicationRunner();
        appRunner.run();
    }
}