package com.threeInRow;

public class Cell {
    private static int nrOfCells = 1;
    //Symbol of cell (X|O) or cell number by default
    private String value;
    private Player player;
    private boolean isTaken;
    private int index;
    private CellCollision cellCollision;

    public Cell() {
        this.value = String.valueOf(this.nrOfCells);
        this.player = null;
        this.isTaken = false;
        this.index++;
        this.cellCollision = new CellCollision();
        this.nrOfCells++;
    }

    protected static int getNrOfCells() {
        return nrOfCells;
    }

    protected static void setNrOfCells(int nrOfCells) {
        Cell.nrOfCells = nrOfCells;
    }

    protected boolean isTaken() {
        return isTaken;
    }

    protected void setTaken(boolean taken) {
        this.isTaken = taken;
    }

    protected CellCollision getCellCollision() {
        return cellCollision;
    }

    protected void setCellCollision(CellCollision cellCollision) {
        this.cellCollision = cellCollision;
    }

    protected String getValue() {
        return value;
    }

    protected void setValue(String value) {
        this.value = value;
    }

    protected Player getPlayer() {
        return player;
    }

    protected void setPlayer(Player player) {
        this.player = player;
    }
}
