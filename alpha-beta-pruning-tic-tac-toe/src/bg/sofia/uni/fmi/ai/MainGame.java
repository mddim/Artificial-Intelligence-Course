package bg.sofia.uni.fmi.ai;

import java.util.Scanner;

public class MainGame {
    private Board board = new Board();
    private Player currentPlayer;
    private Player aiPlayer;
    private Player humanPlayer;
    private Scanner sc = new Scanner(System.in);
    private AI ai;

    public void run() {

        init();

        while (board.checkVictory() == Board.NO_VAL) {

            if (currentPlayer == aiPlayer) {
                System.out.println("AI move:");
                ai.performMove(board);
            } else {
                playerMove();
            }

            if (board.checkVictory() == 'x') {
                board.printBoard();
                System.out.println("The winner is \"X\"!");
                return;
            } else if (board.checkVictory() == 'o') {
                board.printBoard();
                System.out.println("The winner is \"O\"!");
                return;
            } else if (board.checkVictory() == 't') {
                board.printBoard();
                System.out.println("It's a tie!");
                return;
            } else {
                changePlayer();
            }
            board.printBoard();
            System.out.println("\n\n\n");
        }
    }

    public void init() {
        //1 -> human, 2 -> ai
        System.out.println("Are you going to play first? - y/n");
        String answer = sc.nextLine();
        if (answer.equalsIgnoreCase("y")) {
            currentPlayer = new Player(1, 'x');
            humanPlayer = currentPlayer;
            aiPlayer = new Player(2, 'o');
        } else if (answer.equalsIgnoreCase("n")) {
            currentPlayer = new Player(2, 'x');
            aiPlayer = currentPlayer;
            humanPlayer = new Player(2, 'o');
        }
        ai = new AI(aiPlayer, humanPlayer);
    }

    private void changePlayer() {
        if (currentPlayer == aiPlayer) {
            currentPlayer = humanPlayer;
        } else if (currentPlayer == humanPlayer) {
            currentPlayer = aiPlayer;
        }
    }

    private void playerMove() {
        System.out.println("Choose position: ");
        System.out.print("x = ");
        int x = sc.nextInt();
        System.out.print("y = ");
        int y = sc.nextInt();
        while (board.getVal(x, y) != Board.NO_VAL) {
            System.out.println("This place is already taken! Choose again!");
            System.out.println("Choose position: ");
            System.out.print("x = ");
            x = sc.nextInt();
            System.out.print("y = ");
            y = sc.nextInt();
        }
        board.setVal(x, y, currentPlayer.getSign());
        System.out.println("Player move:");
    }

}
