package com.threeInRow;

import java.util.Scanner;

public class Human extends Player {
    public Human(String name, String symbol) {

        super(name, symbol);
        super.isAi = false;
    }

    @Override
    public int selectCell() {
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
}
