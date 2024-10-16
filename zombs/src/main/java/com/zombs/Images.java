package com.zombs;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Images {
    public static ArrayList<BufferedImage> player_running = new ArrayList<>();
    public static ArrayList<BufferedImage> gunfire = new ArrayList<>();
    public static BufferedImage player_idle;
    public static BufferedImage circle;
    public static BufferedImage gun;
    public static BufferedImage bullet;

    public static void loadImages() {
        try {
            // there are 12 image sequences for the player in /sprites/player_running going
            // from r1.png to r12.png
            for (int i = 1; i <= 12; i++) {
                player_running
                        .add(ImageIO.read(Images.class.getResource("/sprites/player_running/200x200/r" + i + ".png")));
            }

            // there are 5 images in /fire going from f1.png to f5.png with 0.5 increments
            for (int i = 1; i <= 5; i++) {
                gunfire.add(ImageIO.read(Images.class.getResource("/fire/f" + i + ".png")));
            }

            player_idle = ImageIO.read(Images.class.getResource("/sprites/idle.png"));
            gun = ImageIO.read(Images.class.getResource("/gun.png"));
            bullet = ImageIO.read(Images.class.getResource("/bullet.png"));
            // resize the images to 50x50
            // gun = ImageIO.read(Images.class.getResource("/sprites/gun.png"));
            // bullet = ImageIO.read(Images.class.getResource("/sprites/bullet.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}