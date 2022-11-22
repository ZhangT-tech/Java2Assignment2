

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static ServerSocket serverSocket; // socket to listen
    private static final int Play_1 = 1; // Player1 - marked as 1 -- Circle
    private static final int Play_2 = 2; // Player2 - marked as 2 -- Line

    private final int EMPTY = 0; // Not occupied
    private static PrintWriter printWriter; // write text to output stream

    private static BufferedReader bufferedReader; // read text from input stream

    private static ExecutorService threads; // Method to control termination and control synchronous

    private static boolean firstPlayerFound; // Already have first player?

    private static void startNewGame() { // Here we can add a Player Selection option
        Player p1 = getPlayer(); // Get player 1
        Player p2 = getPlayer(); // Get Player 2
        threads.execute(new ServerController(p1, p2)); // Start a game
    }
    private static Player getPlayer() {
        while (true) {
            try {
                Socket socket = serverSocket.accept(); // Accept Client Connection
                bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream())); // read from input stream
                printWriter = new PrintWriter((socket.getOutputStream()), true); // write to output stream
                Player player = new Player(socket, bufferedReader.readLine(), getPlayerMark());
                firstPlayerFound = !firstPlayerFound;
                System.out.println(player.getName() + " connected.");
                return player;
            } 
            catch (IOException e) {
                System.out.println("Unable to get a player");
            }
        }
    }
    private static int getPlayerMark() {
        if (!firstPlayerFound) {
            return Play_1;
        }
        else return Play_2;
    }


    public static void main(String[] args) throws IOException {
        try {
            firstPlayerFound = false;
            serverSocket = new ServerSocket(1234);
            threads = Executors.newFixedThreadPool(5);
            System.out.println("...Server is running...");
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        try {
            while (true) {
                startNewGame();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            bufferedReader.close(); // read from input stream
            printWriter.close(); // write to output stream
        }
    }
}