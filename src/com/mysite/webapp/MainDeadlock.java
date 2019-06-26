package com.mysite.webapp;

public class MainDeadlock {
    private static final String str1 = "Object1";
    private static final String str2 = "Object2";

    public static void main(String[] args) {
        new Thread(() -> threadLock(str1, str2)).start();
        new Thread(() -> threadLock(str2, str1)).start();
    }

    private static void threadLock(String s1, String s2) {
        synchronized (s1) {
            System.out.println("Thread, " + Thread.currentThread().getName() + " locked string: " + s1);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread, " + Thread.currentThread().getName() + " is waiting for lock string: " + s2);
            synchronized (s2) {
                System.out.println("Thread, " + Thread.currentThread().getName() + " locked string: " + s1 + ", and string:" + s2);
            }
        }
    }
}
