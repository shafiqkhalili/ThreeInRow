package com.threeInRow;

import java.util.ArrayList;
import java.util.Scanner;

public class ThreeInOne {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        initialize();
        /*int opt = 0;
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
        }*/
    }

    public static void initialize() {
        Scanner sc = new Scanner(System.in);

        ArrayList<Player> players = new ArrayList<>();

        int boardSize = 0;
        while (boardSize == 0) {
            System.out.println("Select game difficulty: ");
            System.out.println("3. Easy");
            System.out.println("4. Medium");
            System.out.println("5. Difficult");
            try {
                boardSize = Integer.parseInt(sc.nextLine());
                boardSize = Math.abs(boardSize);
                if (boardSize < 3 || boardSize > 5) {
                    boardSize = 0;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        int nrOfPlayers = 0;
        while (nrOfPlayers == 0) {
            try {
                System.out.println("Select an option:");
                System.out.println("1. Play again AI");
                System.out.println("2. Play against another person");
                nrOfPlayers = Integer.parseInt(sc.nextLine());
                nrOfPlayers = Math.abs(nrOfPlayers);
                if (nrOfPlayers < 1 || nrOfPlayers > 2) {
                    nrOfPlayers = 0;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        for (int i = 1; i <= nrOfPlayers; i++) {
            String playerName = "";
            while (playerName.equalsIgnoreCase("")) {
                try {
                    System.out.println("Select player " + i + " name.");
                    playerName = sc.nextLine().toUpperCase().trim();
                    if (players.size() > 0) {
                        for (Player p : players
                        ) {
                            if (p.getName().equalsIgnoreCase(playerName)) {
                                System.out.println(playerName + " is already taken ");
                                playerName = "";
                            }
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            String symbol = "";
            while (symbol.equalsIgnoreCase("")) {
                try {
                    System.out.println("Select player " + i + " symbol (Only one character)");
                    symbol = sc.nextLine().toUpperCase().trim().substring(0, 1);
                    if (players.size() > 0) {
                        for (Player p : players
                        ) {
                            if (p.getSymbol().equalsIgnoreCase(symbol)) {
                                System.out.println(symbol + " already taken by " + p.getName());
                                symbol = "";
                            }
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            Human pl = new Human(playerName, symbol);
            players.add(pl);
        }

        System.out.println("Select an option:");
        System.out.println("1. Play");
        System.out.println("2. Exit");

        if (nrOfPlayers == 1) {
            Ai ai = new Ai(boardSize * boardSize);
            players.add(ai);
        }

        startGame(players, boardSize);

    }


    public static void startGame(ArrayList<Player> players, int boardSize) {
        /*Initializes board/map*/
        Map map = new Map(boardSize);

        Scanner sc = new Scanner(System.in);
        ArrayList<Cell> cellCoordinates = map.getCellCoordinates();
        int currentPlayer = (int) Math.random() * players.size();
        Player player = players.get(currentPlayer);
        while (!map.isGameFinished()) {
            boolean cellNotSelected = true;
            map.drawBoard();
            System.out.println(player.getName()+"("+player.getSymbol() + ") plays!");
            while (cellNotSelected) {
                int cell = 0;
                while (cell == 0) {
                    try {
                        cell = player.selectCell();
                    } catch (NumberFormatException e) {
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
                        map.setRemainedEmptyCells(map.getRemainedEmptyCells() - 1);
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
            }
            player.setNrOfMoves(player.getNrOfMoves() + 1);
            if (map.isGameFinished() && map.isWinnerSelected()) {
                System.out.println("Game finished!");
                System.out.println(player.getName().toUpperCase() +
                        "(" + player.getSymbol() + ")" + " WON in " + player.getNrOfMoves() + " moves.");
            }
            if (!map.isGameFinished()) {
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
        map.drawBoard();
    }
}
