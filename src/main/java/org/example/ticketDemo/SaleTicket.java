package org.example.ticketDemo;


public class SaleTicket {
    public static void main(String[] args) {
        Ticket ticket = new Ticket();

        new Thread(()->{
            for (int i = 0; i < 100; i++) {
                ticket.sale ();
            }
        },"t1").start();
        new Thread(()->{
            for (int i = 0; i < 100; i++) {
                ticket.sale ();
            }
        },"t2").start();
        Thread t3 = new Thread(()->{
            for (int i = 0; i < 100; i++) {
                ticket.sale ();
            }
        },"t3");
        t3.start();
    }


}

/**
 * ticket 类
 */
class Ticket{
    public  Integer ticketNumber = 100;
    public synchronized  void sale(){

        if(ticketNumber>0){
            System.out.println("线程"+ Thread.currentThread().getName() +"卖出了第"+ticketNumber+"张票");
            ticketNumber --;
        }
    }
}
