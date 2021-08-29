package juc.study._01sync_and_lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 这里的学习重点是使用 ReentrantLock
 */
public class SaleTicket2 {

    private static class Ticket {
        //private final ReentrantLock reentrantLock = new ReentrantLock(true);
        private final ReentrantLock reentrantLock = new ReentrantLock();

        private int ticketNumber = 10;

        private static int expired = 0;

        private static int effect = 0;

        public boolean sale() {
            reentrantLock.lock();
            if (ticketNumber <= 0) {
                expired++;
                reentrantLock.unlock();
                return false;
            }
            ticketNumber--;
            effect++;
            reentrantLock.unlock();
            return true;
        }

    }

    private static class Saler extends Thread {

        private final Ticket ticket;

        public Saler(final Ticket ticket, final String name) {
            this.ticket = ticket;
            setName(name);
        }

        @Override
        public void run() {
            while(ticket.sale()) {
                System.out.println("Saler [" + Thread.currentThread().getName() + "] Sale one ticket");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Ticket ticket = new Ticket();
        Saler saler1 = new Saler(ticket, "AA");
        Saler saler2 = new Saler(ticket, "BB");
        Saler saler3 = new Saler(ticket, "CC");

        saler1.start();
        saler2.start();
        saler3.start();

        saler1.join();
        saler2.join();
        saler3.join();

        System.out.println("Total:" + Ticket.effect + "," + Ticket.expired);
    }

}
