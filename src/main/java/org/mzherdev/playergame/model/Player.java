package org.mzherdev.playergame.model;

import org.mzherdev.playergame.model.observer.Observable;
import org.mzherdev.playergame.model.observer.Observer;

/**
 * Implementation of org.mzherdev.playergame.model.observer.Observer interface
 *
 * @see org.mzherdev.playergame.model.observer.Observer interface
 */
public class Player implements Observer {

    private static final int STOP_CONDITION = 10;

    private String name;
    private Observable topic;
    private String message;
    private int messageSent;
    private int messageReceived;

    public Player(String name) {
        this.name = name;
        this.message = "";
    }

    @Override
    public void update() {
        String msg = (String) topic.getUpdate(this);
        if (msg == null) {
            return;
        }
        if (!isDone()) {
            this.messageReceived++;
            this.message = msg + " " + messageSent;
            this.messageSent++;
            this.topic.postMessage(this.message);
            System.out.println(name + ": message: " + this.message);
        }
    }

    @Override
    public void setObservable(Observable o) {
        this.topic = o;
    }

    @Override
    public boolean isDone() {
        return messageSent >= STOP_CONDITION && messageReceived >= STOP_CONDITION;
    }

}
