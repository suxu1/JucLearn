package org.example.wait_notify;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 通过标志位使多个线程顺序执行10次
 */
public class NoticeOneByOne {



    public static void main(String[] args) {
        NoticeByLockAndCondition notice = new NoticeByLockAndCondition();
        new Thread(()->{
                try {
                    notice.doJob();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

        },"A").start();
        new Thread(()->{
                try {
                    notice.doJob();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

        },"B").start();

        new Thread(()->{
                try {
                    notice.doJob();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

        },"C").start();
    }

}

class NoticeByLockAndCondition{
    private static Integer flag = 1;
    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();
    public void doJob() throws InterruptedException {
        String name = Thread.currentThread().getName();
        int count = 1;
        for (int i = 0; i < 10; i++) {
            lock.lock();
            try {
                //A线程先执行5次,B线程再执行10次,C最后执行15次 总共循环10次
                while (flag == 1 && name.equals("A")) {
                    for (int j = 0; j < 5; j++) {
                        System.out.println(name + " do job" + count++);
                    }
                    flag = 2;
                    count = 1;
                }
                while (flag == 2 && name.equals( "B")) {
                    for (int j = 0; j < 10; j++) {
                        System.out.println(name + " do job" + count++);
                    }
                    flag = 3;
                    count = 1;
                }
                while (flag == 3 && name.equals( "C")) {
                    for (int j = 0; j < 15; j++) {
                        System.out.println(name + " do job" + count++);
                    }
                    flag = 1;
                    count = 1;
                    System.out.println("======================第"+i+"次循环完毕");
                }

            }finally {
                lock.unlock();
            }
        }
    }
}
