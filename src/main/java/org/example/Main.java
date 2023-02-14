package org.example;

public class Main {
    public static void main(String[] args) {
        //创建线程
        Thread t = new Thread(() -> System.out.println("创建线程"));
        t.run();
        try {
            t.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}