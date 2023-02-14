package org.example.pool;

import java.util.concurrent.*;

/**
 * 线程池的三种方法
 */
public class ThreadPoolDemo {
    public static void main(String[] args) {
        //一池N线程
//        ExecutorService threadPoolExecutor = Executors.newFixedThreadPool(5);
        //一池一线程
//        ExecutorService threadPoolExecutor = Executors.newSingleThreadExecutor();
        //可扩展线程池
//        ExecutorService threadPoolExecutor = Executors.newCachedThreadPool();
        //这三种方式都是通过调用ThreadPoolExecutor构造方法传入不同参数获得的
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                2,
                5,
                2L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        );
        try {
            for (int i = 1; i <= 10; i++) {
                threadPoolExecutor.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "  在执行请求");
                });
            }
        } finally {
            threadPoolExecutor.shutdown();
        }

    }
}
