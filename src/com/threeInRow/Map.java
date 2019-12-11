package com.threeInRow;

import java.util.ArrayList;

public class Map {
    private ArrayList<Cell> cellCoordinates;
    private int nrOfRows;
    private int nrOfColumns;
    private boolean isGameFinished;
    private boolean isWinnerSelected;
    private int remainedEmptyCells;
    /*Local variable to check value of cells in a row and column
     bordering a selected cell on user selection*/
    private ArrayList<String> sameDirectionCellValues;

    public Map(int boardSize) {
        this.remainedEmptyCells = boardSize * boardSize;
        this.nrOfRows = boardSize;
        this.nrOfColumns = boardSize;
        this.isGameFinished = false;
        this.isWinnerSelected = false;
        this.cellCoordinates = new ArrayList<>();
        initializeCells();
    }

    public ArrayList<Cell> getCellCoordinates() {
        return cellCoordinates;
    }

    public int getRemainedEmptyCells() {
        return remainedEmptyCells;
    }

    public void setRemainedEmptyCells(int remainedEmptyCells) {
        this.remainedEmptyCells = remainedEmptyCells;
    }


    public boolean isWinnerSelected() {
        return isWinnerSelected;
    }

    public void setWinnerSelected(boolean winnerSelected) {
        this.isWinnerSelected = winnerSelected;
    }

    public boolean isGameFinished() {
        return isGameFinished;
    }

    public void setGameFinished(boolean gameFinished) {
        this.isGameFinished = gameFinished;
    }

    protected int getNrOfRows() {
        return nrOfRows;
    }

    protected int getNrOfColumns() {
        return nrOfColumns;
    }

    protected void addCell(Cell cell) {
        this.cellCoordinates.add(cell);
    }

    protected void initializeCells() {
        try {
            for (int i = 0; i < getNrOfColumns() * getNrOfRows(); i++) {
                addCell(new Cell());
            }
        } catch (Exception e) {
            System.out.println("initializeCells Error: " + e.getMessage());
        }
    }

    public ArrayList<String> getSameDirectionCellValues() {
        return sameDirectionCellValues;
    }

    /**
     * Visualizes our cell values, empty cell show index of cell instead of its value
     */
    public void drawBoard() {
        try {
            for (int i = 0; i < getCellCoordinates().size(); i++) {
                Cell cell = getCellCoordinates().get(i);
                /*Find last column*/
                int columnBreak = (i + 1) % getNrOfColumns();
                /*For aligning purpose only*/
                if (getNrOfColumns() > 3) {
                    System.out.print("  " + cell.getValue() + "  ");
                } else {
                    System.out.print(" " + cell.getValue() + " ");
                }
                /*If not last column*/
                if (columnBreak > 0) {
                    System.out.print(" | ");
                }
                /*If not first row*/
                if (columnBreak == 0 && i > 0) {
                    System.out.println();
                    /*For aligning purpose only*/
                    if (getNrOfColumns() > 3) {
                        System.out.println("- -- - + - -- - + - -- -");
                    } else {
                        System.out.println("- - + - - + - -");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("drawMap Error: " + e.getMessage());
        }
    }

    public void columnBreaker() {
        System.out.print(" | ");
    }

    /**
     * Loops throw all cells and find the winner
     */
    protected void checkWinner() throws Exception {
        try {
            for (int i = 0; i < getNrOfRows(); i++) {
                boolean isLastColumn = (i + 1) % getNrOfColumns() == 0;
                boolean isFirstColumn = i % getNrOfColumns() == 0;
                boolean isFirstRow = i < getNrOfColumns();
                boolean isLastRow = i + getNrOfColumns() >= getNrOfColumns() * getNrOfRows();
                /*Check if i is the first column in game map to the right in our Map (1,4,7 positions in game map)
                 * If so, get value of all cells in same row if 4 then get value of 5 and 6*/
                try {
                    if (isFirstColumn) {
                        checkRowValue(i);
                    }
                } catch (Exception e) {
                    System.out.println("isFirstColumn error: " + e.getMessage());
                    throw e;
                }

                /*Check if first row in game map then get all cells in column below
                 * if column 2, then get value of cell 5 and 8*/
                try {
                    if (isFirstRow) {
                        checkColumnValue(i);
                    }
                } catch (Exception e) {
                    System.out.println("isFirstRow error: " + e.getMessage());
                    throw e;
                }
                /*If first column and first row, check diagonal values
                * Cell 1,5,8*/
                try {
                    if (isFirstColumn && isFirstRow) {
                        checkRightDiagonal(i);
                    }
                } catch (Exception e) {
                    System.out.println("isFirstColumn && isFirstRow: " + e.getMessage());
                    throw e;
                }
                /*If only last column in first row, check backward diagonal values
                * Cell 3,5,7*/
                try {
                    if (isLastColumn && isFirstRow) {
                        checkLeftDiagonal(i);
                    }
                } catch (Exception e) {
                    System.out.println("isLastColumn && isFirstRow : " + e.getMessage());
                    throw e;
                }
            }
            if (getRemainedEmptyCells() == 0) {
                setGameFinished(true);
            }
        } catch (Exception e) {
            System.out.println("drawMap Error: " + e.getMessage());
            throw e;
        }
    }

    protected boolean checkDirectionValuesForWinner() throws Exception {
        String prevValue = getSameDirectionCellValues().get(0);

        /*Start control from second item*/
        try {
            for (int i = 1; i < getSameDirectionCellValues().size(); i++) {
                /*Check again previous cell if not same, exit loop.*/
                if (getSameDirectionCellValues().get(i) != prevValue) {
                    return false;
                }
                prevValue = getSameDirectionCellValues().get(i);
            }
        } catch (Exception e) {
            System.out.println("checkDirectionValuesForWinner error: " + e.getMessage());
            throw e;
        }
        return true;
    }

    protected void checkRowValue(int i) throws Exception {
        try {
            this.sameDirectionCellValues = new ArrayList<>();
            Cell cell;
            for (int j = i; j < getNrOfRows(); j++) {
                cell = getCellCoordinates().get(j);
                this.sameDirectionCellValues.add(cell.getValue());
            }
            if (checkDirectionValuesForWinner()) {
                setGameFinished(true);
                setWinnerSelected(true);
            }

        } catch (Exception e) {
            System.out.println("isFirstColumn error: " + e.getMessage());
            throw e;
        }
    }

    protected void checkColumnValue(int i) throws Exception {
        try {
            this.sameDirectionCellValues = new ArrayList<>();
            Cell cell;
            for (int j = i; j < getCellCoordinates().size(); j = j + getNrOfColumns()) {
                cell = getCellCoordinates().get(j);
                this.sameDirectionCellValues.add(cell.getValue());
            }
            if (checkDirectionValuesForWinner()) {
                setGameFinished(true);
                setWinnerSelected(true);
            }
        } catch (Exception e) {
            System.out.println("isFirstRow error: " + e.getMessage());
            throw e;
        }
    }

    protected void checkRightDiagonal(int i) throws Exception {
        try {
            this.sameDirectionCellValues = new ArrayList<>();
            Cell cell;
            for (int j = i; j < getCellCoordinates().size(); j = j + getNrOfColumns() + 1) {
                cell = getCellCoordinates().get(j);
                this.sameDirectionCellValues.add(cell.getValue());
            }
            if (checkDirectionValuesForWinner()) {
                setGameFinished(true);
                setWinnerSelected(true);
            }

        } catch (Exception e) {
            System.out.println("isFirstColumn && isFirstRow: " + e.getMessage());
            throw e;
        }
    }

    protected void checkLeftDiagonal(int i) throws Exception{
        try {
            this.sameDirectionCellValues = new ArrayList<>();
            Cell cell;
            int k = 0;
            for (int j = i; j < getCellCoordinates().size(); j = j + getNrOfColumns() - 1) {
                /*Max three items if nr of columns is 3*/
                k++;
                if (k > getNrOfColumns()) {
                    continue;
                }
                cell = getCellCoordinates().get(j);
                this.sameDirectionCellValues.add(cell.getValue());
            }
            if (checkDirectionValuesForWinner()) {
                setGameFinished(true);
                setWinnerSelected(true);

            }
        } catch (Exception e) {
            System.out.println("isLastColumn && isFirstRow : " + e.getMessage());
            throw e;
        }
    }
}