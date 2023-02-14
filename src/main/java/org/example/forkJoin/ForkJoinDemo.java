package org.example.forkJoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class ForkJoinDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MyTask task = new MyTask(0,100);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Integer> submit = forkJoinPool.submit(task);
        System.out.println(submit.get());
        forkJoinPool.shutdown();
    }
}

class MyTask extends RecursiveTask<Integer> {
    private static final int VALUE = 10;
    private int begin;
    private int end;

    Integer result = 0;
    public MyTask(int begin,int end) {
        this.begin = begin;
        this.end = end;
    }

    /**
     * 拆分规则
     * @return
     */
    @Override
    protected Integer compute() {

        if(end-begin <= VALUE){
            for (int i = begin; i <= end; i++) {
                result += i;
            }
        }else {
            int middle = (begin+end) / 2;
            MyTask task = new MyTask(begin, middle);
            MyTask task1 = new MyTask(middle + 1, end);
            task.fork();
            task1.fork();
            result = task.join()+task1.join();
        }
        return result;
    }
}
