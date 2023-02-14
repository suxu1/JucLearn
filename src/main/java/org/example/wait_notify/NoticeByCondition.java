package org.example.wait_notify;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NoticeByCondition {
    public static void main(String[] args) {
        LoopNotice notice = new LoopNotice();
        new Thread(()->{
            try {
                notice.doJob5();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        },"A").start();
        new Thread(()->{
            try {
                notice.doJob10();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        },"B").start();

        new Thread(()->{
            try {
                notice.doJob15();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        },"C").start();
    }
}

class  LoopNotice{
    private volatile Integer flag = 1;
    Lock lock = new ReentrantLock();
    Condition c1 = lock.newCondition();
    Condition c2 = lock.newCondition();
    Condition c3 = lock.newCondition();

    public void doJob5() throws InterruptedException {
        lock.lock();
        try{
            while (flag!=1){
                c1.await();
            }
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName()+"::  do job: "+ i);
            }
            flag = 2;
            c2.signal();
        }finally {
            lock.unlock();
        }
    }

    public void doJob10() throws InterruptedException {
        lock.lock();
        try{
            while (flag!=2){
                c2.await();
            }
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName()+"::  do job: "+ i);
            }
            flag = 3;
            c3.signal();
        }finally {
            lock.unlock();
        }
    }

    public void doJob15() throws InterruptedException {
        lock.lock();
        try{
            while (flag!=3){
                c3.await();
            }
            for (int i = 0; i < 15; i++) {
                System.out.println(Thread.currentThread().getName()+"::  do job: "+ i);
            }
            flag = 1;
            c1.signal();
        }finally {
            lock.unlock();
        }
    }
}
