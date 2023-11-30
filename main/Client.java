package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.Timer;

public class Client {
    private static JButton button1;
    private static JButton button2;
    private static JButton button3;
    private static JButton button4;

    public static void main(String[] args) throws ClassNotFoundException {
        try {
            initGUI();
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
                ArrayList<Color> playerSequence = getPlayerSequence(colorSequence.getLenght());
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
        ArrayList<Color> sequence = (ArrayList<Color>) colorSequence.getSequence();
        System.out.println("Séquence à mémoriser : " + sequence);

        Timer timer = new Timer(1000, new ActionListener() {
            int currentIndex = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentIndex < sequence.size()) {
                    Color currentColor = sequence.get(currentIndex);
                    highlightButton(currentColor);
                    currentIndex++;
                } else {
                    ((Timer) e.getSource()).stop();
                }
            }
        });

        timer.start();
    }

    private static void highlightButton(Color color) {
        int highlightDuration = 500;

        button1.setBackground(color);
        button2.setBackground(color);
        button3.setBackground(color);
        button4.setBackground(color);

        try {
            Thread.sleep(highlightDuration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        resetButtonColors();
    }

    private static void resetButtonColors() {
        button1.setBackground(Color.RED);
        button2.setBackground(Color.GREEN);
        button3.setBackground(Color.BLUE);
        button4.setBackground(Color.YELLOW);
    }

    private static void initGUI() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Carré de Couleurs");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 400);
            frame.setLayout(new GridLayout(2, 2));

            button1 = createColorButton("Rouge", Color.RED);
            button2 = createColorButton("Vert", Color.GREEN);
            button3 = createColorButton("Bleu", Color.BLUE);
            button4 = createColorButton("Jaune", Color.YELLOW);

            frame.add(button1);
            frame.add(button2);
            frame.add(button3);
            frame.add(button4);

            frame.setVisible(true);
        });
    }

    private static JButton createColorButton(String label, Color color) {
        JButton button = new JButton();
        button.setBackground(color);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Action à effectuer lorsque le bouton est cliqué
                System.out.println("Bouton " + label + " cliqué !");
            }
        });

        return button;
    }

    private static ArrayList<Color> getPlayerSequence(int length) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Color> playerSequence = new ArrayList<>();

        for (int i = 0; i < length; i++) {
            System.out.print("Saisissez la couleur n°" + (i + 1) + ": ");
            // Utilisez votre propre logique pour convertir les couleurs de la console en objet Color
            Color color = Color.BLACK; // Remplacez cela par votre propre logique
            playerSequence.add(color);
        }

        return playerSequence;
    }

    private static void sendPlayerMove(ObjectOutputStream outputStream, ArrayList<Color> playerMove) throws IOException {
        outputStream.writeObject(new PlayerMove(playerMove, true));
        outputStream.flush();
    }

    private static boolean receivePlayerMoveResult(ObjectInputStream inputStream) throws IOException {
        return inputStream.readBoolean();
    }
}
