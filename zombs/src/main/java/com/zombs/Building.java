package com.zombs;

import java.awt.Color;
import java.awt.Graphics2D;

public class Building {
    private int x, y, width, height;

    public Building(int x, int y, int width, int height) {
        this.x = x; // world position, fixed
        this.y = y; // world position, fixed
        this.width = width;
        this.height = height;
        System.out.println("Building created at x: " + x + " y: " + y);
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void draw(Graphics2D g2d, int offsetX, int offsetY, int screenWidth, int screenHeight) {
        // Display render coordinates in the top-right corner (for debugging)
        g2d.setColor(Color.WHITE);
        // g2d.drawString("X: " + renderX + " Y: " + renderY, screenWidth - 100, 20);

        // as soon as the building is off the screen, remove it, as soon as we're past
        // left edge show it
        int leftEdge = offsetX + (screenWidth / 2);
        int topEdge = offsetY + (screenHeight / 2);

        if (leftEdge > x - width) {
            g2d.setColor(Color.BLUE);
            g2d.fillRect(leftEdge - x, topEdge - y, width, height);
        } else {
            // renderX = x - offsetX;
        }
    }
}
