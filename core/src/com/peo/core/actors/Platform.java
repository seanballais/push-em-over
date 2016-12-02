package com.peo.core.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Platform extends Actor
{
    private static Texture platformTexture;
    private int x;
    private int y;
    private int width;
    private int height;

    public Platform ( int x, int y )
    {
        platformTexture = new Texture ( Gdx.files.internal ( "img/actors/platform.png" ) );
        this.x = x;
        this.y = y;
        this.width = platformTexture.getWidth ();
        this.height = platformTexture.getHeight ();
    }

    @Override public void draw ( Batch batch, float parentAlpha )
    {
        batch.draw ( platformTexture, x, y, width + ( width / 2 ), height + ( height / 3 ) );
    }
}
