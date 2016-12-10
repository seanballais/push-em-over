package com.peo.utils;

import com.badlogic.gdx.graphics.OrthographicCamera;

import java.util.Random;

public class Rumble
{
    private float elapsed;
    private float duration;
    private float radius;
    private float randomAngle;
    private Random random;

    public Rumble ()
    {
        elapsed = 0;
        duration = 0;
        radius = 0;
        randomAngle = 0;
        random = new Random ();
    }

    public void shake ( float radius, float duration )
    {
        this.radius = radius;
        this.duration = duration / 1000f;
        this.randomAngle = random.nextFloat () % 360f;
    }

    public void update ( float delta, OrthographicCamera camera )
    {
        if ( elapsed < duration ) {
            radius *= 0.9f;
            randomAngle += 150 + random.nextFloat () % 60f;
            float x = ( float ) ( Math.sin ( randomAngle ) * radius );
            float y = ( float ) ( Math.cos ( randomAngle ) * radius );
            camera.translate ( -x, -y );

            elapsed += delta;
        } else {
            camera.position.x = 0;
            camera.position.y = 0;
        }
    }
}
