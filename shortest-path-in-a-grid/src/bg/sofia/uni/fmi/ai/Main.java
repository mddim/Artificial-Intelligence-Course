package bg.sofia.uni.fmi.ai;

public class Main {
    public static void main(String[] args) {
        int matrix[][] = {
                {1, 1, 0, 1, 1, 1},
                {1, 2, 0, 0, 1, 1},
                {1, 1, 1, 2, 0, 1},
                {1, 1, 1, 1, 1, 1},
                {1, 0, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1}
        };
        int N = matrix.length;
        Cell startCell = new Cell(0, 0);
        Cell endCell = new Cell(4, 4);
        Cell firstTeleport = new Cell(1, 1);
        Cell secondTeleport = new Cell(2, 3);

        Grid grid = new Grid(matrix, N, firstTeleport, secondTeleport);

        Cell cell = grid.shortestPath(startCell, endCell);

        if(cell != null) {
            System.out.println("Shortest Path is: ");
            int len = grid.printPath(cell) - 1;
            System.out.println();
            System.out.println("Shorthest Path length is: \n" + len);
        } else {
            System.out.println("Destination not reached.");
        }
    }
}
