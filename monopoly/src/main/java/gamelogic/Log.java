package gamelogic;

import java.util.Vector;

/*
 This class will be used to store information about the last 20 important events in the game.
 That information will be displayed on the right side of the screen while a player is waiting
 for their turn.
 */
public class Log {

    private static final int MAX_NUM_LOGS = 20;
    private Vector<String> recordings;

    public Log() {
        recordings = new Vector<>();
    }

    public void addEntry(String entry) {
        if (recordings.size() >= MAX_NUM_LOGS) {
            recordings.remove(0);
        }
        recordings.add(entry);
    }

    public Vector<String> getRecordings() { return recordings; }

}
