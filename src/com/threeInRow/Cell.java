package com.threeInRow;

public class Cell {
    private static int nrOfCells = 0;
    private int cellIndex;
    //Symbol of cell (X|O) or cell number by default
    private String content;
    private boolean isTaken;
    //Shows how good chance cell has, used for Ai
    private int chanceRank;

    public Cell() {
        this.content = String.valueOf(nrOfCells);
        this.isTaken = false;
        this.chanceRank = 0;
        this.cellIndex = getNrOfCells();
        this.nrOfCells++;
    }

    protected static int getNrOfCells() {
        return nrOfCells;
    }

    protected static void setNrOfCells(int nrOfCells) {
        Cell.nrOfCells = nrOfCells;
    }

    public int getCellIndex() {
        return cellIndex;
    }

    public int getChanceRank() {
        return chanceRank;
    }

    public void setChanceRank(int chanceRank) {
        this.chanceRank = chanceRank;
    }

    protected boolean isTaken() {
        return isTaken;
    }

    protected boolean isNotTaken(){
        if (!this.isTaken) {
            return true;
        }
        return false;
    }
    protected void setTaken(boolean taken) {
        this.isTaken = taken;
    }

//    protected CellCollision getCellCollision() {
//        return cellCollision;
//    }
//
//    protected void setCellCollision(CellCollision cellCollision) {
//        this.cellCollision = cellCollision;
//    }

    protected String getContent() {
        return content;
    }

    protected void setContent(String content) {
        this.content = content;
    }

//    protected Player getPlayer() {
//        return player;
//    }
//
//    protected void setPlayer(Player player) {
//        this.player = player;
//    }
}
