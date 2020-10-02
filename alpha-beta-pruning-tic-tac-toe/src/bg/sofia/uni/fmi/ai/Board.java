package bg.sofia.uni.fmi.ai;

public class Board {
    public static final char X_VAL = 'x';
    public static final char O_VAL = 'o';
    public static final char NO_VAL = ' ';
    int size;
    char[][] board;

    public Board() {
        this.size = 3;
        this.board = new char[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = ' ';
            }
        }
    }

    void printBoard() {
        int bigBoardSize = 11;
        char[][] bigBoard = new char[bigBoardSize][bigBoardSize];
        for (int i = 0; i < bigBoardSize; i++) {
            for (int j = 3; j < bigBoardSize; j += 4) {
                bigBoard[j][i] = '_';
            }
        }
        for (int i = 0; i < bigBoardSize; i++) {
            for (int j = 3; j < bigBoardSize; j += 4) {
                bigBoard[i][j] = '|';
            }
        }
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (board[x][y] == X_VAL) {
                    int actualX = x * 5 - x;
                    int actualY = y * 5 - y;

                    bigBoard[actualX][actualY] = '#';
                    bigBoard[actualX][actualY + 2] = '#';
                    bigBoard[actualX + 1][actualY + 1] = '#';
                    bigBoard[actualX + 2][actualY] = '#';
                    bigBoard[actualX + 2][actualY + 2] = '#';
                }
                if (board[x][y] == O_VAL) {
                    int actualX = x * 5 - x;
                    int actualY = y * 5 - y;

                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 3; j++) {
                            if (i == 1 && j == 1) { }
                            else {
                                bigBoard[actualX + i][actualY + j] = '#';
                            }
                        }
                    }
                }
            }
        }
        for (int i = 0; i < bigBoardSize; i++) {
            for (int j = 0; j < bigBoardSize; j++) {
                System.out.print(bigBoard[i][j]);
            }
            System.out.println();
        }
    }

    void drawXOnPosition(int x, int y) {
        board[x][y] = X_VAL;
    }

    void drawOOnPosition(int x, int y) {
        board[x][y] = O_VAL;
    }

    void clear() {
        this.board = new char[size][size];
    }

    char getVal(int x, int y) {
        return board[x][y];
    }

    void setVal(int x, int y, char c) {
        board[x][y] = c;
    }

    char checkVictory() {
        boolean victory;
        char c;
        //check the rows
        for (int y = 0; y < size; y++) {
            c = getVal(0, y);
            if (c != NO_VAL) {
                victory = true;
                for (int x = 0; x < size; x++) {
                    if (getVal(x, y) != c) {
                        victory = false;
                        break;
                    }
                }
                if (victory) {
                    return c;
                }
            }
        }

        //check the columns
        for (int x = 0; x < size; x++) {
            c = getVal(x, 0);
            if (c != NO_VAL) {
                victory = true;
                for (int y = 0; y < size; y++) {
                    if (getVal(x, y) != c) {
                        victory = false;
                        break;
                    }
                }
                if (victory) {
                    return c;
                }
            }
        }

        //check top left diagonal
        c = getVal(0, 0);
        if (c != NO_VAL) {
            victory = true;
            for (int xy = 0; xy < size; xy++) {
                if(getVal(xy, xy) != c) {
                    victory = false;
                    break;
                }
            }
            if (victory) {
                return c;
            }
        }

        //check top right diagonal
        c = getVal(size - 1, 0);
        if (c != NO_VAL) {
            victory = true;
            for (int xy = 0; xy < size; xy++) {
                if (getVal(size - xy - 1, xy) != c) {
                    victory = false;
                    break;
                }
            }
            if (victory) {
                return c;
            }
        }

        //check for end game
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (getVal(x, y) == NO_VAL) {
                    return NO_VAL;
                }
            }
        }

        //board - filled => tie
        return 't';
    }
}
