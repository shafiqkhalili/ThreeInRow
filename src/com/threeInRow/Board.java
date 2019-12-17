package com.threeInRow;

import java.util.ArrayList;
import java.util.Random;

public class Board {
    /**
     * Info about all cells in board
     */
    private ArrayList<Cell> cellCoordinates;
    private int nrOfRows;
    private int nrOfColumns;
    private boolean isGameFinished;
    private boolean isWinnerSelected;
    //Nr  of empty cells available
    private int emptyCellsCount;
    private int gameDifficulty;
    /**
     * Local variable to check value of cells in single row and column or diagonal
     */
    private ArrayList<Cell> singleDirection;
    //Array of index of empty cells, used for taking random empty cell
    private ArrayList<Integer> emptyCellsIndex;
    private ArrayList<Player> players;

    public Board(int boardSize, ArrayList<Player> players) {
        this.emptyCellsCount = boardSize * boardSize;
        this.nrOfRows = boardSize;
        this.nrOfColumns = boardSize;
        this.isGameFinished = false;
        this.isWinnerSelected = false;
        this.cellCoordinates = new ArrayList<>();
        this.emptyCellsIndex = new ArrayList<>();
        this.players = players;
        initializeCells();
    }

    public int getGameDifficulty() {
        return gameDifficulty;
    }

    public void setGameDifficulty(int gameDifficulty) {
        this.gameDifficulty = gameDifficulty;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    protected ArrayList<Cell> getCellCoordinates() {
        return cellCoordinates;
    }

    public ArrayList<Integer> getEmptyCellsIndex() {
        return emptyCellsIndex;
    }

    public void setEmptyCellsIndex(Integer emptyCellsIndex) {
        this.emptyCellsIndex.add(emptyCellsIndex);
    }

    public void unsetEmptyCellIndex(Integer emptyCellIndex) {
        int position = getEmptyCellsIndex().indexOf(emptyCellIndex);
        this.emptyCellsIndex.remove(position);
    }

    protected int getEmptyCellsCount() {
        return emptyCellsCount;
    }

    protected void setEmptyCellsCount(int emptyCellsCount) {
        this.emptyCellsCount = emptyCellsCount;
    }

    protected boolean isWinnerSelected() {
        return isWinnerSelected;
    }

    protected void setWinnerSelected(boolean winnerSelected) {
        this.isWinnerSelected = winnerSelected;
    }

    protected boolean isGameFinished() {
        return isGameFinished;
    }

    protected void setGameFinished(boolean gameFinished) {
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
                setEmptyCellsIndex(i);
            }
        } catch (Exception e) {
            System.out.println("Line 82: " + e.getMessage());
        }
    }

    /*Cell on each row / column*/
    protected ArrayList<Cell> getSingleDirection() {
        return singleDirection;
    }

    /**
     * Visualizes our cell values, empty cell show index of cell instead of its value
     */
    protected void drawBoard() {
        try {
            for (int i = 0; i < getCellCoordinates().size(); i++) {
                Cell cell = getCellCoordinates().get(i);
                int cellLength = cell.getContent().length();
                /*Find last column on game board to know next line, (3,6 and 9)*/
                boolean isLastRow = i + getNrOfColumns() >= getNrOfColumns() * getNrOfRows();
                boolean isLastColumn = (i + 1) % getNrOfColumns() == 0;
                /*if last column result is 0 */
                int columnBreak = (i + 1) % getNrOfColumns();
                /*For aligning purpose only*/
                if (getNrOfColumns() > 3 && cellLength == 1) {
                    System.out.print("  " + cell.getContent() + " ");
                } else if (getNrOfColumns() > 3 && cellLength > 1) {
                    System.out.print(" " + cell.getContent() + " ");
                } else {
                    System.out.print(" " + cell.getContent() + " ");
                }
                /*If not last column*/
                if (columnBreak > 0) {
                    if (getNrOfColumns() > 3) {
                        System.out.print(" | ");
                    } else {
                        System.out.print(" |  ");
                    }
                }
                /*If not first row*/
                if (columnBreak == 0 && i > 0 && !isLastRow) {
                    /*Line below every row*/
                    System.out.println();
                    for (int j = 0; j < getNrOfColumns(); j++) {
                        cellLength = (cellLength == 1) ? cellLength + 1 : cellLength;
                        for (int k = 0; k < cellLength; k++) {
                            System.out.print("- ");
                        }
                        if (j < getNrOfColumns() - 1) {
                            System.out.print(" + ");
                        }
                    }
                    System.out.println();
                }
            }
            System.out.println();
        } catch (Exception e) {
            System.out.println("Line 131: " + e.getMessage());
        }
    }

    protected void columnBreaker() {
        System.out.print(" | ");
    }

    /**
     * Loops throw all cells and find the winner
     * and find best cell for next location
     */
    protected void evaluateBoard() throws Exception {
        try {
            boolean isWinner = false;
            for (int i = 0; i < getCellCoordinates().size(); i++) {
                boolean isLastColumn = (i + 1) % getNrOfColumns() == 0;
                boolean isFirstColumn = i % getNrOfColumns() == 0;
                boolean isFirstRow = i < getNrOfColumns();
                boolean isLastRow = i + getNrOfColumns() >= getNrOfColumns() * getNrOfRows();
                /*Check if i is the first column in game map to the right in our Board (1,4,7 positions in game map)
                 * If so, get value of all cells in same row if 4 then get value of 5 and 6*/
                try {
                    if (isFirstColumn) {
                        //Create array of content of each cell in specific direction
                        setRowValue(i);
                        //Pick the best cell for next move
                        assignBestChoice();
                        //Check for the winner
                        checkIfCoordinatesAreSame();
                    }
                } catch (Exception e) {
                    System.out.println("Line 161: " + e.getMessage());
                    throw e;
                }

                /*Check if it's first row in game map then get all cells in column below
                 * e.g. if column 2, then get value of cell 5 and 8*/
                try {
                    if (isFirstRow) {
                        setColumnValue(i);
                        assignBestChoice();
                        checkIfCoordinatesAreSame();
                    }
                } catch (Exception e) {
                    System.out.println("Line 177: " + e.getMessage());
                    throw e;
                }
                /*If first column and first row, check diagonal values
                 * Cell 1,5,8*/
                try {
                    if (isFirstColumn && isFirstRow) {
                        setRightDiagonal(i);
                        assignBestChoice();
                        checkIfCoordinatesAreSame();
                    }
                } catch (Exception e) {
                    System.out.println("Line 192: " + e.getMessage());
                    throw e;
                }
                /*If only last column in first row, check backward diagonal values
                 * Cell 3,5,7*/
                try {
                    if (isLastColumn && isFirstRow) {
                        setLeftDiagonal(i);
                        assignBestChoice();
                        checkIfCoordinatesAreSame();
                    }
                } catch (Exception e) {
                    System.out.println("Line 297: " + e.getMessage());
                    throw e;
                }
            }
            if (getEmptyCellsCount() == 0) {
                setGameFinished(true);
            }

        } catch (Exception e) {
            System.out.println("Line 215: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Loop throw string values of an Array and check if all are equal
     *
     * @return boolean
     * @throws Exception
     */
    protected void checkIfCoordinatesAreSame() throws Exception {
        boolean result = true;
        Cell firstCell = getSingleDirection().get(0);

        /*Start control from second item*/
        try {
            for (int i = 1; i < getSingleDirection().size(); i++) {
                /*Check again previous cell if not same, exit loop.*/
                if (getSingleDirection().get(i).getContent() != firstCell.getContent()) {
                    result = false;
                    break;
                }
                firstCell = getSingleDirection().get(i);
            }
            if (result) {
                setGameFinished(true);
                setWinnerSelected(true);
            }
        } catch (Exception e) {
            System.out.println("Line 241: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Sets all cells value in given row into sameCoordinate Array
     *
     * @param i
     * @throws Exception
     */
    protected void setRowValue(int i) throws Exception {
        try {
            this.singleDirection = new ArrayList<>();
            Cell cell;
            for (int j = 0; j < getNrOfColumns(); j++) {
                cell = getCellCoordinates().get(i);
                this.singleDirection.add(cell);
                i++;
            }
        } catch (Exception e) {
            System.out.println("Line 263 " + e.getMessage());
            throw e;
        }
    }

    protected void setColumnValue(int i) throws Exception {
        try {
            this.singleDirection = new ArrayList<>();
            Cell cell;
            for (int j = 0; j < getNrOfRows(); j++) {
                cell = getCellCoordinates().get(i);
                this.singleDirection.add(cell);
                i += getNrOfColumns();
            }
        } catch (Exception e) {
            System.out.println("Line 278: " + e.getMessage());
            throw e;
        }
    }
    /*Ai section*/

    protected void setRightDiagonal(int i) throws Exception {
        try {
            this.singleDirection = new ArrayList<>();
            Cell cell;
            for (int j = 0; j < getNrOfRows(); j++) {
                cell = getCellCoordinates().get(i);
                this.singleDirection.add(cell);
                i += getNrOfColumns() + 1;
            }
        } catch (Exception e) {
            System.out.println("Line 293: " + e.getMessage());
            throw e;
        }
    }

    protected void setLeftDiagonal(int i) throws Exception {
        try {
            this.singleDirection = new ArrayList<>();
            Cell cell;
            for (int j = 0; j < getNrOfRows(); j++) {
                cell = getCellCoordinates().get(i);
                this.singleDirection.add(cell);
                i += getNrOfColumns() - 1;
            }
        } catch (Exception e) {
            System.out.println("Line 314: " + e.getMessage());
            throw e;
        }
    }

    protected Cell highestCell() throws Exception {
        //Check best move
        evaluateBoard();

        /*Temporary variable to compare cell's chance*/
        int bestChance = 0;
        Cell bestCell = null;
        try {
            for (int i = 0; i < getCellCoordinates().size(); i++) {
                Cell cell = getCellCoordinates().get(i);
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
     * Loops throw all cells and find the winner
     */
    /*public void checkBestMove() throws Exception {
        try {

            for (int i = 0; i < getCellCoordinates().size(); i++) {
                *//*Reset previous cell winChance*//*
                getCellCoordinates().get(i).setChanceRank(0);
            }
            for (int i = 0; i < getCellCoordinates().size(); i++) {
                boolean isLastColumn = (i + 1) % getNrOfColumns() == 0;
                boolean isFirstColumn = i % getNrOfColumns() == 0;
                boolean isFirstRow = i < getNrOfColumns();
                boolean isLastRow = i + getNrOfColumns() >= getNrOfColumns() * getNrOfRows();
                *//*Check if i is the first column in game map to the right in our Board (1,4,7 positions in game map)
     * If so, get value of all cells in same row if 4 then get value of 5 and 6*//*
                try {
                    if (isFirstColumn) {
                        setRowValue(i);
                        assignBestChoice();
                    }
                } catch (Exception e) {
                    System.out.println("Line 339 " + e.getMessage());
                    throw e;
                }

                *//*Check if first row in game map then get all cells in column below
     * if column 2, then get value of cell 5 and 8*//*
                try {
                    if (isFirstRow) {
                        setColumnValue(i);
                        assignBestChoice();
                    }
                } catch (Exception e) {
                    System.out.println("Line 351" + e.getMessage());
                    throw e;
                }
                *//*If first column and first row, check diagonal values
     * Cell 1,5,8*//*
                try {
                    if (isFirstColumn && isFirstRow) {
                        setRightDiagonal(i);
                        assignBestChoice();
                    }
                } catch (Exception e) {
                    System.out.println("Line 362: " + e.getMessage());
                    throw e;
                }
                *//*If only last column in first row, check backward diagonal values
     * Cell 3,5,7*//*
                try {
                    if (isLastColumn && isFirstRow) {
                        setLeftDiagonal(i);
                        assignBestChoice();
                    }
                } catch (Exception e) {
                    System.out.println("Line 373: " + e.getMessage());
                    throw e;
                }
            }
            if (getEmptyCellsCount() == 0) {
                setGameFinished(true);
            }
        } catch (Exception e) {
            System.out.println("LIne 381: " + e.getMessage());
            throw e;
        }
    }*/

    /*Returns best possible location in array*/
    protected void assignBestChoice() throws Exception {

        Player human = getPlayers().get(1);
        Player ai = getPlayers().get(0);
        /*Nr of occurrence*/
        int humanTimes = 0;
        int aiTimes = 0;

        int emptyCellIndex = -1;
        /*Nr of empty cells in array*/
        int emptyTimes = 0;
        int arrayLength = getSingleDirection().size();

        Cell cell = null;
        /*Loop throw values of a single row/column/diagonal */
        /*and list empty/taken cells*/
        try {
            for (int i = 0; i < arrayLength; i++) {
                cell = getSingleDirection().get(i);
                if (cell.getContent().equals(human.getSymbol())) {
                    humanTimes++;
                } else if (cell.getContent().equals(ai.getSymbol())) {
                    aiTimes++;
                } else {
                    emptyCellIndex = cell.getCellIndex();
                    emptyTimes++;
                    setEmptyCellsCount(cell.getCellIndex());
                }
            }
        } catch (Exception e) {
            System.out.println("Line 426: " + e.getMessage());
        }
        if (emptyTimes > 0) {
            try {
                /*Return -1 if first or last cells in array are taken*/
                int FirstOrLast = getFirstOrLastIndex();

                /*Return -1 if middle cell in array is taken, else returns index of cell*/
                int middleIndex = getMiddleIndex();

                /*Priority for corner cells then middle else other cells*/
                emptyCellIndex = FirstOrLast >= 0 ? FirstOrLast : middleIndex >= 0 ? middleIndex : emptyCellIndex;

                cell = getCellCoordinates().get(emptyCellIndex);
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
                    cell.setChanceRank(32 + cell.getChanceRank());
                }
                /*If only opposite player has cells, take empty cell to block opponent*/
                else if (humanTimes > 0 && aiTimes == 0) {
                    cell.setChanceRank(16 + cell.getChanceRank());
                } else {
                    /*Random array index*/
                    cell = randomEmptyCell();
                    cell.setChanceRank(4 + cell.getChanceRank());
                }
            }
        }
    }

    protected int getFirstOrLastIndex() {
        int emptyCellIndex = -1;
        int arrayLength = getSingleDirection().size();
        //Check if first cell in current row is not taken then set index to it.
        if (getSingleDirection().get(0).isNotTaken()) {
            try {
                emptyCellIndex = getSingleDirection().get(0).getCellIndex();
            } catch (Exception e) {
                System.out.println("Line 443: " + e.getMessage());
            }
        }
        //Else check if last item is not taken
        else if (getSingleDirection().get(arrayLength - 1).isNotTaken()) {
            try {
                emptyCellIndex = getSingleDirection().get(arrayLength - 1).getCellIndex();
            } catch (Exception e) {
                System.out.println("Line 451: " + e.getMessage());
            }
        }
        return emptyCellIndex;
    }

    protected int getMiddleIndex() {
        int emptyCellIndex = -1;
        int arrayLength = getSingleDirection().size();
        /*If row is empty take middle cell*/
        if (arrayLength % 2 != 0) {
            try {
                /*Select middle of array*/
                int arrayMiddle = arrayLength / 2;
                /*If middle is empty*/
                if (getSingleDirection().get(arrayMiddle).isNotTaken()) {
                    emptyCellIndex = getSingleDirection().get(arrayMiddle).getCellIndex();
                }
            } catch (Exception e) {
                System.out.println("Line 515: " + e.getMessage());
            }
        }
        return emptyCellIndex;
    }

    /**
     * Return a random available cell
     *
     * @return
     */
    protected Cell randomEmptyCell() {
        /*Random array index*/
        Random r = new Random();
        Cell cell;
        int rndIndex = r.nextInt(getEmptyCellsIndex().size());
        try {
            int cellIndex = getEmptyCellsIndex().get(rndIndex);
            cell = getCellCoordinates().get(cellIndex);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
        return cell;
    }
}