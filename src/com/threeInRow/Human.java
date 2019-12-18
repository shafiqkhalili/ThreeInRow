package com.threeInRow;

import java.util.ArrayList;
import java.util.Scanner;

public class Human extends Player {
    public Human(String name, String symbol) {

        super(name, symbol);
        super.isAi = false;
    }

    protected int getInput() {
        Scanner sc = new Scanner(System.in);
        int cell = -1;
        while (cell == -1) {
            try {
                System.out.println("Select an available cell number");
                System.out.println("Or type 'Exit' to finish game!");
                String input = sc.nextLine();
                if (input.equalsIgnoreCase("Exit")) {
                    System.exit(0);
                }
                cell = Integer.parseInt(input);
                cell = Math.abs(cell);
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
            }
        }
        return cell;
    }
    @Override
    protected Cell nextMove(Board board){
        ArrayList<Cell> cellCoordinates = board.getCellCoordinates();
        boolean cellNotSelected = true;
        Cell selectedCell = null;

        while (cellNotSelected) {
            int cellIndex = -1;
            while (cellIndex == -1) {
                try {
                    cellIndex = getInput();
                } catch (NumberFormatException e) {
                    System.out.println(e.getMessage());
                }
            }
            /*Subtract one as index begins in 0*//*
                    cellIndex--;*/
            try {
                selectedCell = cellCoordinates.get(cellIndex);
                if (!selectedCell.isTaken()) {
                    cellNotSelected = false;
                } else {
                    System.out.println("Cell already selected. Select another cellIndex!");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return selectedCell;
    }
}
