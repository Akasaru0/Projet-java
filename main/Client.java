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
import java.util.Iterator;
import java.util.Scanner;
import javax.swing.Timer;

public class Client {
    private static JButton button1;
    private static JButton button2;
    private static JButton button3;
    private static JButton button4;
    private static ArrayList<Color> colors;
    private static int count_input;

    public static void main(String[] args) throws ClassNotFoundException, InterruptedException {
        try {
            initGUI();
            Socket socket = new Socket("localhost", 12345);

            // Configurer les flux de communication avec le serveur
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            
            Thread.sleep(2000);
            while (true) {
            	count_input = 0;
            	colors = new ArrayList<Color>();
            	
                // Recevoir la séquence de couleurs du serveur
                ColorSequence colorSequence = receiveColorSequence(inputStream);
                displayColorSequence(colorSequence);

                while(count_input<colorSequence.getLenght()) {
                    try {
                        // Mettez le thread en pause pendant un certain temps pour éviter une boucle infinie rapide
                        Thread.sleep(500); // Pause d'une seconde, ajustez selon vos besoins
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                sendPlayerMove(outputStream, colors);

                // Recevoir le résultat du mouvement
                boolean moveResult = receivePlayerMoveResult(inputStream);
                if (!moveResult) {
                    System.out.println("Vous avez perdu. Fin du jeu.");
                    break;
                }
                try {
                    // Mettez le thread en pause pendant un certain temps pour éviter une boucle infinie rapide
                    Thread.sleep(1000); // Pause d'une seconde, ajustez selon vos besoins
                } catch (InterruptedException e) {
                    e.printStackTrace();
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
        for (int i = 0; i < sequence.size(); i++) {
        	highlightButton(sequence.get(i));
		}
    }

    private static void highlightButton(Color color) {
        try {
        	Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Color new_color = darkenColor(color, (float) 0.3);
        

//        if (color.equals(Color.RED)) {
//            // Action spécifique pour la couleur rouge
//            System.out.println("Bouton Rouge Affiché !");
//            button1.setBackground(new_color);
//        } else if (color.equals(Color.GREEN)) {
//            // Action spécifique pour la couleur verte
//            System.out.println("Bouton Vert Affiché !");
//			button2.setBackground(new_color);
//        } else if (color.equals(Color.BLUE)) {
//            // Action spécifique pour la couleur bleue
//            System.out.println("Bouton Bleu Affiché !");
//            button3.setBackground(new_color);
//        } else if (color.equals(Color.YELLOW)) {
//            // Action spécifique pour la couleur jaune
//            System.out.println("Bouton Jaune Affiché !");
//            button4.setBackground(new_color);
//        }
    	
		if (color.equals(Color.RED)) {
			// Action spécifique pour la couleur rouge
			System.out.println("Bouton Rouge Affiché !");
			button1.setBackground(Color.RED);
		} else if (color.equals(Color.GREEN)) {
			// Action spécifique pour la couleur verte
			System.out.println("Bouton Vert Affiché !");
			button2.setBackground(Color.GREEN);
		} else if (color.equals(Color.BLUE)) {
			// Action spécifique pour la couleur bleue
			System.out.println("Bouton Bleu Affiché !");
			button3.setBackground(Color.BLUE);
		} else if (color.equals(Color.YELLOW)) {
			// Action spécifique pour la couleur jaune
			System.out.println("Bouton Jaune Affiché !");
			button4.setBackground(Color.YELLOW);
		}
   
        try {
        	Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        resetButtonColors();

    }
    private static Color darkenColor(Color color, float factor) {
        float[] hsbValues = new float[3];
        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsbValues);
        hsbValues[2] *= factor; // Réduire la luminosité (assombrir)
        return new Color(Color.HSBtoRGB(hsbValues[0], hsbValues[1], hsbValues[2]));
    }

    private static void resetButtonColors() {
//        button1.setBackground(Color.RED);
//        button2.setBackground(Color.GREEN);
//        button3.setBackground(Color.BLUE);
//        button4.setBackground(Color.YELLOW);
        
        button1.setBackground(darkenColor(Color.RED, (float) 0.3));
        button2.setBackground(darkenColor(Color.GREEN, (float) 0.3));
        button3.setBackground(darkenColor(Color.BLUE, (float) 0.3));
        button4.setBackground(darkenColor(Color.YELLOW, (float) 0.3));
        
    }

    private static void initGUI() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Carré de Couleurs");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 400);
            frame.setLayout(new GridLayout(2, 2));

//            button1 = createColorButton("Rouge", Color.RED);
//            button2 = createColorButton("Vert", Color.GREEN);
//            button3 = createColorButton("Bleu", Color.BLUE);
//            button4 = createColorButton("Jaune", Color.YELLOW);
            
            button1 = createColorButton("Rouge", darkenColor(Color.RED, (float) 0.3));
            button2 = createColorButton("Vert", darkenColor(Color.GREEN, (float) 0.3));
            button3 = createColorButton("Bleu", darkenColor(Color.BLUE, (float) 0.3));
            button4 = createColorButton("Jaune", darkenColor(Color.YELLOW, (float) 0.3));

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
                System.out.println("Bouton " + label + " cliqué !");
                count_input++;
            	switch (label) {
				case "Rouge": {
					colors.add(Color.RED);
					break;
				}
				case "Vert": {
					colors.add(Color.green);
					break;
				}
				case "Bleu": {
					colors.add(Color.blue);
					break;
				}
				case "Jaune": {
					colors.add(Color.yellow);
					break;
				}

				}
            	
            	
                // Action à effectuer lorsque le bouton est cliqué

            }
        });

        return button;
    }


    private static void sendPlayerMove(ObjectOutputStream outputStream, ArrayList<Color> colors) throws IOException {
        outputStream.writeObject(new PlayerMove(colors, true));
        outputStream.flush();
    }

    private static boolean receivePlayerMoveResult(ObjectInputStream inputStream) throws IOException {
        return inputStream.readBoolean();
    }
}