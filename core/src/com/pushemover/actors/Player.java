package com.pushemover.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Player extends Actor
{
    private static Texture playerTexture;
    private int x;
    private int y;

    public Player ( int x, int y )
    {
        playerTexture = new Texture(Gdx.files.internal("data/img/game_screen/player.png"));
        this.x = x;
        this.y = y;
    }

    @Override public void draw ( Batch batch, float alpha )
    {
        batch.draw ( playerTexture, x, y );
    }

    @Override public void act ( float delta )
    {
        y -= 3;
    }
}