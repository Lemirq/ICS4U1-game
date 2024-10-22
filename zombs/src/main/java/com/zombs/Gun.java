package com.zombs;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;

public class Gun {
    private int damage;
    private int fireRate;
    private int range;
    private int ammo;
    private int maxAmmo;
    private int reloadTime;
    private int bulletSpeed;
    public static ArrayList<Bullet> bullets = new ArrayList<>();

    public Gun(int damage, int fireRate, int range, int ammo, int maxAmmo, int reloadTime, int bulletSpeed) {
        this.damage = damage;
        this.fireRate = fireRate;
        this.range = range;
        this.ammo = ammo;
        this.maxAmmo = maxAmmo;
        this.reloadTime = reloadTime;
        this.bulletSpeed = bulletSpeed;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getFireRate() {
        return fireRate;
    }

    public void setFireRate(int fireRate) {
        this.fireRate = fireRate;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getAmmo() {
        return ammo;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    public int getMaxAmmo() {
        return maxAmmo;
    }

    public void setMaxAmmo(int maxAmmo) {
        this.maxAmmo = maxAmmo;
    }

    public int getReloadTime() {
        return reloadTime;
    }

    public void setReloadTime(int reloadTime) {
        this.reloadTime = reloadTime;
    }

    public int getBulletSpeed() {
        return bulletSpeed;
    }

    public void setBulletSpeed(int bulletSpeed) {
        this.bulletSpeed = bulletSpeed;
    }

    public void reload() {
        this.ammo = this.maxAmmo;
    }

    public void shoot() {
        this.ammo--;
    }

    public boolean canShoot() {
        return this.ammo > 0;
    }

    public boolean needsReload() {
        return this.ammo == 0;
    }

    public void shootBullet(Player player) {
        int gunMouthX;
        int gunMouthY;

        int centerX = GamePanel.screenWidth / 2;
        int centerY = GamePanel.screenHeight / 2;

        if (GamePanel.direction == -1) {
            gunMouthX = centerX - Images.gun.getWidth();
            gunMouthY = centerY + 35;
        } else {
            gunMouthX = centerX + Images.player_idle.getWidth() / 2 - 40;
            gunMouthY = centerY + 35;
        }

        // Create a new bullet object

        // calculate x and y position of the gun mouth
        int collisionX = player.getBounds().x;
        int collisionY = player.getBounds().y + 20;
        Bullet newBullet = new Bullet(gunMouthX, gunMouthY, collisionX, collisionY, GamePanel.direction, this.range);
        // Add the bullet to the list of bullets
        bullets.add(newBullet);
    }

    public void updateBullets() {
        // Update bullets
        Iterator<Bullet> iterator = bullets.iterator();
        while (iterator.hasNext()) {
            Bullet bullet = iterator.next();
            bullet.update();
            if (Math.abs(bullet.getX() - bullet.getStartX()) > bullet.getMaxDistance() ||
                    Math.abs(bullet.getY() - bullet.getStartY()) > bullet.getMaxDistance()) {
                iterator.remove();
            }
        }
    }

    public void draw(Graphics2D g2d, int centerXPlayer, int centerYPlayer) {
        updateBullets();

        // Draw the gun
        if (GamePanel.direction == -1) {
            g2d.translate(centerXPlayer - 70 + Images.player_idle.getWidth() / 2,
                    centerYPlayer + 40 + Images.player_idle.getHeight() / 2);
            g2d.scale(-1, 1);
            g2d.drawImage(Images.gun, -Images.gun.getWidth(), -Images.gun.getHeight() / 2, null);
        } else {
            g2d.translate(centerXPlayer + Images.player_idle.getWidth() / 2,
                    centerYPlayer + 40 + Images.player_idle.getHeight() / 2);
            g2d.drawImage(Images.gun, 0, -Images.gun.getHeight() / 2, null);
        }
        g2d.setTransform(GamePanel.oldTransformation);

        // Draw the bullets
        for (Bullet bullet : Gun.bullets) {
            bullet.draw(g2d);
        }

    }
}
