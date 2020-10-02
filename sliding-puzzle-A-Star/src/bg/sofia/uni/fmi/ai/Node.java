package bg.sofia.uni.fmi.ai;

public class Node {

    public int dimension;

    private Node parent;
    private int[][] puzzle;

    private int rowZero;
    private int columnZero;

    private int heuristicScore;
    private int numberOfMovesFromStartToCurrent;
    private int finalScore;

    public Node(int[][] puzzle, int rowZero, int columnZero, int newRow, int newCol, int numberOfMovesFromStartToCurrent, Node parent) {

        this.parent = parent;
        this.dimension = puzzle.length;
        this.puzzle = new int[dimension][];
        for (int i = 0; i < dimension; i++) {
            this.puzzle[i] = puzzle[i].clone();
        }

        swapValuesInPuzzle(this.puzzle, rowZero, columnZero, newRow, newCol);

        this.numberOfMovesFromStartToCurrent = numberOfMovesFromStartToCurrent;
        this.rowZero = newRow;
        this.columnZero = newCol;
        this.heuristicScore = Integer.MAX_VALUE;
    }

    public int calculateManhattan(int currRow, int currColumn, int goalRow, int goalColumn) {
        return Math.abs(currRow - goalRow) + Math.abs(currColumn - goalColumn);
    }

    public void calculateHeuristicScore(int[][] goalPuzzle) {
        int heuristicNumber = 0;

        for (int row = 0; row < dimension; row++) {
            for (int col = 0; col < dimension; col++) {
                int cellValue = puzzle[row][col];
                if (cellValue == 0) {
                    rowZero = row;
                    columnZero = col;
                } else {
                    int rowGoal = (cellValue - 1) / dimension;
                    int colGoal = (cellValue - 1) % dimension;
                    heuristicNumber += calculateManhattan(row, col, rowGoal, colGoal);
                }
            }
        }
        this.heuristicScore = heuristicNumber;
    }

    public void swapValuesInPuzzle(int[][] puzzle, int row, int col, int newRow, int newCol) {
        int tmp = puzzle[row][col];
        puzzle[row][col] = puzzle[newRow][newCol];
        puzzle[newRow][newCol] = tmp;
    }

    public int[][] getPuzzle() {
        return puzzle;
    }

    public Node getParent() {
        return parent;
    }

    public int getHeuristicScore() {
        return heuristicScore;
    }

    public int getNumberOfMovesFromStartToCurrent() {
        return numberOfMovesFromStartToCurrent;
    }

    public int getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(int finalScore) {
        this.finalScore = finalScore;
    }

    public int getRowZero() {
        return rowZero;
    }

    public int getColumnZero() {
        return columnZero;
    }
}
