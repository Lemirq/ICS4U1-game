package com.zombs;

import java.awt.Graphics2D;
import java.awt.Image;

public class Player {
    // should contain health, player position, etc.

    private int health;
    HealthBar bar = new HealthBar(100, 0, 0);

    public Player(int health, int x, int y) {
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health, int screenHeight, int screenWidth) {
        this.bar = new HealthBar(health, screenHeight, screenWidth);
        this.health = health;
    }

    public void draw(Graphics2D g2d, int direction, boolean idle, int centerXPlayer, int centerYPlayer,
            int screenHeight,
            int screenWidth,
            Image playerImage) {
        bar.draw(g2d);

        // Apply horizontal flip if moving left
        if (direction == -1) {
            g2d.translate(centerXPlayer + Images.player_idle.getWidth(), centerYPlayer);
            g2d.scale(-1, 1);
            g2d.drawImage(playerImage, 0, 0, null);
        } else {
            g2d.drawImage(playerImage, centerXPlayer, centerYPlayer, null);
        }
    }
}
