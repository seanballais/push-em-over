package com.pushemover.physics;

import java.awt.Rectangle;

public class Collision
{
    public static boolean onContact ( Rectangle boundingBox1, Rectangle boundingBox2 )
    {
        return boundingBox1.x < boundingBox2.x + boundingBox2.width &&
               boundingBox1.x + boundingBox1.width > boundingBox2.x &&
               boundingBox1.y < boundingBox2.y + boundingBox2.height &&
               boundingBox1.y + boundingBox1.height > boundingBox2.y;
    }
}
