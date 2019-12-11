package com.threeInRow;

public class Ai extends Player {
    int boardSize;

    public Ai(int size) {
        super("Ai", "A");
        boardSize = size;
    }

    public int getBoardSize() {
        return boardSize;
    }

    @Override
    public int selectCell() {
        int rnd = (int) Math.random() * getBoardSize() + 1;

        return rnd;
    }

}
