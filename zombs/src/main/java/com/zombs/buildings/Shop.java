package com.zombs.buildings;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.zombs.Images;

public class Shop extends Building {

    public Shop(int x, int y, int width, int height) {
        super(x, y, width, height);
        // this.x = x; // world position, fixed
        // this.y = y; // world position, fixed
        // this.width = width;
        // this.height = height;
        System.out.println("Building created at x: " + x + " y: " + y);
    }

    // public void draw(Graphics2D g2d, int screenWidth, int screenHeight) {
    // // Display render coordinates in the top-right corner (for debugging)
    // // g2d.drawString("X: " + renderX + " Y: " + renderY, screenWidth - 100, 20);

    // // as soon as the building is off the screen, remove it, as soon as we're
    // past
    // // left edge show it
    // int leftEdge = GamePanel.offsetX + (screenWidth / 2);
    // int topEdge = GamePanel.offsetY + (screenHeight / 2);

    // // if the shop is on the screen, draw it
    // if (leftEdge > x - width) {
    // // g2d.drawImage(Images.gun, -Images.gun.getWidth(), -Images.gun.getHeight()
    // /
    // // 2, null);

    // }
    // }
    @Override
    void drawBuilding(Graphics2D g2d, int x, int y, int leftEdge, int topEdge) {
        System.out.println("Drawing shop at x: " + x + " y: " + y);
        g2d.drawImage(Images.shop, x, y, null);

    }

    public boolean isColliding(Rectangle rect) {
        Rectangle shopRect = new Rectangle(x, y, width, height);
        return shopRect.intersects(rect);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(super.x, super.y, super.width, super.height);
    }
}
