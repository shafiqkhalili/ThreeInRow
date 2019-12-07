package com.threeInRow;

import java.util.ArrayList;

public class DrawMap {
    protected static ArrayList<Cell> positions;
    protected int nrOfRows;
    protected int nrOfColumns;

    public DrawMap(int nrOfRows, int nrOfColumns) {
        this.nrOfRows = nrOfRows;
        this.nrOfColumns = nrOfColumns;
        this.positions = new ArrayList<>();
        this.initializePositions();
    }

    public void initializePositions() {
        try {
            for (int i = 0; i < nrOfColumns * nrOfRows; i++) {
                positions.add(i, new Cell());
            }
        } catch (Exception e) {
            System.out.println("initializePositions Error: " + e.getMessage());
        }
    }

    public void drawMap() {
        try {
            for (int i = 0; i < positions.size(); i++) {
                if (i % this.nrOfRows == 0) {
                    this.rowBreaker();
                }
                System.out.println(positions.get(i).value+"|");
                //columnBreaker();
            }
        } catch (Exception e) {
            System.out.println("drawMap Error: " + e.getMessage());
        }
    }

    public void setPositions(ArrayList<Cell> positions) {
        this.positions = positions;
    }

    public void columnBreaker() {
        System.out.println("|");
    }

    public void rowBreaker() {
        try {
            for (int i = 1; i <= this.nrOfColumns; i++) {
                System.out.println("- - +");

            }
        } catch (Exception e) {
            System.out.println("rowBreaker Error: " + e.getMessage());
        }

    }
}