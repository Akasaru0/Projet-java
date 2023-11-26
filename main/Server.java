package main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final List<String> COLORS = Arrays.asList("red", "blue", "green", "yellow");
    private static final int PORT = 12345;

    private static final ExecutorService executorService = Executors.newFixedThreadPool(10); // Nombre maximum de joueurs simultanés
    private static final Random random = new Random();

    private static int playerCounter = 1; // Compteur pour attribuer un numéro unique à chaque joueur

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Serveur en attente de connexions...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nouveau joueur connecté: " + clientSocket.getInetAddress().getHostAddress());

                // Créer un thread distinct pour chaque joueur
                executorService.submit(() -> handleClient(clientSocket, playerCounter++));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket clientSocket, int playerNumber) {
        try (
        		ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        		ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());

        ) {
            System.out.println("Joueur " + playerNumber + " prêt à jouer!");
            ColorSequence colorSequence = generateColorSequence();
            boolean playerWon = true;
            sendColorSequence(outputStream, colorSequence);
            System.out.println("Joueur "+playerNumber+" envoie séquence : " + colorSequence.getSequence());
            
            while (true) {
            	
                PlayerMove playerMove = receivePlayerMove(inputStream);
                System.out.println("Joueur "+playerNumber+" séquence reçu : " + playerMove.getPlayerSequence());
                
                // Attendre la réponse du joueur avant de procéder à la vérification
                boolean playerMoveCorrect = checkPlayerMove(colorSequence, playerMove);
                if (playerMoveCorrect) {
                    System.out.println("Joueur " + playerNumber + " a réussi la séquence!");
                    
                    sendPlayerMoveResult(outputStream, true);
                } else {
                    System.out.println("Joueur " + playerNumber + " a perdu!");
                 // Informer le joueur qu'il a perdu en envoyant le résultat
                    sendPlayerMoveResult(outputStream, false);
                    playerWon = false;
                    break;
                }
                ColorSequence colorSequence2 = addNewColorSequence(colorSequence);
                System.out.println("Joueur "+playerNumber+" envoie séquence : " + colorSequence.getSequence());
                sendColorSequence(outputStream, colorSequence2);
                colorSequence = colorSequence2;
                
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void sendPlayerMoveResult(ObjectOutputStream outputStream, boolean moveResult) throws IOException {
        outputStream.writeBoolean(moveResult);
        outputStream.flush();
    }
    private static void sendColorSequence(ObjectOutputStream outputStream, ColorSequence colorSequence) throws IOException {
        System.out.println("Taille de la séquence avant l'envoi côté serveur : " + colorSequence.getLenght());
        outputStream.writeObject(new ColorSequence(new ArrayList<>(colorSequence.getSequence())));
        outputStream.flush();
    }

    private static ColorSequence generateColorSequence() {
        ColorSequence colorSequence = new ColorSequence();
        
        // Ajouter la première couleur
        colorSequence.addToSequence(COLORS.get(random.nextInt(COLORS.size())));

        return colorSequence;
    }
    private static ColorSequence addNewColorSequence(ColorSequence ColorSequenceOld) {
    	ColorSequence colorSequence = ColorSequenceOld;
    	colorSequence.addToSequence(COLORS.get(random.nextInt(COLORS.size())));
    	return colorSequence;
    }

    private static PlayerMove receivePlayerMove(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        return (PlayerMove) inputStream.readObject();
    }

    private static boolean checkPlayerMove(ColorSequence correctSequence, PlayerMove playerMove) {
        List<String> correctColors = correctSequence.getSequence();
        List<String> playerColors = playerMove.getPlayerSequence();
        return Arrays.equals(correctColors.toArray(), playerColors.toArray());
    }
}
    



