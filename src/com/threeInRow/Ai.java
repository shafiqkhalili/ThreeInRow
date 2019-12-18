package com.threeInRow;

import java.util.ArrayList;
import java.util.Random;

public class Ai extends Player {

    public Ai() {
        super("Ai", "A");
        super.isAi = true;
    }

    @Override
    protected Cell nextMove(Board board) throws Exception {
        ArrayList<Cell> cellCoordinates = board.getCellCoordinates();
        Cell selectedCell = null;
        //Easy game or if board is empty, select random cell
        if (board.getEmptyCellsCount() == cellCoordinates.size() || board.getNrOfColumns() == 3) {
            //Takes random cell
            selectedCell = randomEmptyCell(board);
        } else if (board.getNrOfColumns() > 3) {
            //Finds best move
            selectedCell = highestCell(board);
        }
        return selectedCell;
    }

    protected Cell highestCell(Board board) throws Exception {
        ArrayList<Cell> cellCoordinates = board.getCellCoordinates();
        //Check best move and sets value into each cell based on cell's winning chance
        evaluateBoard(board);

        /*Temporary variable to compare cell's chance*/
        int bestChance = 0;
        Cell bestCell = null;
        try {
            for (int i = 0; i < cellCoordinates.size(); i++) {
                Cell cell = cellCoordinates.get(i);
                /*Finding the the best choice cell, highest is best*/
                if (cell.getChanceRank() > bestChance && cell.isNotTaken()) {
                    bestChance = cell.getChanceRank();
                    bestCell = cell;
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return bestCell;
    }

    /**
     * Loops throw all cells finds best cell for next location
     */
    protected void evaluateBoard(Board board) throws Exception {
        try {
            boolean isWinner = false;
            ArrayList<Cell> cellCoordinates = board.getCellCoordinates();

            for (int i = 0; i < cellCoordinates.size(); i++) {
                boolean isLastColumn = (i + 1) % board.getNrOfColumns() == 0;
                boolean isFirstColumn = i % board.getNrOfColumns() == 0;
                boolean isFirstRow = i < board.getNrOfColumns();
                boolean isLastRow = i + board.getNrOfColumns() >= board.getNrOfColumns() * board.getNrOfRows();
                /*Check if i is the first column in game map to the right in our Board (1,4,7 positions in game map)
                 * If so, get value of all cells in same row if 4 then get value of 5 and 6*/
                try {
                    if (isFirstColumn) {
                        //Create array of content of each cell in specific direction
                        board.setRowValue(i);
                        //Pick the best cell for next move
                        assignBestChoice(board);
                    }
                } catch (Exception e) {
                    System.out.println("Line 161: " + e.getMessage());
                    throw e;
                }

                /*Check if it's first row in game map then get all cells in column below
                 * e.g. if column 2, then get value of cell 5 and 8*/
                try {
                    if (isFirstRow) {
                        board.setColumnValue(i);
                        assignBestChoice(board);
                    }
                } catch (Exception e) {
                    System.out.println("Line 177: " + e.getMessage());
                    throw e;
                }
                /*If first column and first row, check diagonal values
                 * Cell 1,5,8*/
                try {
                    if (isFirstColumn && isFirstRow) {
                        board.setRightDiagonal(i);
                        assignBestChoice(board);
                    }
                } catch (Exception e) {
                    System.out.println("Line 192: " + e.getMessage());
                    throw e;
                }
                /*If only last column in first row, check backward diagonal values
                 * Cell 3,5,7*/
                try {
                    if (isLastColumn && isFirstRow) {
                        board.setLeftDiagonal(i);
                        assignBestChoice(board);
                    }
                } catch (Exception e) {
                    System.out.println("Line 297: " + e.getMessage());
                    throw e;
                }
            }
            if (board.getEmptyCellsCount() == 0) {
                board.setGameFinished(true);
            }

        } catch (Exception e) {
            System.out.println("Line 215: " + e.getMessage());
            throw e;
        }
    }


    protected void assignBestChoice(Board board) throws Exception {
        /*Nr of occurrence*/
        int humanTimes = 0;
        int aiTimes = 0;

        int emptyCellIndex = -1;
        /*Nr of empty cells in array*/
        int emptyTimes = 0;
        int arrayLength = board.getSingleDirection().size();

        Cell cell = null;
        /*Loop throw values of a single row/column/diagonal */
        /*and list empty/taken cells*/
        try {
            for (int i = 0; i < arrayLength; i++) {
                cell = board.getSingleDirection().get(i);
                if (!cell.getContent().equalsIgnoreCase(getSymbol()) && cell.isTaken()) {
                    humanTimes++;
                } else if (cell.getContent().equalsIgnoreCase(getSymbol())) {
                    aiTimes++;
                } else {
                    emptyCellIndex = cell.getCellIndex();
                    emptyTimes++;
                }
            }
        } catch (Exception e) {
            System.out.println("Line 426: " + e.getMessage());
        }
        if (emptyTimes > 0) {
            try {
                /*Return -1 if first or last cells in array are taken*/
                int FirstOrLast = board.getFirstOrLastIndex();

                /*Return -1 if middle cell in array is taken, else returns index of cell*/
                int middleIndex = board.getMiddleIndex();

                /*Priority for corner cells then middle else other cells*/
                emptyCellIndex = FirstOrLast >= 0 ? FirstOrLast : middleIndex >= 0 ? middleIndex : emptyCellIndex;

                cell = board.getCellCoordinates().get(emptyCellIndex);
            } catch (Exception e) {
                System.out.println("Line 435: " + e.getMessage());
            }

            /*If Ai has only one cell left to win*/
            if (aiTimes == (arrayLength - 1)) {
                //highest rank 512
                cell.setChanceRank(512 + cell.getChanceRank());
            }
            //If opposite player has one move left to win, block the opposite player
            else if (humanTimes == (arrayLength - 1)) {
                cell.setChanceRank(256 + cell.getChanceRank());
            }
            /*Give rest of cells lower chanceRank so that not get higher
             * than 512/256 because those are more important*/
            else {
                /*If only Ai has cells, try more cells*/
                if (aiTimes > 0 && humanTimes == 0) {
                    cell.setChanceRank((32*aiTimes) + cell.getChanceRank());
                }
                /*If only opposite player has cells, take empty cell to block opponent*/
                else if (humanTimes > 0 && aiTimes == 0) {
                    cell.setChanceRank(16 + cell.getChanceRank());
                } else {
                    /*Random array index*/
                    cell = randomEmptyCell(board);
                    cell.setChanceRank(4 + cell.getChanceRank());
                }
            }
        }
    }

    /**
     * Return a random available cell
     *
     * @return
     */
    protected Cell randomEmptyCell(Board board) {
        /*Random array index*/
        Random r = new Random();
        Cell cell;
        int rndIndex = r.nextInt(board.getEmptyCellsIndex().size());
        try {
            int cellIndex = board.getEmptyCellsIndex().get(rndIndex);
            cell = board.getCellCoordinates().get(cellIndex);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
        return cell;
    }
}
