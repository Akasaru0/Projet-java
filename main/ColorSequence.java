package main;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.awt.color.*;

public class ColorSequence implements Serializable {
    private static final long serialVersionUID = 1L;
    private ArrayList<String> sequence;
    private int lenght;

    public ColorSequence() {
        this.sequence = new ArrayList<>();
    }
    public ColorSequence(ArrayList<String> sequence) {
        this.sequence = sequence;
    }


    public void addToSequence(String color) {
        sequence.add(color);
    }

    public List<String> getSequence() {
        return sequence;
    }
    public int getLenght() {
    	return sequence.size();
    }
}