package com.zombs;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel {

    private int offsetX = 0; // Offset for the grid's X position
    private int offsetY = 0; // Offset for the grid's Y position
    private int MOVE_SPEED = 6; // Speed of movement
    private final int GRID_SIZE = 50; // Size of each grid cell
    private final int[] X_Bounds = { -2000, 2000 };
    private final int[] Y_Bounds = { -700, 700 };
    public static AffineTransform oldTransformation;
    public static int screenWidth;
    public static int screenHeight;

    private boolean upPressed = false;
    private boolean downPressed = false;
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean shootPressed = false;

    private boolean idle = true;

    Building building = new Building(500, 500, 200, 200);
    Player player = new Player(100, 0, 0);

    private int direction = 1; // 0 is up, 1 is right, 2 is down, 3 is left
    public static int gunMouthX, gunMouthY;

    private ArrayList<Bullet> bullets = new ArrayList<>();

    private int animationFrame = 0;
    private Timer animationTimer;

    public GamePanel() {
        setBackground(Theme.BG); // Set the background to green for the grid ground
        setPreferredSize(new Dimension(800, 600)); // Set the game window size

        // Load images
        Images.loadImages();

        // set screen width and height
        screenWidth = getWidth();
        screenHeight = getHeight();

        // Key listener to control movement using WASD and shooting with space
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_W) {
                    upPressed = true;
                    direction = 0;
                } else if (key == KeyEvent.VK_S) {
                    downPressed = true;
                    direction = 2;
                } else if (key == KeyEvent.VK_A) {
                    leftPressed = true;
                    direction = -1;
                } else if (key == KeyEvent.VK_D) {
                    rightPressed = true;
                    direction = 1;
                } else if (key == KeyEvent.VK_SPACE) {
                    shootPressed = true;
                } else if (key == KeyEvent.VK_SHIFT) {
                    MOVE_SPEED = 30;
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
                } else if (key == KeyEvent.VK_E) {
                    shootPressed = false;
                } else if (key == KeyEvent.VK_SHIFT) {
                    MOVE_SPEED = 6;
                }
            }
        });

        Timer timer = new Timer(17, new ActionListener() { // roughly 60 frames per second as 1000ms / 60fps =
                                                           // 16.6666666667ms
            @Override
            public void actionPerformed(ActionEvent e) {
                moveMap();
                if (shootPressed) {
                    shootBullet();
                    shootPressed = false; // Prevent continuous shooting
                }
                updateBullets();
                calculateGunMouth();
                repaint();
            }
        });
        timer.start();

        // Animation timer for player running animation
        animationTimer = new Timer(100, new ActionListener() { // 10 frames per second
            @Override
            public void actionPerformed(ActionEvent e) {
                animationFrame = (animationFrame + 1) % Images.player_running.size();
            }
        });
        animationTimer.start();

        setFocusable(true); // Make sure the panel is focusable to receive key events
    }

    private void moveMap() {

        if (upPressed && offsetY < Y_Bounds[1]) {
            offsetY += MOVE_SPEED;
        }
        if (downPressed && offsetY > Y_Bounds[0]) {
            offsetY -= MOVE_SPEED;
        }
        if (leftPressed && offsetX < X_Bounds[1]) {
            offsetX += MOVE_SPEED;
        }
        if (rightPressed && offsetX > X_Bounds[0]) {
            offsetX -= MOVE_SPEED;
        }
    }

    private void calculateGunMouth() {
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        if (direction == -1) {
            gunMouthX = centerX - Images.gun.getWidth();
            gunMouthY = centerY + 35;
        } else {
            gunMouthX = centerX + Images.player_idle.getWidth() / 2 - 40;
            gunMouthY = centerY + 35;
        }
    }

    private void shootBullet() {
        // Graphics2D g2d = (Graphics2D) getGraphics();

        // Create and add a new bullet
        Bullet newBullet = new Bullet(gunMouthX, gunMouthY, direction);
        bullets.add(newBullet);
        // Show gunfire animation
        // newBullet.showGunfire(g2d, gunMouthX, gunMouthY);
    }

    private void updateBullets() {
        // Update bullets
        Iterator<Bullet> iterator = bullets.iterator();
        while (iterator.hasNext()) {
            Bullet bullet = iterator.next();
            bullet.update();
            if (Math.abs(bullet.getX() - bullet.getStartX()) > bullet.getMaxDistance() ||
                    Math.abs(bullet.getY() - bullet.getStartY()) > bullet.getMaxDistance()) {
                iterator.remove();
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        oldTransformation = g2d.getTransform();

        // Get the center of the screen
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        // Set the stroke width for the grid lines
        g2d.setStroke(new BasicStroke(4)); // Change the value to make the lines thicker or thinner

        // Draw the grid
        g2d.setColor(Theme.GRID);
        for (int x = offsetX % GRID_SIZE; x < getWidth(); x += GRID_SIZE) {
            g2d.drawLine(x, 0, x, getHeight());
        }
        for (int y = offsetY % GRID_SIZE; y < getHeight(); y += GRID_SIZE) {
            g2d.drawLine(0, y, getWidth(), y);
        }

        // Reset the transform
        g2d.setTransform(oldTransformation);

        // Draw black rectangles over the areas outside the map boundaries
        // Left and right boundaries
        // red line for top and bottom boundaries

        // Draw the map boundaries
        // Calculate the map boundaries relative to the center of the screen
        // left boundary should increase to the max of centerx as the player moves left
        // the player
        // should be centered on the screen

        // monitor
        // int leftBoundary = offsetX - centerX + 300;
        // int rightBoundary = -(offsetX + centerX) - 300;
        // int topBoundary = offsetY - centerY + 100;
        // int bottomBoundary = -(offsetY + centerY) + 100;

        // macbook
        int leftBoundary = offsetX - centerX - centerX / 2;
        int rightBoundary = -(offsetX + centerX) - centerX / 2;
        int topBoundary = offsetY - centerY + 200;
        int bottomBoundary = -(offsetY + centerY) + 200;

        // Draw black rectangles outside the map bounds
        g2d.setColor(Color.BLACK);
        // if left boundary is between zero and negative centerx
        if (leftBoundary > 0) {
            g2d.fillRect(0, 0, leftBoundary, getHeight());
        }
        // if right boundary is between centerx and width
        if (rightBoundary > 0) {
            g2d.fillRect(getWidth() - rightBoundary, 0, rightBoundary, getHeight());
        }
        // if top boundary is between zero and negative centery
        if (topBoundary > 0) {
            g2d.fillRect(0, 0, getWidth(), topBoundary);
        }
        // // if bottom boundary is between centery and height
        if (bottomBoundary > 0) {
            g2d.fillRect(0, getHeight() - bottomBoundary, getWidth(), bottomBoundary);
        }

        // Reset the transform
        g2d.setTransform(oldTransformation);

        int centerXPlayer = getWidth() / 2 - Images.player_idle.getWidth() / 2;
        int centerYPlayer = getHeight() / 2 - Images.player_idle.getHeight() / 2;

        // decide if player is running or idle and change sprite accordingly
        Image playerImage = (upPressed || downPressed || leftPressed || rightPressed)
                ? Images.player_running.get(animationFrame)
                : Images.player_idle;

        // Draw the player image
        player.draw(g2d, direction, idle, centerXPlayer, centerYPlayer, getHeight(), getWidth(), playerImage);

        g2d.setTransform(oldTransformation);

        // Draw the gun image
        if (direction == -1) {
            g2d.translate(centerXPlayer - 70 + Images.player_idle.getWidth() / 2,
                    centerYPlayer + 40 + Images.player_idle.getHeight() / 2);
            g2d.scale(-1, -1);
            g2d.drawImage(Images.gun, -Images.gun.getWidth(), -Images.gun.getHeight() / 2, null);
        } else {
            g2d.translate(centerXPlayer + Images.player_idle.getWidth() / 2,
                    centerYPlayer + 40 + Images.player_idle.getHeight() / 2);
            g2d.drawImage(Images.gun, 0, -Images.gun.getHeight() / 2, null);
        }
        g2d.setTransform(oldTransformation);

        // Draw the bullets
        for (Bullet bullet : bullets) {
            bullet.draw(g2d);
        }

        // test rectangle to gain mouth of gun
        g2d.setColor(Color.RED);

        g2d.drawRect(gunMouthX, gunMouthY, 5, 5);

        // print text for offsety and offsetx in top left corner
        // bg black rect
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, 200, 300);
        g2d.setColor(Color.RED);
        g2d.drawString("offsetX: " + offsetX + " offsetY: " + offsetY, 10, 20);
        // centerx and centery
        g2d.drawString("centerX: " + centerX + " centerY: " + centerY, 10, 40);
        // print sizes

        // print all boundaries
        g2d.drawString("leftBoundary: " + leftBoundary, 10, 60);
        g2d.drawString("rightBoundary: " + rightBoundary, 10, 80);
        g2d.drawString("topBoundary: " + topBoundary, 10, 100);
        g2d.drawString("bottomBoundary: " + bottomBoundary, 10, 120);
        g2d.drawString("width: " + getWidth() + " height: " + getHeight(), 10, 140);
        building.draw(g2d, offsetX, offsetY, getWidth(), getHeight());

    }
}