package com.threeInRow;

import java.util.ArrayList;
import java.util.Scanner;

public class ThreeInOne {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opt = 0;
        while (opt == 0) {
            System.out.println("Select an option:");
            System.out.println("1. To start new game");
            System.out.println("2. To close game");
            try {
                opt = Math.abs(sc.nextInt());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            switch (opt) {
                case 1:
                    initialize();
                    break;
                case 2:
                    System.exit(0);
                    break;
            }
        }
    }

    public static void initialize() {
        Scanner sc = new Scanner(System.in);

        ArrayList<Player> players = new ArrayList<>();

        int nrOfColumns = 0;
        while (nrOfColumns == 0) {
            System.out.println("Select nr of columns, select 3,4 or 5 ");
            try {
                nrOfColumns = Integer.parseInt(sc.nextLine());
                nrOfColumns = Math.abs(nrOfColumns);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        int nrOfPlayers = 0;
        while (nrOfPlayers == 0) {
            try {
                System.out.println("Write nr of players in numbers, 2 or 3 players");
                nrOfPlayers = Integer.parseInt(sc.nextLine());
                nrOfPlayers = Math.abs(nrOfPlayers);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        Map map = new Map(nrOfColumns, nrOfColumns);

        for (int i = 1; i <= nrOfPlayers; i++) {
            Scanner scn = new Scanner(System.in);
            String playerName = "";
            while (playerName.equalsIgnoreCase("")) {
                try {
                    System.out.println("Select player " + i + " name");
                    playerName = scn.nextLine().toUpperCase().trim();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            String symbol = "";
            while (symbol.equalsIgnoreCase("")) {
                try {
                    System.out.println("Select player " + i + " symbol (Only one character)");
                    symbol = scn.nextLine().toUpperCase().trim().substring(0, 1);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            Player pl = new Player(playerName, symbol);
            players.add(pl);
        }
        startGame(map,players);
    }


    public static void startGame(Map map, ArrayList<Player> players) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Cell> cellCoordinates = map.getCellCoordinates();
        int currentPlayer = 0;
        Player player = players.get(currentPlayer);
        while (!map.isGameFinished()) {
            boolean cellNotSelected = true;
            map.drawMap();
            System.out.println(player.getName() + " plays!");
            while (cellNotSelected) {
                int cell = 0;
                while (cell == 0) {
                    System.out.println("Select a cell number");
                    try {
                        cell = Math.abs(sc.nextInt());
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                /*Subtract one as index begins in 0*/
                cell--;
                try {
                    Cell selectedCell = cellCoordinates.get(cell);
                    if (!selectedCell.isTaken()) {
                        /*Set current players symbol as cell value*/
                        selectedCell.setValue(player.getSymbol());
                        map.setEmptyCells(map.getEmptyCells() - 1);
                        selectedCell.setTaken(true);
                        cellNotSelected = false;
                    } else {
                        System.out.println("Cell already selected. Select another cell!");
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                try {
                    map.checkWinner();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                if (map.isGameFinished() && map.isWinnerSelected()) {
                    System.out.println("Game finished!");
                    System.out.println(player.getName().toUpperCase() +
                            "(" + player.getSymbol() + ")" + " WON in " + player.getNrOfMoves() + " moves.");
                }
            }
            if (!map.isGameFinished()) {
                player.setNrOfMoves(player.getNrOfMoves() + 1);
                /*Change players on each turn, works even if more then one player*/
                for (int j = 0; j < players.size(); j++) {
                    if (currentPlayer == j) {
                        currentPlayer = j + 1 == players.size() ? 0 : j + 1;
                        break;
                    }
                }
                player = players.get(currentPlayer);
            }
        }
        if (!map.isWinnerSelected()) {
            System.out.println("DRAW !!!");
        }
        map.drawMap();
    }
}
