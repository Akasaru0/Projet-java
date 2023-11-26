package main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws ClassNotFoundException {
        try {
            Socket socket = new Socket("localhost", 12345);

            // Configurer les flux de communication avec le serveur
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());


            while (true) {
                // Recevoir la séquence de couleurs du serveur
                ColorSequence colorSequence = receiveColorSequence(inputStream);
                displayColorSequence(colorSequence);

                // Pause pendant 2 secondes
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                
                // Le joueur fait son essai
                ArrayList<String> playerSequence = getPlayerSequence(colorSequence.getLenght());
                sendPlayerMove(outputStream, playerSequence);

                // Recevoir le résultat du mouvement
                boolean moveResult = receivePlayerMoveResult(inputStream);
                if (!moveResult) {
                    System.out.println("Vous avez perdu. Fin du jeu.");
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static ColorSequence receiveColorSequence(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        return (ColorSequence) inputStream.readObject();
    }

    private static void displayColorSequence(ColorSequence colorSequence) {
        System.out.println("Mémorisez la séquence : " + colorSequence.getSequence());
    }

    private static ArrayList<String> getPlayerSequence(int length) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> playerSequence = new ArrayList<>();
        
        for (int i = 0; i < length; i++) {
            System.out.print("Saisissez la couleur n°" + (i + 1) + ": ");
            String color = scanner.nextLine();
            playerSequence.add(color);
        }

        return playerSequence;
    }

    private static void sendPlayerMove(ObjectOutputStream outputStream, ArrayList<String> playerMove) throws IOException {
        outputStream.writeObject(new PlayerMove(playerMove, true));
        outputStream.flush();
    }

    private static boolean receivePlayerMoveResult(ObjectInputStream inputStream) throws IOException {
        return inputStream.readBoolean();
    }
}
