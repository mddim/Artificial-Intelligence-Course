package bg.sofia.uni.fmi.ai;

import java.util.LinkedList;
import java.util.List;

class AiMove {
    AiMove() {};
    AiMove(int score) {
        this.score = score;
    }
    int x;
    int y;
    int score;
}

public class AI {

    public Player AI_PLAYER;
    public Player HUMAN_PLAYER;

    AI(Player AI_PLAYER, Player HUMAN_PLAYER) {
        this.AI_PLAYER = AI_PLAYER;
        this.HUMAN_PLAYER = HUMAN_PLAYER;
    }

    AiMove getBestMove(Board board, Player player, int alpha, int beta) {

        char res = board.checkVictory();
        if (res == AI_PLAYER.getSign()) {
            return new AiMove(10);
        } else if (res == HUMAN_PLAYER.getSign()) {
            return new AiMove(-10);
        } else if (res == 't') {
            return new AiMove(0);
        }

        List<AiMove> moves = new LinkedList<>();

        for (int x = 0; x < board.size; x++) {
            for (int y = 0; y < board.size; y++) {
                if (board.getVal(x, y) == Board.NO_VAL) {
                    AiMove move = new AiMove();
                    move.x = x;
                    move.y = y;

                    board.setVal(x, y, player.getSign());
                    if (player == AI_PLAYER) {
                        move.score = getBestMove(board, HUMAN_PLAYER, alpha, beta).score;
                        if (move.score > alpha) {
                            alpha = move.score;
                            if (alpha >= beta) {
                                moves.add(move);
                                board.setVal(x, y, Board.NO_VAL);
                                break;
                            }
                        }

                    } else {
                        move.score = getBestMove(board, AI_PLAYER, alpha, beta).score;
                        if (move.score < beta) {
                            beta = move.score;
                            if (alpha >= beta) {
                                moves.add(move);
                                board.setVal(x, y, Board.NO_VAL);
                                break;
                            }
                        }
                    }
                    board.setVal(x, y, Board.NO_VAL);
                    moves.add(move);
                }
            }
        }

        int bestMove = 0;
        if (player == AI_PLAYER) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < moves.size(); i++) {
                if (moves.get(i).score > bestScore) {
                    bestMove = i;
                    bestScore = moves.get(i).score;
                }
            }
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < moves.size(); i++) {
                if (moves.get(i).score < bestScore) {
                    bestMove = i;
                    bestScore = moves.get(i).score;
                }
            }
        }
        return moves.get(bestMove);
    }

    public void performMove(Board board) {
        AiMove bestMove = getBestMove(board, AI_PLAYER, Integer.MIN_VALUE, Integer.MAX_VALUE);
        board.setVal(bestMove.x, bestMove.y, AI_PLAYER.getSign());
    }
}
