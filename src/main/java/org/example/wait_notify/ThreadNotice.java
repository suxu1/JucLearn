package org.example.wait_notify;

public class ThreadNotice {


    public static void main(String[] args) {
        Incr_Decl incrDecl = new Incr_Decl();
        new Thread(()->{
            for (int i = 0; i < 40; i++) {
                try {
                    incrDecl.incr();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        },"tA").start();
        new Thread(()->{
            for (int i = 0; i < 40; i++) {
                try {
                    incrDecl.decl();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        },"tB").start();

        new Thread(()->{
            for (int i = 0; i < 40; i++) {
                try {
                    incrDecl.incr();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        },"tC").start();
        new Thread(()->{
            for (int i = 0; i < 40; i++) {
                try {
                    incrDecl.decl();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        },"tD").start();
    }

}
    class Incr_Decl{
    int number = 0;
    public synchronized void incr() throws InterruptedException {
        while(number==1){ //必须用循环判断, 不然会唤醒后直接执行造成数值错误
            wait();
        }
        number++;
        System.out.println(Thread.currentThread().getName()+":::"+number);

        notifyAll();
    }
    public synchronized void decl() throws InterruptedException {
        while(number==0){
            wait();
        }
        number--;
        System.out.println(Thread.currentThread().getName()+":::"+number);
        notifyAll();
    }
}
