package org.mzherdev.playergame.model.observer;

/**
 * A class can implement the Observer interface when it
 * wants to be informed of changes in observable objects.
 */
public interface Observer {
    /**
     * This method is called when the observed object is changed.
     */
    void update();

    /**
     * attach with object to observe
     *
     * @param o Object to observe
     */
    void setObservable(Observable o);

    /**
     * Check if stop condition achieved
     *
     * @return <tt>true</tt> if Observer ready to finish the game
     */
    boolean isDone();
}