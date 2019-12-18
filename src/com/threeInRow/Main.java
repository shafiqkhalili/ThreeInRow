package com.threeInRow;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        initialize();
    }

    public static void initialize() throws Exception {
        Scanner sc = new Scanner(System.in);
        int nrOfGames = 0;
        int nrOfPlayers = 0;
        int newGameOpt = 2;
        int boardSize = 0;
        ArrayList<Player> players = new ArrayList<>();
        while (true) {
            if (nrOfGames > 0) {
                System.out.println();
                System.out.println("Rounds played: " + (nrOfGames));
                System.out.println("Players Win/Lose ratio: ");
                System.out.println(players.get(0).getName() + ": " + players.get(0).getWinStats() + "/" + players.get(0).getLoseStats());
                System.out.println(players.get(1).getName() + ": " + players.get(1).getWinStats() + "/" + players.get(1).getLoseStats());
                System.out.println();
                System.out.println("All rounds winner: ");
                if (players.get(0).getWinStats() > players.get(1).getWinStats()) {
                    System.out.println(players.get(0).getName());
                } else if (players.get(1).getWinStats() > players.get(0).getWinStats()) {
                    System.out.println(players.get(1).getName());
                } else {
                    System.out.println("It is DRAW !");
                }
                System.out.println();

                while (true) {
                    //reset player nr of moves
                    for (Player p : players) {
                        p.setNrOfMoves(0);
                    }
                    System.out.println("Select an option:");
                    System.out.println("1. Continue playing");
                    System.out.println("2. New game");
                    System.out.println("3. Exit");

                    System.out.println();
                    try {
                        newGameOpt = Integer.parseInt(sc.nextLine());
                        newGameOpt = Math.abs(newGameOpt);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    switch (newGameOpt) {
                        case 2:
                            //Reset variables on new game
                            nrOfPlayers = 0;
                            newGameOpt = 2;
                            boardSize = 0;
                            nrOfGames = 0;
                            players = new ArrayList<>();
                            break;
                        case 3:
                            System.exit(0);
                            break;
                    }
                    //If correct value exit loop
                    if (newGameOpt > 0 && newGameOpt < 4) {
                        break;
                    }
                }
            }
            //If new game, else use old info
            if (newGameOpt == 2) {
                while (nrOfPlayers == 0) {
                    try {
                        System.out.println("Select an option:");
                        System.out.println("1. Play against AI");
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
                if (nrOfPlayers == 1) {
                    try {
                        Ai ai = new Ai();
                        players.add(ai);
                    } catch (Exception e) {
                        System.out.println("Line 73: " + e.getMessage());
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
                            System.out.println("Line 89: " + e.getMessage());
                        }
                    }
                    String symbol = "";
                    while (symbol.equalsIgnoreCase("")) {
                        try {
                            System.out.println("Select player " + i + " symbol (Not a number and only one character)");
                            symbol = sc.nextLine();
                            symbol = symbol.toUpperCase().trim().substring(0, 1);

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
                        try {
                            if (symbol.contains("0123456789")) {
                                symbol = "";
                            }
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    Human pl = new Human(playerName, symbol);
                    pl.setPlaying(true);
                    players.add(pl);
                }
                while (boardSize == 0) {
                    System.out.println("Select game difficulty / board size: ");
                    System.out.println("3. Easy");
                    System.out.println("4. Medium");
                    System.out.println("5. Difficult");
                    try {
                        boardSize = Integer.parseInt(sc.nextLine());
                        //If user put negative number
                        boardSize = Math.abs(boardSize);
                        if (boardSize < 3 || boardSize > 5) {
                            boardSize = 0;
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
            //Reset cell index
            Cell.setNrOfCells(0);
            startGame(boardSize, players);
            nrOfGames++;
        }
    }

    public static void startGame(int boardSize, ArrayList<Player> players) throws Exception {
        /*Initializes board/board*/
        Board board = new Board(boardSize, players);
        ArrayList<Cell> cellCoordinates = board.getCellCoordinates();
        //Take random player at beginning
        double rnd = Math.random();
        int currentPlayer = (int) (rnd * players.size());
        Player player = players.get(currentPlayer);
        player.setPlaying(true);
        board.drawBoard();
        Cell selectedCell = null;

        while (!board.isGameFinished()) {
            System.out.println();
            System.out.println(player.getName() + "(" + player.getSymbol() + ") plays!");
            try {
                selectedCell = player.nextMove(board);
                /*Set current players symbol as cellIndex value*/
                selectedCell.setContent(player.getSymbol());
                board.setEmptyCellsCount(board.getEmptyCellsCount() - 1);
                selectedCell.setTaken(true);
            } catch (Exception e) {
                e.getMessage();
            }
            try {
                /*Remove cell from empty cells array, as it is used now*/
                board.unsetEmptyCellIndex(selectedCell.getCellIndex());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            try {
                board.drawBoard();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            try {
                //Check winner
                board.evaluateBoard();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            player.setNrOfMoves(player.getNrOfMoves() + 1);

            if (board.isGameFinished()) {
                System.out.println();
                System.out.println("Game finished!");
                if (board.isWinnerSelected()) {
                    System.out.println(player.getName().toUpperCase() +
                            "(" + player.getSymbol() + ")" + " WON in " + player.getNrOfMoves() + " moves.");
                    player.setWinStats(player.getWinStats() + 1);
                } else if (!board.isWinnerSelected()) {
                    System.out.println("DRAW !!!");
                }
            }
            /*Switch player only if game is not finished*/
            if (!board.isGameFinished()) {
                /*Change players on each turn, works even if more than two player*/
                for (int j = 0; j < players.size(); j++) {
                    if (currentPlayer == j) {
                        currentPlayer = j + 1 == players.size() ? 0 : j + 1;
                        break;
                    }
                }
                //Change playing status of old player to false
                player.setPlaying(false);
                player = players.get(currentPlayer);
                player.setPlaying(true);
            } else if (!board.isWinnerSelected()) {
                //Set loose stats of second player as first player has already won
                player.setLoseStats(player.getLoseStats() + 1);
            }
        }
    }
}
