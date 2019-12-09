package com.threeInRow;

import java.util.ArrayList;

public class Map {
    protected static ArrayList<Cell> positions;
    protected int nrOfRows;
    protected int nrOfColumns;

    public Map(int nrOfRows, int nrOfColumns) {
        this.nrOfRows = nrOfRows;
        this.nrOfColumns = nrOfColumns;
        this.positions = new ArrayList<>();
        this.initializePositions();
    }

    protected int getNrOfRows() {
        return nrOfRows;
    }

    protected int getNrOfColumns() {
        return nrOfColumns;
    }

    protected static ArrayList<Cell> getPositions() {
        return positions;
    }

    protected void addPosition(Cell position) {
        this.positions.add(position);
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

    /**
     * Visualizes our cell values, empty cell show index of cell instead of its value
     */
    public void drawMap() {
        try {
            for (int i = 0; i < getPositions().size(); i++) {
                Cell cell = getPositions().get(i);
                int columnBreak = (i + 1) % getNrOfColumns();
                System.out.print(" " + cell.getValue() + " ");

                if (columnBreak > 0) {
                    System.out.print(" | ");
                }
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
        Cell cell = getPositions().get(i);

        boolean isLastColumn = i + 1 % getNrOfColumns() == 0;
        boolean isFirstColumn = i % getNrOfColumns() == 0;
        boolean isFirstRow = i < getNrOfColumns();
        boolean isLastRow = i + getNrOfColumns() >= getNrOfColumns() * getNrOfRows();
        /* Right cell
         * If not last cell in column, add 1 one to i as positions array start from 0 index
         */
        try {
            if (i < positions.size() && !isLastColumn) {
                cell.getCellNeighbours().setRightCell(getPositions().get(i + 1));
            }
        } catch (Exception e) {
            System.out.println("setCellNeighbours Right cell error: " + e.getMessage());
        }
        /*Left cell
         * If not last cell to the left*/
        try {
            if (i > 0 && !isFirstColumn) {
                cell.getCellNeighbours().setLeftCell(getPositions().get(i - 1));
            }
        } catch (Exception e) {
            System.out.println("setCellNeighbours Left Cell error " + e.getMessage());
        }
        /*Top Cell*/
        try {
            if (i + 1 - getNrOfRows() > 0) {
                cell.getCellNeighbours().setTopCell(getPositions().get(i - getNrOfRows()));
            }
        } catch (Exception e) {
            System.out.println("setCellNeighbours Top cell: " + e.getMessage());
        }
        /*Bottom Cell*/
        try {
            if (i + getNrOfRows() < getNrOfRows() * getNrOfColumns()) {
                cell.getCellNeighbours().setBottomCell(getPositions().get(i + getNrOfRows()));
            }
        } catch (Exception e) {
            System.out.println("setCellNeighbours Bottom cell: " + e.getMessage());
        }
        /*Right Top
         * Check if not last column or first row*/
        try {
            if (!isLastColumn && !isFirstRow) {
                cell.getCellNeighbours().setRightTop(getPositions().get(i - (getNrOfColumns() - 1)));
            }
        } catch (Exception e) {
            System.out.println("setCellNeighbours Right top");
        }
        /*Right Bottom*/
        try {
            if (!isLastColumn && !isLastRow) {
                cell.getCellNeighbours().setRightBottom(getPositions().get(i + (getNrOfColumns() + 1)));
            }
        } catch (Exception e) {
            System.out.println("setCellNeighbours Right Bottom: " + e.getMessage());
        }
        /*Left Top*/
        try {
            if (!isFirstColumn && !isFirstRow) {
                cell.getCellNeighbours().setLeftTop(getPositions().get(i - (getNrOfColumns() + 1)));
            }
        } catch (Exception e) {
            System.out.println("setCellNeighbours Left Top: " + e.getMessage());
        }
        /*Left Bottom*/
        try {
            if (!isFirstColumn && !isFirstRow) {
                cell.getCellNeighbours().setLeftTop(getPositions().get(i + (getNrOfColumns() - 1)));
            }
        } catch (Exception e) {
            System.out.println("setCellNeighbours Left Bottom");
        }
    }

    /**
     * Loops throw all cells and find the winner
     */
    protected void findWinner(Cell cell) {
        ArrayList<Cell> neighbourCells = cell.getCellNeighbours().getNeighboursArray();

        for (Cell c: neighbourCells) {
            if (c.getValue() == null) {

            }
        }
    }
}