package com.threeInRow;

import java.util.ArrayList;

public class Ai extends Player {

    public Ai() {
        super("Ai", "A");
        super.isAi = true;
    }

    @Override
    protected int selectCell() {
        int rnd = (int) Math.random() * 9;

        return rnd;
    }
}
