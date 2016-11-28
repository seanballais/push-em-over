package com.pushemover.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.awt.Rectangle;

public class Player extends Actor
{
    private Texture playerTexture;
    private Rectangle boundingBox;
    private int x;
    private int y;
    private int prevX;
    private int prevY;
    private int deltaX;
    private int deltaY;

    public Player ( int x, int y )
    {
        playerTexture = new Texture(Gdx.files.internal("data/img/game_screen/player.png"));
        boundingBox = new Rectangle ( x, y, playerTexture.getWidth (), playerTexture.getHeight () );
        deltaX = 0;
        deltaY = 7;
        this.x = x;
        this.y = y;
    }

    @Override public void draw ( Batch batch, float alpha )
    {
        batch.draw ( playerTexture, x, y );
    }
    @Override public void act ( float delta )
    {
        prevX = x;
        prevY = y;
        x += deltaX;
        y -= deltaY;
        boundingBox.x = x;
        boundingBox.y = y;
    }

    public void moveXPos ( int x )
    {
        this.deltaX = x;
    }
    public void adjustXPos ( int x ) { this.x = x; }
    public void adjustYPos ( int y ) { this.y = y; }
    public Rectangle getBoundingBox () { return boundingBox; }
    public int getPrevX () { return prevX; }
    public int getPrevY () { return prevY; }
    public int getPlayerX() { return x; }
    public int getPlayerY() { return y; }
}