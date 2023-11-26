package main;

import java.io.Serializable;
import java.util.ArrayList;
import java.awt.Color;

public class PlayerMove implements Serializable {
    private static final long serialVersionUID = 1L;
    private ArrayList<String> playerSequence;
    private boolean moveResult;

    public PlayerMove(ArrayList<String> playerSequence, boolean bool) {
        this.playerSequence = playerSequence;
        this.moveResult = bool;
    }

    public ArrayList<String> getPlayerSequence() {
        return playerSequence;
    }
    public boolean getMoveResult() {
        return moveResult;
    }
}
