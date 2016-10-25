package com.pushemover.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Platform extends Actor {
    private Texture platform_texture;
    public int x;
    public int y;
    private int deltaY;

    public Platform ()
    {
        platform_texture = new Texture(Gdx.files.internal("data/img/game_screen/platform.png"));
        x = 0;
        y = 0;
        deltaY = 0;
    }

    public Platform ( int x, int y, int deltaY )
    {
        this.x = x;
        this.y = y;
        this.deltaY = deltaY;
    }

    @Override public void draw ( Batch batch, float alpha )
    {
        batch.draw ( platform_texture, x, y );
    }

    @Override public void act ( float delta )
    {
        //y -= deltaY;
    }
}
