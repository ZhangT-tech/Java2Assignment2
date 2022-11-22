

import java.io.*;
import java.net.Socket;

public class Player implements Serializable {
    private String name;

    private final int Play_1 = 1;

    private final int Play_2 = 2;

    private final int EMPTY = 0;

    private int mark;

    private boolean hasPlayed;

    private transient PrintWriter printWriter;

    private transient BufferedReader bufferedReader;

    private transient ObjectInputStream objectInputStream;

    private transient ObjectOutputStream objectOutputStream;

    private static final long serialVersionUID = 720785984605791249L;
    public Player(Socket socket, String name, int mark) {
        try {
            this.name = name;
            this.mark = mark;
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printWriter = new PrintWriter((socket.getOutputStream()), true);
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void makeMove() {
        hasPlayed = false;
        while (!hasPlayed) {
            try {
                Thread.sleep(0);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public String getName() { return name; }
    public int getMark() { return mark; }
    public void setHasPlayed(boolean hasPlayed) { this.hasPlayed = hasPlayed; }
    public PrintWriter getPrintWriter() { return printWriter; }
    public BufferedReader getSocketIn() { return bufferedReader; }
    public ObjectInputStream getObjectInputStream() { return objectInputStream; }
    public ObjectOutputStream getObjectOutputStream() { return objectOutputStream; }
}