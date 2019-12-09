package com.threeInRow;

public class Cell {
    //Symbol of cell (X|O) or cell number by default
    private String value;
    private Player player;
    private static int nrOfCells=1;
    private boolean taken;
    private int index;
    private CellNeighbours cellNeighbours;

    public Cell() {
        this.value = String.valueOf(this.nrOfCells);
        this.player = null;
        this.taken = false;
        this.cellNeighbours = new CellNeighbours();
        this.nrOfCells++;
    }

    public Cell(Player player) {
        this.value = player.symbol;
        this.player = player;
        this.nrOfCells++;
    }

    protected void setValue(String value) {
        this.value = value;
    }

    protected static int getNrOfCells() {
        return nrOfCells;
    }

    protected static void setNrOfCells(int nrOfCells) {
        Cell.nrOfCells = nrOfCells;
    }

    protected boolean isTaken() {
        return taken;
    }

    protected void setTaken(boolean taken) {
        this.taken = taken;
    }

    protected CellNeighbours getCellNeighbours() {
        return cellNeighbours;
    }

    protected void setCellNeighbours(CellNeighbours cellNeighbours) {
        this.cellNeighbours = cellNeighbours;
    }

    protected String getValue() {
        return value;
    }

    protected Player getPlayer() {
        return player;
    }

    protected void setPlayer(Player player) {
        this.player = player;
    }
}
