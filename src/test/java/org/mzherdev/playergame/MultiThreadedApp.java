package org.mzherdev.playergame;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Dummy multithreaded player game application.
 * Attempt to write multithreaded app with stop condition on 10 received and sent messages
 */
public class MultiThreadedApp {

    public static void main(String[] args) {
        if (args.length != 1) {
            throw new RuntimeException("Please provide message for player to send.");
        }

        ExecutorService ex = Executors.newFixedThreadPool(2);
        Lock lock = new ReentrantLock();
        Player sender = new Player(args[0], true, lock);
        Player receiver = new Player(args[0], false, lock);
        while (sender.continuePlaying()) {
            ex.submit(sender);
            ex.submit(receiver);
        }
        ex.shutdown();
    }
}

class Player extends Thread {
    private static final int STOP_CONDITION = 10;

    private AtomicInteger messagesSent;
    private AtomicInteger messagesReceived;
    private final boolean initiator;
    private String message;
    private Lock lock;

    public Player(String message, boolean initiator, Lock lock) {
        super(initiator ? "Sender" : "Receiver");
        this.message = message;
        this.initiator = initiator;
        this.lock = lock;
        messagesSent = new AtomicInteger(0);
        messagesReceived = new AtomicInteger(0);
    }

    public boolean continuePlaying() {
        return initiator
                ? messagesSent.get() < STOP_CONDITION
                && messagesReceived.get() < STOP_CONDITION
                : messagesSent.get() < STOP_CONDITION
                && messagesReceived.get() < STOP_CONDITION - 1;
    }

    @Override
    public void run() {
        if (continuePlaying()) {
            messagesSent.getAndIncrement();
            messagesReceived.getAndIncrement();
            System.out.println(getName() + ": " + message + " " + messagesSent);
            lock.notifyAll();
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            if (!isInterrupted()) {
                interrupt();
            }
        }
    }
}
