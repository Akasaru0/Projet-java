package main;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;

public class ColorSequence implements Serializable {
    private static final long serialVersionUID = 1L;
    private ArrayList<Color> sequence;
    private int lenght;

    public ColorSequence() {
        this.sequence = new ArrayList<>();
    }
    public ColorSequence(ArrayList<Color> sequence) {
        this.sequence = sequence;
    }


    public void addToSequence(Color color) {
        sequence.add(color);
    }

    public List<Color> getSequence() {
        return sequence;
    }
    public int getLenght() {
    	return sequence.size();
    }

}