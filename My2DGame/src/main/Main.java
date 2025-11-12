package main;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        // Create the main window (JFrame)
        JFrame window = new JFrame();

        // Close the program when the window is closed
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Prevent the window from being resizable
        window.setResizable(false);

        // Set the title of the window
        window.setTitle("Nigga Quest");

        // Create the game panel (where the game runs and draws)
        GamePanel gamePanel = new GamePanel();

        // Add the game panel to the window
        window.add(gamePanel);

        // Set the window size to fit the game panel
        window.pack();

        // Center the window on the screen
        window.setLocationRelativeTo(null);

        // Make the window visible
        window.setVisible(true);

        // Start the game loop
        gamePanel.startGameThread();
    }
}