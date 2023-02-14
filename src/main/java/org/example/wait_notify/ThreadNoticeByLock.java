package org.example.wait_notify;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadNoticeByLock {
    public static void main(String[] args) {
        NoticeByLock noticeByLock = new NoticeByLock();
        new Thread(()->{
            try {
                for (int i = 0; i < 20; i++) {
                    noticeByLock.incr();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },"tA").start();
        new Thread(()->{
            try {
                for (int i = 0; i < 20; i++) {
                    noticeByLock.decl();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },"tB").start();
        new Thread(()->{
            try {
                for (int i = 0; i < 20; i++) {
                    noticeByLock.incr();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },"tC").start();
        new Thread(()->{
            try {
                for (int i = 0; i < 20; i++) {
                    noticeByLock.decl();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },"tD").start();
    }
}

class NoticeByLock{
    private volatile Integer number=0;
    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();
    public void incr() throws InterruptedException {
        try{
            lock.lock();
            while (number==1){
                condition.await();
            }
            number++;
            System.out.println(Thread.currentThread().getName()+":::"+number);
            condition.signalAll();
        }finally {
            lock.unlock();

        }
    }
    public void decl() throws InterruptedException {
        try{
            lock.lock();
            while (number==0){
                condition.await();
            }
            number--;
            System.out.println(Thread.currentThread().getName()+":::"+number);
            condition.signalAll();
        }finally {
            lock.unlock();
        }
    }
}
