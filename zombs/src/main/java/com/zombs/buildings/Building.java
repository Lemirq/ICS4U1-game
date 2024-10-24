package com.zombs.buildings;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.zombs.GamePanel;

public abstract class Building {

    public int x, y, width, height;

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

    abstract void drawBuilding(Graphics2D g2d, int x, int y, int leftEdge, int topEdge);

    public abstract Rectangle getBounds();

    public void draw(Graphics2D g2d) {
        // Display render coordinates in the top-right corner (for debugging)
        g2d.setColor(Color.WHITE);
        // g2d.drawString("X: " + renderX + " Y: " + renderY, screenWidth - 100, 20);

        // as soon as the building is off the screen, remove it, as soon as we're past
        // left edge show it
        int leftEdge = GamePanel.offsetX + (GamePanel.screenWidth / 2);
        int topEdge = GamePanel.offsetY + (GamePanel.screenHeight / 2);

        if (leftEdge > x - width) {
            drawBuilding(g2d, leftEdge - x, topEdge - y, leftEdge, topEdge);
        }
    }
}
