package com.pushemover.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Platform extends Actor
{
    private static Texture platform_texture;
    private int x;
    private int y;

    public Platform ( int x, int y )
    {
        platform_texture = new Texture(Gdx.files.internal("data/img/game_screen/platform.png"));
        this.x = x;
        this.y = y;
    }

    @Override public void draw ( Batch batch, float alpha )
    {
        batch.draw ( platform_texture, x, y );
    }

    @Override public void act ( float delta )
    {
        y -= 2;
    }

    public int getTextureHeight ()
    {
        return platform_texture.getHeight ();
    }
    public int getXPos () { return x; }
    public int getYPos () { return y; }
    public void setPos ( int x, int y )
    {
        this.x = x;
        this.y = y;
    }
}
