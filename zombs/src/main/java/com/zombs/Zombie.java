package com.zombs;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

public class Zombie extends GameObject {
    private int x, y; // Position of the zombie
    private int speed; // Speed of the zombie
    private Image sprite; // Sprite for the zombie
    private int width = Images.player_idle.getWidth();
    private int height = Images.player_idle.getHeight(); // Width of the zombie
    private int health = 100; // Health of the zombie
    public int money = 10; // Money dropped by the zombie

    // isdead boolea
    public boolean isDead = false;

    public Zombie(int startX, int startY) {
        super(startX, startY, Images.player_idle.getWidth(), Images.player_idle.getHeight());
        this.x = startX;
        this.y = startY;
        this.speed = 1; // Example speed, adjust as needed
        this.sprite = Images.player_idle; // Using the same sprite as the player
        System.out.println("Zombie x: " + x + " y: " + y);

    }

    // Method to update the zombie's position to chase the player
    public void update(Player player) {
        // get offset x and y, then subtract half the player sprite width and height
        int playerX = player.getBounds().x;
        int playerY = player.getBounds().y;
        // int playerX = player.getX();
        // int playerY = player.getY();

        // System.out.println("Player x: " + playerX + " y: " + playerY);

        // // Calculate the direction to move towards the player
        if (x < playerX) {
            x += speed;
        } else if (x > playerX) {
            x -= speed;
        }

        if (y < playerY) {
            y += speed;
        } else if (y > playerY) {
            y -= speed;
        }
    }

    // Method to draw the zombie on the screen
    public void draw(Graphics2D g2d) {
        int leftEdge = GamePanel.offsetX + (GamePanel.screenWidth / 2);
        int topEdge = GamePanel.offsetY + (GamePanel.screenHeight / 2);
        if (leftEdge > x - width) {
            g2d.drawImage(sprite, leftEdge - x, topEdge - y, null);
            drawHealthBar(g2d, leftEdge - x, topEdge - y - 10); // Draw health bar above the zombie

        }
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            // Zombie is dead
            System.out.println("Zombie is dead");
            // remove
            isDead = true;
        }
    }

    // Method to draw the health bar above the zombie
    private void drawHealthBar(Graphics2D g2d, int x, int y) {
        int barWidth = 50;
        int barHeight = 5;
        int healthBarWidth = (int) ((health / 100.0) * barWidth);

        g2d.setColor(Color.RED);
        g2d.fillRect(x, y, barWidth, barHeight);

        g2d.setColor(Color.GREEN);
        g2d.fillRect(x, y, healthBarWidth, barHeight);
    }

    // Getters for the zombie's position
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    // Getters for the zombie's health
    public int getHealth() {
        return health;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}