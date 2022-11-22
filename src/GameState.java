

import java.io.Serializable;

public class GameState implements Serializable {

    private Board chessboard;
    private Player current;

    private Player next;

    private static final long serialVersionUID = 3002053260033745936L;

    public GameState(Board chessboard, Player current, Player next)
    {
        this.chessboard = chessboard;
        this.current = current;
        this.next = next;
    }

    public boolean hasEnded() 
    {
        return chessboard.hasEnded();
    }

    public boolean isWon()
    {
        return chessboard.isWon();
    }

    public Board getBoard() { return chessboard; }
    public void setBoard(Board board) { this.chessboard = board; }
    public Player getCurrent() { return current; }

    public Player getNext() {
        return next;
    }

    public void setCurrent(Player current) { this.current = current; }

}