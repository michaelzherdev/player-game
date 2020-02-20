package org.mzherdev.playergame;

import org.mzherdev.playergame.model.Player;
import org.mzherdev.playergame.model.PlayerSubscriber;
import org.mzherdev.playergame.model.observer.Observer;

/**
 * Main class. Runs application
 */
public class App {

    public static void main(String[] args) {
        if (args.length != 1) {
            throw new RuntimeException("Please provide message for game.");
        }

        PlayerSubscriber observable = new PlayerSubscriber(args[0]);

        Observer player1 = new Player("Player 1");
        Observer player2 = new Player("Player 2");

        observable.register(player1);
        observable.register(player2);

        observable.notifyObservers();
        System.out.println("Done!");
    }
}
