package com.zombs;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class Player extends GameObject {
    private int health;
    public static int runningAnimFrame = 0;
    private Timer animationTimer;
    public static int totalCash = 0;

    private boolean isDead = false;

    HealthBar bar = new HealthBar(100);

    public Player(int health) {
        super(0, 0, Images.player_idle.getWidth(), Images.player_idle.getHeight()); // Call to the superclass
                                                                                    // constructor
        this.health = health;

        initRunningAnimation();

    }

    public void addCash(int cash) {
        totalCash += cash;
    }

    private void initRunningAnimation() {
        // Animation timer for player running animation
        animationTimer = new Timer(100, new ActionListener() { // 10 frames per second
            @Override
            public void actionPerformed(ActionEvent e) {
                runningAnimFrame = (runningAnimFrame + 1) % Images.player_running.size();
            }
        });
        animationTimer.start();
    }

    public int getHealth() {
        return health;
    }

    public void decreaseHealth(int amount) {
        this.health = Math.max(0, this.health - amount);
        bar.setHealthValue(this.health);
        if (this.health < 99 && !isDead) {
            isDead = true;
            System.out.println("Player is dead");
        }
    }

    public void setHealth(int health) {
        this.bar = new HealthBar(health);
        this.health = health;
    }

    public Rectangle getBounds() {
        // System.out.println("Player bounds: " + (GamePanel.offsetX +
        // (Images.player_idle.getWidth() / 2)) + " "
        // + (GamePanel.offsetY + (Images.player_idle.getHeight() / 2)) + " " +
        // Images.player_idle.getWidth() + " "
        // + Images.player_idle.getHeight());

        return new Rectangle(GamePanel.offsetX + (Images.player_idle.getWidth() / 2),
                GamePanel.offsetY + (Images.player_idle.getHeight() / 2), Images.player_idle.getWidth(),
                Images.player_idle.getHeight());
    }

    public void drawCash(Graphics2D g2d) {
        g2d.setFont(new Font("Impact", Font.BOLD, 30));
        g2d.drawString("$" + totalCash, 100, GamePanel.screenHeight - 130);
        g2d.setFont(new Font("DejaVuSans 12", Font.PLAIN, 12));

    }

    public void draw(Graphics2D g2d, int direction, boolean idle, int centerXPlayer, int centerYPlayer) {
        Image playerImage = (GamePanel.upPressed || GamePanel.downPressed || GamePanel.leftPressed
                || GamePanel.rightPressed)
                        ? Images.player_running.get(runningAnimFrame)
                        : Images.player_idle;

        // Apply horizontal flip if moving left
        if (direction == -1) {
            g2d.translate(centerXPlayer + Images.player_idle.getWidth(), centerYPlayer);
            g2d.scale(-1, 1);
            g2d.drawImage(playerImage, 0, 0, null);
        } else {
            g2d.drawImage(playerImage, centerXPlayer, centerYPlayer, null);
        }

        if (isDead) {
            // big text in the middle of the screen
            // g2d.drawString("Game Over", GamePanel.screenWidth / 2, GamePanel.screenHeight
            // / 2);
        } else {

            // decide if player is running or idle and change sprite accordingly

        }

        g2d.setTransform(GamePanel.oldTransformation);

    }
}
