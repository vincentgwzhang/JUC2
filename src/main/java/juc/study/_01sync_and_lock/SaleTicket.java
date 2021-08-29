package juc.study._01sync_and_lock;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 这里的学习重点是使用 synchronized
 */
public class SaleTicket {

    private static class Ticket {
        private int ticketNumber = 10;

        public synchronized boolean sale() {
            if (ticketNumber > 0) {
                this.ticketNumber --;
                return true;
            }
            return false;
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
                try {
                    TimeUnit.MILLISECONDS.sleep(new Random().nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        Ticket ticket = new Ticket();
        Saler saler1 = new Saler(ticket, "AA");
        Saler saler2 = new Saler(ticket, "BB");
        Saler saler3 = new Saler(ticket, "CC");

        saler1.start();
        saler2.start();
        saler3.start();
    }

}
