package main;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SwingExample {
	    public static void main(String[] args) {
	        // Création de la fenêtre
	        JFrame frame = new JFrame("Carré de Couleurs");
	        frame.setSize(400, 450);
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	        // Création du panneau principal avec un GridLayout
	        JPanel mainPanel = new JPanel();
	        mainPanel.setLayout(new GridLayout(2, 2)); // 2 lignes, 2 colonnes

	        // Création des boutons de différentes couleurs dans le carré principal
	        JButton button1 = createColorButton("Rouge", Color.RED);
	        JButton button2 = createColorButton("Vert", Color.GREEN);
	        JButton button3 = createColorButton("Bleu", Color.BLUE);
	        JButton button4 = createColorButton("Jaune", Color.YELLOW);

	        // Ajout des boutons au panneau principal
	        mainPanel.add(button1);
	        mainPanel.add(button2);
	        mainPanel.add(button3);
	        mainPanel.add(button4);

	        // Création du panneau supérieur avec un GridLayout
	        JPanel headerPanel = new JPanel();
	        headerPanel.setLayout(new GridLayout(1, 4)); // 1 ligne, 4 colonnes

	        // Création des boutons de couleur pour le panneau supérieur
	        JButton headerButton1 = createColorButton("Rouge", Color.RED);
	        JButton headerButton2 = createColorButton("Vert", Color.GREEN);
	        JButton headerButton3 = createColorButton("Bleu", Color.BLUE);
	        JButton headerButton4 = createColorButton("Jaune", Color.YELLOW);

	        // Ajout des boutons de couleur au panneau supérieur
	        headerPanel.add(headerButton1);
	        headerPanel.add(headerButton2);
	        headerPanel.add(headerButton3);
	        headerPanel.add(headerButton4);

	        // Ajout des panneaux à la fenêtre
	        frame.getContentPane().add(headerPanel);
	        frame.getContentPane().add(mainPanel);

	        // Affichage de la fenêtre
	        frame.setLayout(new GridLayout(2, 1)); // 2 lignes, 1 colonne
	        frame.setVisible(true);
	    }

	    // Fonction utilitaire pour créer un bouton de couleur
	    private static JButton createColorButton(String label, Color color) {
	        JButton button = new JButton(label);
	        button.setBackground(color);

	        // Ajout d'un écouteur d'événements au bouton
	        button.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                // Action à effectuer lorsque le bouton est cliqué
	                System.out.println("Bouton " + label + " cliqué !");
	            }
	        });

	        return button;
	    }
	}
