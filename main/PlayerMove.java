package main;

import java.io.Serializable;
import java.util.ArrayList;
import java.awt.Color;
public class PlayerMove implements Serializable {
    private static final long serialVersionUID = 1L;
    private ArrayList<Color> playerSequence;
    private boolean moveResult;

    public PlayerMove(ArrayList<Color> playerSequence, boolean bool) {
        this.playerSequence = playerSequence;
        this.moveResult = bool;
    }

    public ArrayList<Color> getPlayerSequence() {
        return playerSequence;
    }
    public boolean getMoveResult() {
        return moveResult;
    }
}
