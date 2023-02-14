package org.example.ticketDemo;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SaleTicketByLock {
    public static void main(String[] args) {
        Ticket ticket = new Ticket();
        new Thread(()->{
            for (int i = 0; i < 100; i++) {
                ticket.sale();
            }
        },"T1").start();
        new Thread(()->{
            for (int i = 0; i < 100; i++) {
                ticket.sale();
            }
        },"T2").start();
        new Thread(()->{
            for (int i = 0; i < 100; i++) {
                ticket.sale();
            }
        },"T3").start();
    }
  static  class Ticket{
        private volatile Integer number = 100;
        Lock lock = new ReentrantLock();
        public void sale(){

            try {
                lock.lock();
                if (number > 0) {
                    System.out.println(Thread.currentThread().getName() + "  卖出了第" + number + " 张票");
                    number--;
                }
            }
            finally{
                lock.unlock();
            }
        }
    }
}
