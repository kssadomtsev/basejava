package com.mysite.webapp;

public class MainDeadlock {
    private static final String str1 = "Object1";
    private static final String str2 = "Object2";

    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (str1) {
                System.out.println("Thread 1 locked string: " + str1);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread 1 is waiting for lock string: " + str2);
                synchronized (str2) {
                    System.out.println("Thread 1 locked string: " + str1 + ", and string:" + str2);
                }
            }
        }).start();
        new Thread(() -> {
            synchronized (str2) {
                System.out.println("Thread 2 locked string: " + str2);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread 2 is waiting for lock string: " + str1);
                synchronized (str1) {
                    System.out.println("Thread 2 locked string: " + str2 + ", and string:" + str1);
                }
            }
        }).start();
    }
}
