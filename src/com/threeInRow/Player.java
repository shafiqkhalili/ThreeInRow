package com.threeInRow;

import java.util.ArrayList;

public class Player {
    protected String name;
    protected String symbol;
    protected int nrOfMoves;
    protected int winStats;
    protected int loseStats;

    public Player(String name, String symbol) {
        setName(name);
        setSymbol(symbol);
        this.nrOfMoves = 0;
        this.winStats = 0;
        this.loseStats = 0;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getNrOfMoves() {
        return nrOfMoves;
    }

    public int getWinStats() {
        return winStats;
    }

    public int getLoseStats() {
        return loseStats;
    }
}