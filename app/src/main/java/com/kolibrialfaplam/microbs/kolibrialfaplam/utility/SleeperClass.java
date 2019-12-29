package com.kolibrialfaplam.microbs.kolibrialfaplam.utility;

public class SleeperClass {

    private SleeperClass() {

    }

    public static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
         Utilities.writeErrorToFile(e);
        }
    }
}
