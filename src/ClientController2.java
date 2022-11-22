import java.net.ConnectException;
import java.io.*;
import java.awt.event.ActionEvent;

import java.net.Socket;
import javax.swing.*;

public class ClientController2 {

    private static Socket socket;

    private static PrintWriter printWriter;






    private static BufferedReader bufferedReader;




    private ObjectInputStream objectInputStream;

    private ObjectOutputStream objectOutputStream;

    private ClientView view;
    private ClientModel model;

    private boolean isLive;

    private String name;

    public ClientController2(String serverName, int portNumber) throws IOException {
        socket = new Socket(serverName, portNumber);
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        printWriter = new PrintWriter(socket.getOutputStream(), true);

        // Initialize
        view = new ClientView();
        model = new ClientModel();
        isLive = false;
        name = "";

        addButtonFunctionality();
    }

    public void communicate() throws IOException {
        // get the name of the player
        while (name.equals("") || name.isEmpty() || name == null) {
            name = JOptionPane.showInputDialog("Please enter your name:", "");
        }
        // Add player to the game
        view.setNameField(name);
        printWriter.println(name);
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectInputStream = new ObjectInputStream(socket.getInputStream());


        view.setTextArea("Waiting for opponent...");
        while (!isLive)
        {
            String response = bufferedReader.readLine();
            if (response.equals("Opponent found. A new game has started!"))
            {
                view.setTextArea(response);
                isLive = true;
            }
        }

        // Running game
        while (isLive)
        {
            GameState gameState = latestGameState();
            if (gameState == null) {
                break;
            }
            // update state
            model.setBoard(gameState.getBoard());
            model.setCurrent(gameState.getCurrent());
            view.updateButtonText(model.getBoardArr());
            view.setNameField(gameState.getCurrent().getName());
            view.setMarkField(gameState.getCurrent().getMark() == 1 ? "O" : "X");


            // Continue State check
            if (!gameState.hasEnded()) {
                processTurn(gameState);
            }
            else if (gameState.isWon()) {
                view.setTextArea(gameState.getNext().getName() + " has won!");
                isLive = false;
            }
            else {
                view.setTextArea("There was a tie!");
                isLive = false;
            }
        }

        // Close the game
        bufferedReader.close();
        printWriter.close();
        objectInputStream.close();
        objectOutputStream.close();
    }

    private void addButtonFunctionality() {
        // Add button functionality
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                final int finalI = i;
                final int finalJ = j;
                view.addListener((ActionEvent e) -> handleMove(finalI, finalJ), i, j);
            }

    }

    private void handleMove(int i, int j)
    {
        if (!model.attemptMove(i, j))
        {
            view.setTextArea("Please select a blank space.");
        }
    }

    private GameState latestGameState() {
        try {
            return (GameState)objectInputStream.readObject();
        }
        catch (StreamCorruptedException e) {
            view.setTextArea("Your opponent has rage quit. Game over.");
            isLive = false;
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void processTurn(GameState gameState) throws IOException {
        // If active player
        if (gameState.getCurrent().getName().equals(name))
        {
            playTurn(gameState);
        }
        // If non-active player
        else
        {
            view.setTextArea(gameState.getCurrent().getName() + " is making their move...");
        }
    }
    private void playTurn(GameState gameState) throws IOException {
        view.setTextArea("It's your turn to make a move");
        view.enableButtons(true);
        model.move();
        // Finish put its step
        view.enableButtons(false);
        view.updateButtonText(model.getBoardArr());
        gameState.setBoard(model.getBoard());
        gameState.setCurrent(model.getCurrent());
        // Update
        objectOutputStream.writeObject(gameState);
    }

    public static void main(String[] args) throws IOException {
        ClientController2 clientController2 = new ClientController2("127.0.0.1", 1234);
        clientController2.communicate();
    }
}