package com.pushemover.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.awt.Rectangle;

public class Platform extends Actor
{
    private Texture platformTexture;
    private Rectangle boundingBox;
    private int x;
    private int y;

    public Platform ( int x, int y )
    {
        platformTexture = new Texture ( Gdx.files.internal ( "data/img/game_screen/platform.png" ) );
        boundingBox = new Rectangle ( x, y, platformTexture.getWidth (), platformTexture.getHeight () );
        this.x = x;
        this.y = y;
    }

    @Override public void draw ( Batch batch, float alpha )
    {
        batch.draw (platformTexture, x, y );
    }

    @Override public void act ( float delta )
    {
        y -= 2;
        boundingBox.x = x;
        boundingBox.y = y;
    }

    public int getTextureHeight ()
    {
        return platformTexture.getHeight ();
    }
    public int getXPos () { return x; }
    public int getYPos () { return y; }
    public Rectangle getBoundingBox () { return boundingBox; }
    public void setPos ( int x, int y )
    {
        this.x = x;
        this.y = y;
    }
}
