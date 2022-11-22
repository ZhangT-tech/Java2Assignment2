import java.io.Serializable;

public class Board implements Serializable {
    private static final long serialVersionUID = -4309518207161622889L;
    private int[][] chessBoard;
    private int markCount;
    private final int EMPTY = 0;

    public Board() {
        markCount = 0;
        chessBoard = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                chessBoard[i][j] = EMPTY;
            }
        }
    }

    public boolean isAvailable(int i, int j) {
        return chessBoard[i][j] == EMPTY;
    }

    public boolean isFull() {
        return markCount >= 9;
    }


    public boolean isWon() {
        return isHorizontalWin() || isVerticalWin() || isDiagonalWin();
    }

    public boolean hasEnded() {
        return isWon() || isFull();
    }

    public void addMark(int row, int col, int mark) {
        chessBoard[row][col] = mark;
        //drawChess(chessBoard);
        markCount++;
    }

    private boolean isHorizontalWin() {
        for (int[] ints : chessBoard) {
            if (ints[0] == ints[1] && ints[1] == ints[2] && ints[2] != EMPTY) {
                return true;
            }
        }
        return false;
    }

    private boolean isVerticalWin() {
        for (int i = 0; i < chessBoard[0].length; i++)
        {
            if (chessBoard[0][i] == chessBoard[1][i] && chessBoard[1][i] == chessBoard[2][i] && chessBoard[2][i] != EMPTY)
            {
                return true;
            }   
        }
        return false;
    }
    private boolean isDiagonalWin() {
        return 
            (chessBoard[0][0] == chessBoard[1][1] && chessBoard[0][0] == chessBoard[2][2] && chessBoard[0][0] != EMPTY) ||
            (chessBoard[2][0] == chessBoard[1][1] && chessBoard[2][0] == chessBoard[0][2] && chessBoard[2][0] != EMPTY);
    }
    public int[][] getBoard() { return chessBoard; }
}