package com.zombs;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import com.zombs.buildings.Building;

public class CollisionManager {
    private List<Building> collidables;

    public CollisionManager() {
        collidables = new ArrayList<>();
    }

    public void addCollidable(Building b) {
        collidables.add(b);
    }

    public boolean isColliding(Rectangle rect) {
        for (Building collidable : collidables) {
            if (collidable.getBounds().intersects(rect)) {
                return true;
            }
        }
        return false;
    }
}