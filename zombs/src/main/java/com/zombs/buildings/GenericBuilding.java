package com.zombs.buildings;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class GenericBuilding extends Building {
    public GenericBuilding(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    void drawBuilding(Graphics2D g2d, int x, int y, int leftEdge, int topEdge) {
        // Draw a square
        g2d.setColor(Color.BLUE);
        g2d.fillRect(leftEdge - x, topEdge - y, width, height);

    }

    public boolean isColliding(Rectangle rect) {
        Rectangle shopRect = new Rectangle(x, y, width, height);
        return shopRect.intersects(rect);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(super.x, super.y, width, height);
    }
}
