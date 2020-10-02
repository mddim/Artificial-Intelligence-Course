package bg.sofia.uni.fmi.ai;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Grid {
    private int size;
    private int[][] matrix;
    private Cell firstTeleport;
    private Cell secondTeleport;

    private Queue<Cell> queue;

    private static int[] moveRows = { 0, 1, 0, -1 };
    private static int[] moveColumns = { 1, 0, -1, 0 };

    private boolean[][] visited;

    public Grid (int[][] matrix, int size, Cell firstTeleport, Cell secondTeleport) {
        this.matrix = matrix;
        this.size = size;
        this.firstTeleport = firstTeleport;
        this.secondTeleport = secondTeleport;
        queue = new LinkedList<Cell>();
        visited = new boolean[size][size];
        for (boolean[] row: visited) {
            Arrays.fill(row, false);
        }
    }

    public Cell shortestPath(Cell startCell, Cell endCell) {

        queue.add(startCell);
        visited[startCell.getRow()][startCell.getColumn()] = true;

        while (!queue.isEmpty()) {

            Cell currentCell = queue.remove();

            if (currentCell.equals(endCell)) {
                return currentCell;
            }

            for (int i = 0; i < 4; i++) {
                Cell neighbourCell = new Cell(currentCell.getRow() + moveRows[i], currentCell.getColumn() + moveColumns[i], currentCell);

                //skip out of bounds location
                if (neighbourCell.getRow() < 0 || neighbourCell.getRow() >= size) continue;
                if (neighbourCell.getColumn() < 0 || neighbourCell.getColumn() >= size) continue;

                //skip visited locations or blocked cells
                if (visited[neighbourCell.getRow()][neighbourCell.getColumn()]) continue;
                if (matrix[neighbourCell.getRow()][neighbourCell.getColumn()] == 0) continue;

                //use teleportation
                if (neighbourCell.equals(firstTeleport) && matrix[firstTeleport.getRow()][firstTeleport.getColumn()] == 2) {
                    neighbourCell = secondTeleport;
                    neighbourCell.setParent(currentCell);
                    matrix[firstTeleport.getRow()][firstTeleport.getColumn()] = 0;
                    matrix[secondTeleport.getRow()][secondTeleport.getColumn()] = 0;
                }
                else if (neighbourCell.equals(secondTeleport) && matrix[secondTeleport.getRow()][secondTeleport.getColumn()] == 2) {
                    neighbourCell = firstTeleport;
                    neighbourCell.setParent(currentCell);
                    matrix[firstTeleport.getRow()][firstTeleport.getColumn()] = 0;
                    matrix[secondTeleport.getRow()][secondTeleport.getColumn()] = 0;
                }

                queue.add(neighbourCell);
                visited[neighbourCell.getRow()][neighbourCell.getColumn()] = true;

            }
        }
        return null;
    }

    public int printPath(Cell cell) {

        if(cell == null) {
            return 0;
        }
        int len = printPath(cell.parent);
        if(cell.equals(firstTeleport)) {
            System.out.print(secondTeleport + " ");
        }
        if(cell.equals(secondTeleport)) {
            System.out.print(firstTeleport + " ");
        }
        System.out.print(cell + " ");
        return len + 1;
    }
}
