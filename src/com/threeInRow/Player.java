package com.threeInRow;

public abstract class Player {
    private String name;
    private String symbol;
    private int nrOfMoves;
    private int winStats;
    private int loseStats;

    public Player(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
        this.nrOfMoves = 0;
        this.winStats = 0;
        this.loseStats = 0;
    }

    public void setNrOfMoves(int nrOfMoves) {
        this.nrOfMoves = nrOfMoves;
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

    public abstract int selectCell();
}