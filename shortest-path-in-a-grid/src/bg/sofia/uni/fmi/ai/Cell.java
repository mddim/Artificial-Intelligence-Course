package bg.sofia.uni.fmi.ai;

public class Cell {

    private int row;
    private int column;
    Cell parent;

    public Cell(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public Cell(int row, int column, Cell parent) {
        this.row = row;
        this.column = column;
        this.parent = parent;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public Cell getParent() {
        return parent;
    }

    public void setParent(Cell parent) {
        this.parent = parent;
    }

    public void printCell() {
        System.out.println("(" + this.row + ", " + this.column + ")");
    }

    public String toString() {
        return "(" + this.row + ", " + this.column + ")";
    }

    public boolean equals(Cell other) {
        return this.row == other.getRow() && this.column == other.getColumn();
    }
}
