package org.example.callable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class CallAbleTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> futureTask = new FutureTask(()->{
           return 100;
        });
        FutureTask<Integer> futureTask2 = new FutureTask(()->{
            System.out.println("futureTask2 is running");
            TimeUnit.SECONDS.sleep(1);
            return 200;
        });
        new Thread(futureTask,"ft").start();
        new Thread(futureTask2,"ft2").start();
        System.out.println(futureTask2.get());
        while (!futureTask.isDone()){
            System.out.println("wait");
        }
        System.out.println(futureTask.get());
    }
}
