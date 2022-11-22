import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
public class ServerModel {
    private Board chessboard;
    private Player current;
    private Player next;
    public ServerModel() 
    {
        this.chessboard = new Board();
    }

    public void changePlayer() {
        Player temp1 = current;
        Player temp2 = next;
        current = temp2;
        next = temp1;
    }
    public Board getBoard() { return chessboard; }
    public void setBoard(Board board) { this.chessboard = board; }
    public Player getCurrent() { return current; }
    public void setCurrent(Player current) { this.current = current; }
    public Player getNext() { return next; }
    public void setNext(Player next) { this.next = next; }
    public PrintWriter getCurrentPrintWriter() { return current.getPrintWriter(); } // write to output stream
    public PrintWriter getNextPrintWriter() { return next.getPrintWriter(); }
    public ObjectInputStream getCurrentInputStream() { return current.getObjectInputStream(); } // Serializable
    public ObjectOutputStream getCurrentOutputStream() { return current.getObjectOutputStream(); }
    public ObjectOutputStream getNextOutputStream() { return next.getObjectOutputStream(); }
}