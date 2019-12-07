package com.threeInRow;

public class Cell {
    //Symbol of cell (X|O) or cell number by default
    protected String value;
    protected Player player;
    protected static int nrOfCells=1;

    public Cell() {
        this.value = String.valueOf(nrOfCells);
        this.player = null;
        this.nrOfCells++;
    }

    public Cell(Player player) {
        this.value = player.symbol;
        this.player = player;
        this.nrOfCells++;
    }

    public String getValue() {
        return value;
    }

    public void setValue(Player player) {
        this.value = player.symbol;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void drawCell(){

    }
}
