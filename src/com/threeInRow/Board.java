package com.threeInRow;

import java.util.ArrayList;

public class Board {
    /**
     * Info about all cells in board
     */
    private ArrayList<Cell> cellCoordinates;
    private int nrOfRows;
    private int nrOfColumns;
    private boolean isGameFinished;
    private boolean isWinnerSelected;
    private int remainedEmptyCells;
    /**
     * Local variable to check value of cells in single row and column or diagonal
     */
    private ArrayList<Cell> sameCoordinate;
    private ArrayList<Player> players;

    public Board(int boardSize, ArrayList<Player> players) {
        this.remainedEmptyCells = boardSize * boardSize;
        this.nrOfRows = boardSize;
        this.nrOfColumns = boardSize;
        this.isGameFinished = false;
        this.isWinnerSelected = false;
        this.cellCoordinates = new ArrayList<>();
        this.players = players;
        initializeCells();
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    protected ArrayList<Cell> getCellCoordinates() {
        return cellCoordinates;
    }

    protected int getRemainedEmptyCells() {
        return remainedEmptyCells;
    }

    protected void setRemainedEmptyCells(int remainedEmptyCells) {
        this.remainedEmptyCells = remainedEmptyCells;
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
            }
        } catch (Exception e) {
            System.out.println("Line 82: " + e.getMessage());
        }
    }

    protected ArrayList<Cell> getSameCoordinate() {
        return sameCoordinate;
    }

    /**
     * Visualizes our cell values, empty cell show index of cell instead of its value
     */
    protected void drawBoard() {
        try {
            for (int i = 0; i < getCellCoordinates().size(); i++) {
                Cell cell = getCellCoordinates().get(i);
                /*Find last column on game board to know next line, (3,6 and 9)*/
                boolean isLastRow = i + getNrOfColumns() >= getNrOfColumns() * getNrOfRows();
                boolean isLastColumn = (i + 1) % getNrOfColumns() == 0;
                /*if last column result is 0 */
                int columnBreak = (i + 1) % getNrOfColumns();
                /*For aligning purpose only*/
                if (getNrOfColumns() > 3 && cell.getContent().length() == 1) {
                    System.out.print("  " + cell.getContent() + "  ");
                } else {
                    System.out.print(" " + cell.getContent() + " ");
                }
                /*If not last column*/
                if (columnBreak > 0) {
                    System.out.print(" | ");
                }
                /*If not first row*/
                if (columnBreak == 0 && i > 0 && !isLastRow) {
                    /*Line below every row*/
                    System.out.println();
                    for (int j = 0; j < getNrOfColumns(); j++) {
                        int cellLength = cell.getContent().length();
                        cellLength = (cellLength > 1 && getNrOfColumns() > 4) ? cellLength : cellLength + 1;
                        for (int k = 0; k < cellLength; k++) {
                            System.out.print("- ");
                        }
                        if (j < getNrOfColumns() - 1) {
                            System.out.print("+ ");
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
     */
    protected void checkWinner() throws Exception {
        try {
            for (int i = 0; i < getCellCoordinates().size(); i++) {
                boolean isLastColumn = (i + 1) % getNrOfColumns() == 0;
                boolean isFirstColumn = i % getNrOfColumns() == 0;
                boolean isFirstRow = i < getNrOfColumns();
                boolean isLastRow = i + getNrOfColumns() >= getNrOfColumns() * getNrOfRows();
                /*Check if i is the first column in game map to the right in our Board (1,4,7 positions in game map)
                 * If so, get value of all cells in same row if 4 then get value of 5 and 6*/
                try {
                    if (isFirstColumn) {
                        setRowValue(i);
                        if (checkIfCoordinatesAreSame()) {
                            setGameFinished(true);
                            setWinnerSelected(true);
                            break;
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Line 161: " + e.getMessage());
                    throw e;
                }

                /*Check if first row in game map then get all cells in column below
                 * if column 2, then get value of cell 5 and 8*/
                try {
                    if (isFirstRow) {
                        setColumnValue(i);
                        if (checkIfCoordinatesAreSame()) {
                            setGameFinished(true);
                            setWinnerSelected(true);
                            break;
                        }
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
                        if (checkIfCoordinatesAreSame()) {
                            setGameFinished(true);
                            setWinnerSelected(true);
                            break;
                        }
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
                        if (checkIfCoordinatesAreSame()) {
                            setGameFinished(true);
                            setWinnerSelected(true);
                            break;
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Line 297: " + e.getMessage());
                    throw e;
                }
            }
            if (getRemainedEmptyCells() == 0) {
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
    protected boolean checkIfCoordinatesAreSame() throws Exception {
        boolean result = true;
        Cell firstCell = getSameCoordinate().get(0);

        /*Start control from second item*/
        try {
            for (int i = 1; i < getSameCoordinate().size(); i++) {
                /*Check again previous cell if not same, exit loop.*/
                if (getSameCoordinate().get(i).getContent() != firstCell.getContent()) {
                    result = false;
                    break;
                }
                firstCell = getSameCoordinate().get(i);
            }
        } catch (Exception e) {
            System.out.println("Line 241: " + e.getMessage());
            throw e;
        }
        return result;
    }

    /**
     * Sets all cells value in given row into sameCoordinate Array
     *
     * @param i
     * @throws Exception
     */
    protected void setRowValue(int i) throws Exception {
        try {
            this.sameCoordinate = new ArrayList<>();
            Cell cell;
            for (int j = 0; j < getNrOfColumns(); j++) {
                cell = getCellCoordinates().get(i);
                this.sameCoordinate.add(cell);
                i++;
            }
        } catch (Exception e) {
            System.out.println("Line 263 " + e.getMessage());
            throw e;
        }
    }

    protected void setColumnValue(int i) throws Exception {
        try {
            this.sameCoordinate = new ArrayList<>();
            Cell cell;
            for (int j = 0; j < getNrOfRows(); j++) {
                cell = getCellCoordinates().get(i);
                this.sameCoordinate.add(cell);
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
            this.sameCoordinate = new ArrayList<>();
            Cell cell;
            for (int j = 0; j < getNrOfRows(); j++) {
                cell = getCellCoordinates().get(i);
                this.sameCoordinate.add(cell);
                i += getNrOfColumns() + 1;
            }
        } catch (Exception e) {
            System.out.println("Line 293: " + e.getMessage());
            throw e;
        }
    }

    protected void setLeftDiagonal(int i) throws Exception {
        try {
            this.sameCoordinate = new ArrayList<>();
            Cell cell;
            for (int j = 0; j < getNrOfRows(); j++) {
                cell = getCellCoordinates().get(i);
                this.sameCoordinate.add(cell);
                i += getNrOfColumns() - 1;
            }
        } catch (Exception e) {
            System.out.println("Line 314: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Loops throw all cells and find the winner
     */
    public int checkBestMove() throws Exception {
        /*Temporary variable to compare cell's chance*/
        int bestChance = 0;
        int cellIndex = -1;
        try {
            for (int i = 0; i < getCellCoordinates().size(); i++) {
                /*Reset cell winChance*/
                getCellCoordinates().get(i).setChanceRank(0);
            }
            for (int i = 0; i < getCellCoordinates().size(); i++) {
                boolean isLastColumn = (i + 1) % getNrOfColumns() == 0;
                boolean isFirstColumn = i % getNrOfColumns() == 0;
                boolean isFirstRow = i < getNrOfColumns();
                boolean isLastRow = i + getNrOfColumns() >= getNrOfColumns() * getNrOfRows();
                /*Check if i is the first column in game map to the right in our Board (1,4,7 positions in game map)
                 * If so, get value of all cells in same row if 4 then get value of 5 and 6*/
                try {
                    if (isFirstColumn) {
                        setRowValue(i);
                        getBestChoice();
                    }
                } catch (Exception e) {
                    System.out.println("Line 339 " + e.getMessage());
                    throw e;
                }

                /*Check if first row in game map then get all cells in column below
                 * if column 2, then get value of cell 5 and 8*/
                try {
                    if (isFirstRow) {
                        setColumnValue(i);
                        getBestChoice();
                    }
                } catch (Exception e) {
                    System.out.println("Line 351" + e.getMessage());
                    throw e;
                }
                /*If first column and first row, check diagonal values
                 * Cell 1,5,8*/
                try {
                    if (isFirstColumn && isFirstRow) {
                        setRightDiagonal(i);
                        getBestChoice();
                    }
                } catch (Exception e) {
                    System.out.println("Line 362: " + e.getMessage());
                    throw e;
                }
                /*If only last column in first row, check backward diagonal values
                 * Cell 3,5,7*/
                try {
                    if (isLastColumn && isFirstRow) {
                        setLeftDiagonal(i);
                        getBestChoice();
                    }
                } catch (Exception e) {
                    System.out.println("Line 373: " + e.getMessage());
                    throw e;
                }
            }
            if (getRemainedEmptyCells() == 0) {
                setGameFinished(true);
            }
        } catch (Exception e) {
            System.out.println("LIne 381: " + e.getMessage());
            throw e;
        }
        try {
            for (int i = 0; i < getCellCoordinates().size(); i++) {
                Cell cell = getCellCoordinates().get(i);
                /*Finding the the best choice cell, highest is best*/
                if (cell.getChanceRank() > bestChance) {
                    cellIndex = cell.getCellIndex();
                    bestChance = cell.getChanceRank();
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return cellIndex;
    }

    /*Returns best possible location in array*/
    protected void getBestChoice() throws Exception {
        ArrayList<String> A = new ArrayList<>();
        ArrayList<String> B = new ArrayList<>();
        /*Key-Value pair of symbol and number of their occurrence*/
        //Map<String, Integer> symbols = new HashMap<>();
        Player human = getPlayers().get(1);
        Player ai = getPlayers().get(0);
        /*Nr of occurrence*/
        int humanTimes = 0;
        int aiTimes = 0;

        int emptyCellIndex = -1;
        /*Nr of empty cells in array*/
        int emptyTimes = 0;
        int arrayLength = getSameCoordinate().size();
        try {
            Cell cell = null;
            /*Loop throw values of a single row/column/diagonal */
            /*and list empty/taken cells*/
            try {
                for (int i = 0; i < arrayLength; i++) {
                    cell = getSameCoordinate().get(i);
                    if (cell.getContent().equals(human.getSymbol())) {
                        humanTimes++;
                    } else if (cell.getContent().equals(ai.getSymbol())) {
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
                    int FirstOrLast = getFirstOrLastIndex();
                    emptyCellIndex = FirstOrLast >= 0 ? FirstOrLast : emptyCellIndex;
                    if (emptyCellIndex >= 0) {
                        cell = getCellCoordinates().get(emptyCellIndex);
                    }
                } catch (Exception e) {
                    System.out.println("Line 435: " + e.getMessage());
                }

                //Current cell chance to add with new in case if cell is in crossroad
                int currentChance = cell.getChanceRank();
                /*If Ai has only one cell left to win*/
                if (aiTimes == (arrayLength - 1) && emptyTimes > 0) {
                    //highest rank
                    cell.setChanceRank(256 + currentChance);
                }
                //If opposite player has one move left to win, block the opposite player
                else if (humanTimes == (arrayLength - 1) && emptyTimes > 0) {
                    cell.setChanceRank(128 + currentChance);
                }
                /*Block opponent*/
                else {
                    /*Return -1 if middle cell in array is taken, else returns index of cell*/
                    int middleIndex = getMiddleIndex();
                    /*If row is empty take middle cell*/
                    if (middleIndex >= 0) {
                        try {
                            cell = getCellCoordinates().get(middleIndex);
                            cell.setChanceRank(64 + currentChance);

                        } catch (Exception e) {
                            System.out.println("Line 452: " + e.getMessage());
                        }
                    }/*If only opposite player has cells, take empty cell to block opponent*/ else if (humanTimes > 0 && aiTimes == 0 && emptyTimes > 0) {
                        cell.setChanceRank(32 + currentChance);
                    }
                    /*If only Ai has cells*/
                    else if (aiTimes > 0 && humanTimes == 0) {
                        cell.setChanceRank(16 + currentChance);
                    } else {
                        cell.setChanceRank(8 + currentChance);
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Line 466: " + e.getMessage());
        }
    }

    protected int getFirstOrLastIndex() {
        int emptyCellIndex = -1;
        int arrayLength = getSameCoordinate().size();
        //Check if first cell in current row is not taken then set index to it.
        if (getSameCoordinate().get(0).isNotTaken()) {
            try {
                emptyCellIndex = getSameCoordinate().get(0).getCellIndex();
            } catch (Exception e) {
                System.out.println("Line 443: " + e.getMessage());
            }
        }
        //Else check if last item is not taken
        else if (getSameCoordinate().get(arrayLength - 1).isNotTaken()) {
            try {
                emptyCellIndex = getSameCoordinate().get(arrayLength - 1).getCellIndex();
            } catch (Exception e) {
                System.out.println("Line 451: " + e.getMessage());
            }
        }
        return emptyCellIndex;
    }

    protected int getMiddleIndex() {
        int emptyCellIndex = -1;
        int arrayLength = getSameCoordinate().size();
        /*If row is empty take middle cell*/
        if (arrayLength % 2 != 0) {
            try {
                /*Select middle of array*/
                int arrayMiddle = arrayLength / 2;
                /*If middle is empty*/
                if (getSameCoordinate().get(arrayMiddle).isNotTaken()) {
                    emptyCellIndex = getSameCoordinate().get(arrayMiddle).getCellIndex();
                }
            } catch (Exception e) {
                System.out.println("Line 515: " + e.getMessage());
            }
        }
        return emptyCellIndex;
    }
}