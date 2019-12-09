package com.threeInRow;

import java.util.ArrayList;
import java.util.Scanner;

public class ThreeInOne {

    public static void main(String[] args) {
        initialize();
    }

    public static void initialize() {
        ArrayList<Player> players = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        System.out.println("Select nr of columns");
        int nrOfColumns = sc.nextInt();

        System.out.println("Write nr of players in numbers, max 2");
        int nrOfPlayers = sc.nextInt();

        Map map = new Map(nrOfColumns, nrOfColumns);


        for (int i = 1; i <= nrOfPlayers; i++) {
            Scanner scn = new Scanner(System.in);
            System.out.println("Select player " + i + " name");
            String playerName = scn.nextLine();
            System.out.println("Select player " + i + " symbol (Only one character)");
            String symbol = scn.nextLine().toUpperCase();
            Player pl = new Player(playerName, symbol);
            players.add(pl);
        }
        startGame(map, players);
    }

    public static void startGame(Map map, ArrayList<Player> players) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Cell> positions = map.getPositions();
        int currentPlayer = 0;

        for (int i = 0; i < map.nrOfColumns * map.nrOfRows; i++) {
            boolean waitingInput = true;
            map.drawMap();
            System.out.println(players.get(currentPlayer).name + " plays!");
            while (waitingInput) {
                System.out.println("Select a cell number");
                int cell = sc.nextInt()-1;
                Cell selectedCell = positions.get(cell);
                if (!selectedCell.isTaken()) {
                    selectedCell.setValue(players.get(currentPlayer).symbol);
                    selectedCell.setTaken(true);
                    waitingInput = false;
                } else {
                    System.out.println("Cell already selected. Select another cell!");
                }
                map.setCellNeighbours(i);
            }
            for (int j = 0; j < players.size(); j++) {
                if (currentPlayer == j) {
                    currentPlayer = j + 1 == players.size() ? 0 : j + 1;
                    break;
                }
            }
        }
        map.drawMap();

    }

    public static void checkWinner(Map map, ArrayList<Player> players) {

    }
}
