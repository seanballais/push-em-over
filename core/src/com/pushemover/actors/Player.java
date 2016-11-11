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
    private int deltaY;
    private boolean inContact;

    public Player ( int x, int y )
    {
        playerTexture = new Texture(Gdx.files.internal("data/img/game_screen/player.png"));
        boundingBox = new Rectangle ( x, y, playerTexture.getWidth (), playerTexture.getHeight () );
        deltaY = 3;
        inContact = false;
        this.x = x;
        this.y = y;
    }

    @Override public void draw ( Batch batch, float alpha )
    {
        batch.draw ( playerTexture, x, y );
    }
    @Override public void act ( float delta )
    {
        y -= deltaY;
        boundingBox.x = x;
        boundingBox.y = y;
    }

    public void moveXPos ( int x )
    {
        this.x += x;
    }
    public Rectangle getBoundingBox () { return boundingBox; }
    public void setDeltaY ( int deltaY) { this.deltaY = deltaY; }
    public boolean isInContact () { return inContact; }
    public void setInContact ( boolean contactState ) { this.inContact = contactState; }
}