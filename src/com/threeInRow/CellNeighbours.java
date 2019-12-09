package com.threeInRow;

import java.util.ArrayList;

public class CellNeighbours{
    private Cell rightCell;
    private Cell leftCell;
    private Cell topCell;
    private Cell bottomCell;
    private Cell rightBottom;
    private Cell rightTop;
    private Cell leftBottom;
    private Cell leftTop;
    private ArrayList<Cell> neighboursArray;

    public CellNeighbours() {
        this.rightCell = null;
        this.leftCell = null;
        this.topCell = null;
        this.bottomCell = null;
        this.rightBottom = null;
        this.rightTop = null;
        this.leftBottom = null;
        this.leftTop = null;
        this.neighboursArray = new ArrayList<>();
        setNeighboursArray();
    }

    protected ArrayList<Cell> getNeighboursArray() {
        return neighboursArray;
    }

    protected void setNeighboursArray() {
        getNeighboursArray().add(getRightCell());
        getNeighboursArray().add(getLeftCell());
        getNeighboursArray().add(getTopCell());
        getNeighboursArray().add(getBottomCell());
        getNeighboursArray().add(getRightBottom());
        getNeighboursArray().add(getRightTop());
        getNeighboursArray().add(getLeftBottom());
        getNeighboursArray().add(getLeftTop());
        getNeighboursArray().add(getRightCell());
        getNeighboursArray().add(getRightCell());
    }

    protected Cell getRightCell() {
        return rightCell;
    }

    protected void setRightCell(Cell rightCell) {
        this.rightCell = rightCell;
    }

    protected Cell getLeftCell() {
        return leftCell;
    }

    protected void setLeftCell(Cell leftCell) {
        this.leftCell = leftCell;
    }

    protected Cell getTopCell() {
        return topCell;
    }

    protected void setTopCell(Cell topCell) {
        this.topCell = topCell;
    }

    protected Cell getBottomCell() {
        return bottomCell;
    }

    protected void setBottomCell(Cell bottomCell) {
        this.bottomCell = bottomCell;
    }

    protected Cell getRightBottom() {
        return rightBottom;
    }

    protected void setRightBottom(Cell rightBottom) {
        this.rightBottom = rightBottom;
    }

    protected Cell getRightTop() {
        return rightTop;
    }

    protected void setRightTop(Cell rightTop) {
        this.rightTop = rightTop;
    }

    protected Cell getLeftBottom() {
        return leftBottom;
    }

    protected void setLeftBottom(Cell leftBottom) {
        this.leftBottom = leftBottom;
    }

    protected Cell getLeftTop() {
        return leftTop;
    }

    protected void setLeftTop(Cell leftTop) {
        this.leftTop = leftTop;
    }
}
