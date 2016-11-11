package com.pushemover.physics;

import com.pushemover.actors.Platform;
import com.pushemover.actors.Player;

import java.awt.Rectangle;

public class Collision
{
    public static boolean onContact ( Player player, Platform platform )
    {
        Rectangle playerBoundingBox = player.getBoundingBox ();
        Rectangle platformBoundingBox = platform.getBoundingBox ();
        return playerBoundingBox.x < platformBoundingBox.x + platformBoundingBox.width &&
               playerBoundingBox.x + playerBoundingBox.width > platformBoundingBox.x &&
               playerBoundingBox.y < platformBoundingBox.y + platformBoundingBox.height &&
               playerBoundingBox.y + playerBoundingBox.height > platformBoundingBox.y;
    }
}
