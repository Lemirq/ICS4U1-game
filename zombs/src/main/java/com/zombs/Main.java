package com.zombs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Main extends JPanel {

    private int rectX = 50; // Initial X character
    private int rectY = 50; // Initial Y character
    private final int RECT_SIZE = 40; // Size of the red rectangle
    private final int MOVE_SPEED = 10; // Speed of movement

    private boolean upPressed = false;
    private boolean downPressed = false;
    private boolean leftPressed = false;
    private boolean rightPressed = false;

    public Main() {
        setBackground(Color.GREEN); // Set the background to green for the grid ground

        // get screen dimensions
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setPreferredSize(new Dimension(screenSize.width, screenSize.height));

        // Key listener to control movement using WASD
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_W) {
                    upPressed = true;
                } else if (key == KeyEvent.VK_S) {
                    downPressed = true;
                } else if (key == KeyEvent.VK_A) {
                    leftPressed = true;
                } else if (key == KeyEvent.VK_D) {
                    rightPressed = true;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_W) {
                    upPressed = false;
                } else if (key == KeyEvent.VK_S) {
                    downPressed = false;
                } else if (key == KeyEvent.VK_A) {
                    leftPressed = false;
                } else if (key == KeyEvent.VK_D) {
                    rightPressed = false;
                }
            }
        });

        Timer timer = new Timer(16, new ActionListener() { // 60 fps
            @Override
            public void actionPerformed(ActionEvent e) {
                if (upPressed) {
                    rectY -= MOVE_SPEED;
                }
                if (downPressed) {
                    rectY += MOVE_SPEED;
                }
                if (leftPressed) {
                    rectX -= MOVE_SPEED;
                }
                if (rightPressed) {
                    rectX += MOVE_SPEED;
                }
                repaint();
            }
        });
        timer.start();

        setFocusable(true); // Make sure the panel is focusable to receive key events
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED); // Set the color to red for the rectangle
        g.fillRect(rectX, rectY, RECT_SIZE, RECT_SIZE); // Draw the red rectangle
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("2D Game");
        Main game = new Main();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        // frame.setUndecorated(true);

        frame.setVisible(true);

    }
}