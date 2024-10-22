package com.zombs;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.zombs.buildings.Building;
import com.zombs.buildings.GenericBuilding;
import com.zombs.buildings.Shop;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class GamePanel extends JPanel {

    public static int offsetX = 0; // Offset for the grid's X position
    public static int offsetY = 0; // Offset for the grid's Y position
    private int MOVE_SPEED = 6; // Speed of movement
    private final int GRID_SIZE = 50; // Size of each grid cell
    private final int[] X_Bounds = { -2000, 2000 };
    private final int[] Y_Bounds = { -700, 700 };
    public static AffineTransform oldTransformation;
    public static int screenWidth = 1920;
    public static int screenHeight = 1080;

    public static boolean upPressed = false;
    public static boolean downPressed = false;
    public static boolean leftPressed = false;
    public static boolean rightPressed = false;
    public static boolean shootPressed = false;

    public boolean idle = true;

    Building building = null;
    Tree tree = null;
    Player player = null;
    Gun gun = null;
    Shop shop = null;
    Boundary boundary = new Boundary();
    Screens screens = new Screens();

    public static int direction = 1; // 0 is up, 1 is right, 2 is down, 3 is left
    public static int gunMouthX, gunMouthY;

    private ArrayList<Zombie> zombies = new ArrayList<>(); // List to store zombies
    private CollisionManager collisionManager;

    public GamePanel() {
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // This method is called when the JPanel is resized
                System.out.println("GamePanel resized to: " + getSize());
                screenHeight = getSize().height;
                screenWidth = getSize().width;
                // You can update or reposition components here if needed
                revalidate();
                initializeObjects();
            }
        });
        // Ensure the frame and panel have been fully laid out

        setBackground(Theme.BG); // Set the background to green for the grid ground
        setPreferredSize(new Dimension(1920, 1080)); // Set the game window size

        // Load all images: sprites, backgrounds, etc.
        Images.loadImages();
        setFocusable(true); // Make sure the panel is focusable to receive key events

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
                    gun.shootBullet(player);
                    shootPressed = false; // Prevent continuous shooting
                }
                updateGame();
                repaint();
            }
        });
        timer.start();

        // Delay initialization of objects to allow window to resize
        Timer initTimer = new Timer(500, new ActionListener() { // 500ms delay
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                initializeObjects();
            }
        });
        initTimer.setRepeats(false); // Only execute once
        initTimer.start();

        // Timer to decrease player's health, for testing purposes
        // Timer healthTimer = new Timer(1000, new ActionListener() { // Decrease health
        // every second
        // @Override
        // public void actionPerformed(java.awt.event.ActionEvent e) {
        // player.decreaseHealth(1); // Decrease health by 1
        // repaint(); // Repaint to update health bar
        // }
        // });
        // healthTimer.start();
    }

    private void updateGame() {
        updateZombies();
        checkCollisions();
        calculateGunMouth();
        removeDeadZombies();
    }

    private void removeDeadZombies() {
        Iterator<Zombie> zombieIterator = zombies.iterator();
        while (zombieIterator.hasNext()) {
            Zombie zombie = zombieIterator.next();
            if (zombie.isDead) {
                // add money to player
                player.addCash(zombie.money);
                zombieIterator.remove();
            }
        }
    }

    private void initializeObjects() {
        // Initialize the player object
        if (player == null && tree == null) {
            tree = new Tree(500, 500, 232, 211);
            player = new Player(100);
            building = new GenericBuilding(500, 500, 200, 200);

            collisionManager = new CollisionManager();
            collisionManager.addCollidable(building);
            collisionManager.addCollidable(shop);

            gun = new Gun(10, 10, 1600, 30, 30, 2, 1);
            shop = new Shop(300, 300, 200, 200);
            // add 10 zombies at random positions
            for (int i = 0; i < 10; i++) {
                int x = (int) (Math.random() * 1000);
                int y = (int) (Math.random() * 1000);
                zombies.add(new Zombie(x, y));
            }

            zombies.add(new Zombie(300, 300)); // Spawn a zombie at position (300, 300)
        }
    }

    private void print(String s) {
        System.out.println(s);
    }

    private void checkCollisions() {
        // check collisions between player and zombies
        for (Zombie zombie : zombies) {
            // System.out.println("Player x: " + player.getBounds().getX() + " y: " +
            // player.getBounds().getY());
            // print offset x and y
            // System.out.println("offsetX: " + offsetX + " offsetY: " + offsetY);

            // if zombie intersects with offset x and y
            if (zombie.getBounds().intersects(player.getBounds())) {
                print("Player collided with zombie");
                player.decreaseHealth(1);
            }
        }

        // check collisions between bullets and zombies
        Iterator<Bullet> bulletIterator = Gun.bullets.iterator();
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            Rectangle bulletBounds = bullet.getBounds();

            for (Zombie zombie : zombies) {
                Rectangle zombieBounds = zombie.getBounds();
                // print("Zombie x: " + zombieBounds.getX() + " y: " + zombieBounds.getY());
                if (bulletBounds.intersects(zombieBounds)) {
                    print("Bullet collided with zombie");
                    bulletIterator.remove();
                    // Decrease the zombie's health
                    zombie.takeDamage(10);
                    break;
                }
            }
        }
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
        int centerX = screenWidth / 2;
        int centerY = screenHeight / 2;

        if (direction == -1) {
            gunMouthX = centerX - Images.gun.getWidth();
            gunMouthY = centerY + 35;
        } else {
            gunMouthX = centerX + Images.player_idle.getWidth() / 2 - 40;
            gunMouthY = centerY + 35;
        }
    }

    private void updateZombies() {
        // Update zombies
        for (Zombie zombie : zombies) {
            zombie.update(player);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        screenHeight = getHeight();
        screenWidth = getWidth();

        Graphics2D g2d = (Graphics2D) g;
        oldTransformation = g2d.getTransform();

        // Get the center of the screen
        int centerX = screenWidth / 2;
        int centerY = screenHeight / 2;

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
        // macbook
        int leftBoundary = offsetX - centerX - centerX / 2;
        int rightBoundary = -(offsetX + centerX) - centerX / 2;
        int topBoundary = offsetY - centerY + 200;
        int bottomBoundary = -(offsetY + centerY) + 200;

        boundary.draw(g2d, leftBoundary, rightBoundary, topBoundary, bottomBoundary);

        int centerXPlayer = screenWidth / 2 - Images.player_idle.getWidth() / 2;
        int centerYPlayer = screenHeight / 2 - Images.player_idle.getHeight() / 2;

        // Draw the player image
        player.draw(g2d, direction, idle, centerXPlayer, centerYPlayer);

        // Draw the gun
        gun.draw(g2d, centerXPlayer, centerYPlayer);

        // Draw the zombies
        for (Zombie zombie : zombies) {
            zombie.draw(g2d);
        }

        // test rectangle to gain mouth of gun
        g2d.setColor(Color.RED);

        g2d.drawRect(gunMouthX, gunMouthY, 5, 5);
        tree.draw(g2d, getWidth(), getHeight());
        shop.draw(g2d);

        // print text for offsety and offsetx in top left corner
        // bg black rect
        // g2d.setColor(Color.BLACK);
        // g2d.fillRect(0, 0, 200, 300);
        // g2d.setColor(Color.RED);
        // g2d.drawString("offsetX: " + offsetX + " offsetY: " + offsetY, 10, 20);
        // // centerx and centery
        // g2d.drawString("centerX: " + centerX + " centerY: " + centerY, 10, 40);
        // // print sizes

        // // print all boundaries
        // g2d.drawString("leftBoundary: " + leftBoundary, 10, 60);
        // g2d.drawString("rightBoundary: " + rightBoundary, 10, 80);
        // g2d.drawString("topBoundary: " + topBoundary, 10, 100);
        // g2d.drawString("bottomBoundary: " + bottomBoundary, 10, 120);
        // g2d.drawString("width: " + getWidth() + " height: " + getHeight(), 10, 140);

        player.bar.draw(g2d);
        player.drawCash(g2d);

    }
}