package controller;

import model.Crossword;

import java.util.Comparator;

public class CrosswordComparator implements Comparator<Crossword> {
    @Override
    public int compare(Crossword o1, Crossword o2) {
        return o1.getPassword().length() - o2.getPassword().length();
    }

}
