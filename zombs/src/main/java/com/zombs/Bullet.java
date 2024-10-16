package com.zombs;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class Bullet extends Rectangle {
    private int x, y;
    private int startX, startY;
    private int direction;
    private int speed = 10; // Adjust speed as needed
    private int width = 10; // Adjust bullet width as needed
    private int height = 10; // Adjust bullet height as needed
    private int maxDistance = 500; // Maximum distance the bullet can travel

    public Bullet(int x, int y, int direction) {
        this.x = x;
        this.y = y;
        this.startX = x;
        this.startY = y;
        this.direction = direction;
    }

    public void update() {
        // Update bullet position based on direction
        if (direction == 1) {
            x += speed;
        } else if (direction == -1) {
            x -= speed;
        } else if (direction == 0) {
            y -= speed;
        } else if (direction == 2) {
            y += speed;
        }

        // Check if the bullet has traveled the maximum distance
        if (Math.abs(x - startX) > maxDistance || Math.abs(y - startY) > maxDistance) {
            // Remove the bullet from the game (implementation depends on your game
            // structure)

        }
    }

    public void showGunfire(Graphics2D g2d, int gunMouthX, int gunMouthY) {
        // Show gunfire animation (implementation depends on your game structure)
        // images are in Images.gunfire as an arraylist, there are 5 images
        // f1.png to f5.png
        // Use a timer to cycle through the images to create an animation
        Timer timer = new Timer(100, new ActionListener() {
            int index = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                // Draw the image at the gunMouthX and gunMouthY position
                g2d.drawImage(Images.gunfire.get(index), gunMouthX, gunMouthY, null);
                index++;
                if (index >= Images.gunfire.size()) {
                    ((Timer) e.getSource()).stop();
                }
            }
        });
        timer.start();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public int getMaxDistance() {
        return maxDistance;
    }

    public void draw(Graphics2D g2d) {
        // Update bullet position
        update();
        // Set bullet color to yellow
        g2d.setColor(Color.YELLOW);
        // Draw the bullet
        g2d.fillOval(x, y, width, height);
    }
}