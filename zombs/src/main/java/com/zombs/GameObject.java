package com.zombs;

import java.awt.Rectangle;

public abstract class GameObject {
    protected int x, y; // Position
    protected int width, height; // Size

    public GameObject(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    // Abstract method to render the object
    // public abstract void draw(Graphics g);

    // Get the bounding box for collision detection
    abstract Rectangle getBounds();

    // Check for collision with another GameObject
    // public abstract boolean collidesWith(GameObject other);

    // Update the object's position based on its velocity
    public void update() {
    }

    // Getters and setters for position and velocity
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}