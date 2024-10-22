package com.zombs;

import java.awt.Graphics2D;

public class Tree {
    private int x, y, width, height;

    public Tree(int x, int y, int width, int height) {
        this.x = x; // world position, fixed
        this.y = y; // world position, fixed
        this.width = width;
        this.height = height;
        System.out.println("Building created at x: " + x + " y: " + y);
    }

    public void draw(Graphics2D g2d, int screenWidth, int screenHeight) {
        // Display render coordinates in the top-right corner (for debugging)
        // g2d.drawString("X: " + renderX + " Y: " + renderY, screenWidth - 100, 20);

        // as soon as the building is off the screen, remove it, as soon as we're past
        // left edge show it
        int leftEdge = GamePanel.offsetX + (GamePanel.screenWidth / 2);
        int topEdge = GamePanel.offsetY + (GamePanel.screenHeight / 2);

        if (leftEdge > x - width) {
            // g2d.drawImage(Images.gun, -Images.gun.getWidth(), -Images.gun.getHeight() /
            // 2, null);

            g2d.drawImage(Images.tree, leftEdge - x, topEdge - y, null);
        } else {
            // renderX = x - offsetX;
        }
    }
}
