package com.zombs;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.Timer;

public class Screens {
    public class DeathScreen {
        private Timer deathAnimationTimer;
        private static float alpha = 0.0f; // Opacity for fade-in effect

        // public DeathScreen() {
        // initializeDeathAnimationTimer();
        // }

        // private void initializeDeathAnimationTimer() {
        // deathAnimationTimer = new Timer(100, e -> {
        // alpha += 0.1f;
        // if (alpha == 1) {
        // // alpha = 0;
        // deathAnimationTimer.stop();
        // }
        // });
        // }

        public static void draw(Graphics2D g2d) {
            // Draw the death screen with the fade-in effect as a black rectangle
            // g2d.setColor(Color.BLACK);
            // g2d.fillRect(0, 0, GamePanel.screenWidth, GamePanel.screenHeight);
            // g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            // g2d.fillRect(0, 0, GamePanel.screenWidth, GamePanel.screenHeight);
            // g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

        }

        // This class should contain the logic for the death screen
    }
}
