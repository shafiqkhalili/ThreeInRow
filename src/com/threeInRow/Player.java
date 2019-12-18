package com.threeInRow;

import java.util.ArrayList;

public abstract class Player {
    private static ArrayList<Player> allPlayers;
    protected boolean isAi;
    private String name;
    private String symbol;
    private int nrOfMoves;
    private int winStats;
    private int loseStats;
    private boolean isPlaying;

    public Player(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
        this.nrOfMoves = 0;
        this.winStats = 0;
        this.loseStats = 0;
        this.isPlaying = false;
    }

    public static void addAllPlayers(Player player) {
        allPlayers.add(player);
    }

    protected static ArrayList<Player> getAllPlayers() {
        return allPlayers;
    }

    public boolean isAi() {
        return isAi;
    }

    protected boolean isPlaying() {
        return isPlaying;
    }

    protected void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    protected String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    protected String getSymbol() {
        return symbol;
    }

    protected void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    protected int getNrOfMoves() {
        return nrOfMoves;
    }

    protected void setNrOfMoves(int nrOfMoves) {
        this.nrOfMoves = nrOfMoves;
    }

    public int getWinStats() {
        return winStats;
    }

    public void setWinStats(int winStats) {
        this.winStats = winStats;
    }

    public int getLoseStats() {
        return loseStats;
    }

    public void setLoseStats(int loseStats) {
        this.loseStats = loseStats;
    }

    protected abstract Cell nextMove(Board board) throws Exception;
}