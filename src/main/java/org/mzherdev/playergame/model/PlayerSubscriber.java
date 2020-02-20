package org.mzherdev.playergame.model;

import org.mzherdev.playergame.model.observer.Observable;
import org.mzherdev.playergame.model.observer.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of org.mzherdev.playergame.model.observer.Observable interface
 *
 * @see org.mzherdev.playergame.model.observer.Observable interface
 */
public class PlayerSubscriber implements Observable {

    private List<Observer> observers;
    private String message;
    private final Object MUTEX = new Object();

    public PlayerSubscriber(String message) {
        this.message = message;
        this.observers = new ArrayList<>();
    }

    @Override
    public void register(Observer o) {
        if (o == null) {
            throw new NullPointerException("Observer could not be null");
        }
        synchronized (MUTEX) {
            if (!observers.contains(o)) {
                observers.add(o);
                o.setObservable(this);
            }
        }
    }

    @Override
    public void deregister(Observer o) {
        synchronized (MUTEX) {
            observers.remove(o);
        }
    }

    @Override
    public void notifyObservers() {
        final List<Observer> observers = getObservers();
        while (observers.stream().noneMatch(Observer::isDone)) {
            for (Observer observer : observers) {
                observer.update();
            }
        }
    }

    @Override
    public Object getUpdate(Observer o) {
        final List<Observer> observers = getObservers();
        if (observers.stream().anyMatch(Observer::isDone)) {
            return null;
        }
        return this.message;
    }

    @Override
    public void postMessage(String msg) {
//        System.out.println("Message Posted: " + msg);
        this.message = msg;
    }

    /**
     * synchronization is used to make sure that any observer registered after message was received is not notified
     */
    private List<Observer> getObservers() {
        List<Observer> observersLocal;
        synchronized (MUTEX) {
            observersLocal = new ArrayList<>(this.observers);
        }
        return observersLocal;
    }
}
