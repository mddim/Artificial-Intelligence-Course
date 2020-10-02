package bg.sofia.uni.fmi.ai;

public class Player {
    private int number;
    private char sign;

    Player(int number, char sign) {
        this.number = number;
        this.sign = sign;
    }

    public int getNumber() {
        return number;
    }

    public char getSign() {
        return sign;
    }
}
