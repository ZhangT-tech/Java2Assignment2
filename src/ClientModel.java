
public class ClientModel {

    private Board board;
    private Player current;

    public ClientModel() 
    {
        this.board = new Board();
    }
    public void move() {
        current.makeMove();
    }
    public boolean isBlank(int i, int j)
    {
        return board.isAvailable(i, j);
    }
    public void addMark(int row, int col, int mark)
    {
        board.addMark(row, col, mark);
    }
    public boolean attemptMove(int i, int j)
    {
        if (isBlank(i, j))
        {
            addMark(i, j, current.getMark());
            current.setHasPlayed(true);
            return true;
        } 
        return false;
    }

    public int[][] getBoardArr()
    { 
        return board.getBoard(); 
    }
    public Board getBoard() { return board; }
    public void setBoard(Board board) { this.board = board; }
    public Player getCurrent() { return current; }
    public void setCurrent(Player current) { this.current = current; }
}