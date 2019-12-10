package com.threeInRow;

import java.util.ArrayList;

public class Map {
    private static ArrayList<Cell> cellCoordinates;
    private int nrOfRows;
    private int nrOfColumns;
    private boolean isGameFinished;
    private boolean isWinnerSelected;
    private boolean isMapFull;
    private int emptyCells;
    private ArrayList<String> sameDirectionCellValues;

    public Map(int nrOfRows, int nrOfColumns) {
        this.emptyCells = nrOfColumns * nrOfRows;
        this.nrOfRows = nrOfRows;
        this.nrOfColumns = nrOfColumns;
        this.isGameFinished = false;
        this.isWinnerSelected = false;
        this.cellCoordinates = new ArrayList<>();
        this.initializePositions();
    }

    protected static ArrayList<Cell> getCellCoordinates() {
        return cellCoordinates;
    }

    public int getEmptyCells() {
        return emptyCells;
    }

    public void setEmptyCells(int emptyCells) {
        this.emptyCells = emptyCells;
    }

    public boolean isMapFull() {
        return isMapFull;
    }

    public void setMapFull(boolean mapFull) {
        isMapFull = mapFull;
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

    protected void addPosition(Cell position) {
        this.cellCoordinates.add(position);
    }

    protected void initializePositions() {
        try {
            for (int i = 0; i < getNrOfColumns() * getNrOfRows(); i++) {
                addPosition(new Cell());
            }
        } catch (Exception e) {
            System.out.println("initializePositions Error: " + e.getMessage());
        }
    }

    public ArrayList<String> getSameDirectionCellValues() {
        return sameDirectionCellValues;
    }

    /**
     * Visualizes our cell values, empty cell show index of cell instead of its value
     */
    public void drawMap() {
        try {
            for (int i = 0; i < getCellCoordinates().size(); i++) {
                Cell cell = getCellCoordinates().get(i);
                /*Find last column*/
                int columnBreak = (i + 1) % getNrOfColumns();

                System.out.print(" " + cell.getValue() + " ");
                /*If not last column*/
                if (columnBreak > 0) {
                    System.out.print(" | ");
                }
                /*If not first row*/
                if (columnBreak == 0 && i > 0) {
                    System.out.println();
                    System.out.println("- - + - - + - -");
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
     * Set neighbours of a certain cell based on its index
     *
     * @param i
     */
    public void setCellNeighbours(int i) {
        Cell cell = getCellCoordinates().get(i);

        boolean isLastColumn = i + 1 % getNrOfColumns() == 0;
        boolean isFirstColumn = i % getNrOfColumns() == 0;
        boolean isFirstRow = i < getNrOfColumns();
        boolean isLastRow = i + getNrOfColumns() >= getNrOfColumns() * getNrOfRows();


        /* Right cell
         * If not last cell in column, add 1 one to i as positions array start from 0 index
         */
        try {
            if (i < getCellCoordinates().size() && !isLastColumn) {
                cell.getCellCollision().setRightCell(getCellCoordinates().get(i + 1));
            }
        } catch (Exception e) {
            System.out.println("setCellCollision Right cell error: " + e.getMessage());
        }
        /*Left cell
         * If not last cell to the left*/
        try {
            if (i > 0 && !isFirstColumn) {
                cell.getCellCollision().setLeftCell(getCellCoordinates().get(i - 1));
            }
        } catch (Exception e) {
            System.out.println("setCellCollision Left Cell error " + e.getMessage());
        }
        /*Top Cell*/
        try {
            if (i + 1 - getNrOfRows() > 0) {
                cell.getCellCollision().setTopCell(getCellCoordinates().get(i - getNrOfRows()));
            }
        } catch (Exception e) {
            System.out.println("setCellCollision Top cell: " + e.getMessage());
        }
        /*Bottom Cell*/
        try {
            if (i + getNrOfRows() < getNrOfRows() * getNrOfColumns()) {
                cell.getCellCollision().setBottomCell(getCellCoordinates().get(i + getNrOfRows()));
            }
        } catch (Exception e) {
            System.out.println("setCellCollision Bottom cell: " + e.getMessage());
        }
        /*Right Top
         * Check if not last column or first row*/
        try {
            if (!isLastColumn && !isFirstRow) {
                cell.getCellCollision().setRightTop(getCellCoordinates().get(i - (getNrOfColumns() - 1)));
            }
        } catch (Exception e) {
            System.out.println("setCellCollision Right top");
        }
        /*Right Bottom*/
        try {
            if (!isLastColumn && !isLastRow) {
                cell.getCellCollision().setRightBottom(getCellCoordinates().get(i + (getNrOfColumns() + 1)));
            }
        } catch (Exception e) {
            System.out.println("setCellCollision Right Bottom: " + e.getMessage());
        }
        /*Left Top*/
        try {
            if (!isFirstColumn && !isFirstRow) {
                cell.getCellCollision().setLeftTop(getCellCoordinates().get(i - (getNrOfColumns() + 1)));
            }
        } catch (Exception e) {
            System.out.println("setCellCollision Left Top: " + e.getMessage());
        }
        /*Left Bottom*/
        try {
            if (!isFirstColumn && !isFirstRow) {
                cell.getCellCollision().setLeftTop(getCellCoordinates().get(i + (getNrOfColumns() - 1)));
            }
        } catch (Exception e) {
            System.out.println("setCellCollision Left Bottom");
        }
        cell.getCellCollision().setNeighboursArray();
    }


    /**
     * Loops throw all cells and find the winner
     */
    protected void checkWinner() {
        try {
            for (int i = 0; i < getNrOfRows(); i++) {
                boolean isLastColumn = (i + 1) % getNrOfColumns() == 0;
                boolean isFirstColumn = i % getNrOfColumns() == 0;
                boolean isFirstRow = i < getNrOfColumns();
                boolean isLastRow = i + getNrOfColumns() >= getNrOfColumns() * getNrOfRows();
                /*Check if i is the first column to the left in our Map
                 * If so, get next x number of columns based to length of our map*/
                try {
                    if (isFirstColumn) {
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
                    }
                } catch (Exception e) {
                    System.out.println("isFirstColumn error: " + e.getMessage());
                }

                /*Check if first row then get all cells in column below*/
                try {
                    if (isFirstRow) {
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
                    }
                } catch (Exception e) {
                    System.out.println("isFirstRow error: " + e.getMessage());
                }
                /*If first column and first row, check diagonal values*/
                try {
                    if (isFirstColumn && isFirstRow) {
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
                    }
                } catch (Exception e) {
                    System.out.println("isFirstColumn && isFirstRow: " + e.getMessage());
                }
                /*If only last column in first row, check backward diagonal values*/
                try {
                    if (isLastColumn && isFirstRow) {
                        this.sameDirectionCellValues = new ArrayList<>();
                        Cell cell;
                        for (int j = i; j < getCellCoordinates().size(); j = j + getNrOfColumns() - 1) {
                            cell = getCellCoordinates().get(j);
                            this.sameDirectionCellValues.add(cell.getValue());
                        }
                        if (checkDirectionValuesForWinner()) {
                            setGameFinished(true);
                            setWinnerSelected(true);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("isLastColumn && isFirstRow : " + e.getMessage());
                }
            }
            if (getEmptyCells() == 0) {
                setGameFinished(true);
            }
        } catch (Exception e) {
            System.out.println("drawMap Error: " + e.getMessage());
        }
    }

    protected boolean checkDirectionValuesForWinner() {
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
        }
        return true;
    }

}