package org.mzherdev.playergame.model.observer;

/**
 * This class represents an observable object, or "data"
 * in the model-view paradigm. It can be subclassed to represent an
 * object that the application wants to have observed.
 */
public interface Observable {
    /**
     * Adds an observer to the set of observers for this object, provided
     * that it is not the same as some observer already in the list.
     *
     * @param o an observer to be added.
     * @throws NullPointerException if the parameter o is null.
     */
    void register(Observer o);

    /**
     * Removes an observer from the set of observers of this object
     *
     * @param o observer to remove
     */
    void deregister(Observer o);

    /**
     * If this object has changed, then notify all of its observers.
     */
    void notifyObservers();

    /**
     * Notify if
     *
     * @param o org.mzherdev.playergame.model.observer.Oberver to notify about changing the message
     * @return message for player to reply
     */
    Object getUpdate(Observer o);

    /**
     * Post the message to observers.
     *
     * @param msg message to sent
     */
    void postMessage(String msg);

}