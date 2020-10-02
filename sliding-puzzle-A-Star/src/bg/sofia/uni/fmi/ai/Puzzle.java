package bg.sofia.uni.fmi.ai;

import java.util.PriorityQueue;
import java.util.Queue;

public class Puzzle {

    public int dimension = 3;

    private int[][] startPuzzle;
    private int[][] goalPuzzle;

    private int rowZero;
    private int colZero;

    private int[] moveRow = { -1, 1, 0, 0 };
    private int[] moveCol = { 0, 0, -1, 1 };

    public Puzzle(int[][] startPuzzle, int[][] goalPuzzle, int rowZero, int colZero) {
        this.startPuzzle = startPuzzle;
        this.goalPuzzle = goalPuzzle;
        this.rowZero = rowZero;
        this.colZero = colZero;
    }

    public boolean isValidCell(int row, int col) {
        return row >= 0 && row < dimension && col >= 0 && col < dimension;
    }

    public int calculateFinalScore(Node puzzle) {
        puzzle.calculateHeuristicScore(goalPuzzle);
        return puzzle.getHeuristicScore() + puzzle.getNumberOfMovesFromStartToCurrent();
    }

    public void algorithm() {
        Queue<Node> priorityQueue = new PriorityQueue<>(new FinalCostComparator());
        Node root = new Node(startPuzzle, rowZero, colZero, rowZero, colZero, 0, null);
        root.setFinalScore(calculateFinalScore(root));
        priorityQueue.add(root);

        while (!priorityQueue.isEmpty()) {
            Node min = priorityQueue.poll();
            if (min.getHeuristicScore() == 0) {
                printPath(min);
                return;
            }

            for (int i = 0; i < 4; i++) {
                if (isValidCell(min.getRowZero() + moveRow[i], min.getColumnZero() + moveCol[i])) {
                    Node child = new Node(min.getPuzzle(), min.getRowZero(), min.getColumnZero(), min.getRowZero() + moveRow[i],
                            min.getColumnZero() + moveCol[i], min.getNumberOfMovesFromStartToCurrent() + 1, min);
                    child.setFinalScore(calculateFinalScore(child));
                    priorityQueue.add(child);
                }
            }
        }
    }

    public void printPuzzle(int[][] puzzle) {
        for (int i = 0; i < puzzle.length; i++) {
            for (int j = 0; j < puzzle.length; j++) {
                System.out.print(puzzle[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void printPath(Node root) {
        if (root == null) {
            return;
        }
        printPath(root.getParent());
        printPuzzle(root.getPuzzle());
        System.out.println();
    }

    public static void main(String[] args) {

        int[][] startPuzzle = { {6, 5, 3}, {2, 4, 8}, {7, 0, 1} };
        int[][] goalPuzzle    = { {1, 2, 3}, {4, 5, 6}, {7, 8, 0} };

        int rowZero = 2, colZero = 1;

        Puzzle puzzle = new Puzzle(startPuzzle, goalPuzzle, rowZero, colZero);
        puzzle.algorithm();
    }
}
