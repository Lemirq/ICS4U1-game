package com.zombs;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.BasicStroke;

public class HealthBar {
    // Constants for the health bar dimensions and position
    private static final int BAR_WIDTH = 200;
    private static final int BAR_HEIGHT = 20;
    private static int X_POSITION = 200; // Bottom-left of the screen
    private static int Y_POSITION = 200; // Adjust based on your screen height

    private int healthValue;

    public HealthBar(int value, int screenHeight, int screenWidth) {
        this.healthValue = value;

    }

    // Setter to update health
    public void setHealthValue(int value) {
        this.healthValue = value;
    }

    public void draw(Graphics2D g2d) {
        // Determine the health bar color based on the health value
        Color fillColor;
        if (healthValue < 50) {
            fillColor = Color.RED; // Less than 50% health
        } else if (healthValue < 75) {
            fillColor = Color.YELLOW; // Less than 75% health
        } else {
            fillColor = Color.GREEN; // 75% or more health
        }

        // Draw the border of the health bar
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRect(X_POSITION, Y_POSITION, BAR_WIDTH, BAR_HEIGHT);

        // Fill the health bar based on the current health percentage
        int fillWidth = (int) ((healthValue / 100.0) * BAR_WIDTH);
        g2d.setColor(fillColor);
        g2d.fillRect(X_POSITION, Y_POSITION, fillWidth, BAR_HEIGHT);
    }
}