

import java.io.*;
import java.net.SocketException;

public class ServerController implements Runnable {

    private ServerModel model;
    public ServerController(Player player1, Player player2) {
         this.model = new ServerModel();
         model.setCurrent(player1);
         model.setNext(player2);
    }        // a model for server



    @Override
    public void run() {        // run the game
        try {
            // Notify Game Start
            System.out.println("Game started between " + model.getCurrent().getName() +
                    " and " + model.getNext().getName() + ".");
            model.getCurrentPrintWriter().println("Opponent found. A new game has started!"); // Writer to output stream
            model.getNextPrintWriter().println("Opponent found. A new game has started!"); // Write to output stream
            try {
                Thread.sleep(2000);
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            try {       // Play the game
                // While the game doesn't end, keep running
                while (!model.getBoard().isFull() || !model.getBoard().isWon()) {
                    model.changePlayer();
                    // Send state Info
                    GameState gameState_send = new GameState(model.getBoard(), model.getCurrent(), model.getNext());
                    model.getCurrentOutputStream().writeObject(gameState_send);
                    model.getNextOutputStream().writeObject(gameState_send);
                    // Update state Info
                    GameState gameState_update = (GameState) model.getCurrentInputStream().readObject();

                    model.setBoard(gameState_update.getBoard());
                }
            } catch (IOException e) {
                throw new SocketException();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            // End game
            try {
                GameState gameState = new GameState(model.getBoard(), model.getCurrent(), model.getNext());
                model.getCurrentOutputStream().writeObject(gameState);
                model.getNextOutputStream().writeObject(gameState);
                System.out.println("Game ended between " + model.getNext().getName() + " and " +
                        model.getCurrent().getName() + ". " + model.getNext().getName() + " won!");
            } catch (EOFException e) {
                e.printStackTrace();
            }
        }
        catch (SocketException e) {
            System.out.println("One or more players have rage quit. Ending game between " +
                    model.getNext().getName() + " and " + model.getCurrent().getName() + ".");
            model.getCurrentPrintWriter().println("Your opponent has rage quit. Game over.");
            model.getNextPrintWriter().println("Your opponent has rage quit. Game over.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}