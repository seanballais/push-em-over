package com.peo.core.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.peo.utils.GamePreferences;

public class Locator extends Actor
{
    private Texture texture;
    private GamePreferences gamePreferences;
    private GenericPlayer playerRef;
    private int x;
    private int y;

    public Locator ( GenericPlayer playerRef, Texture locatorTexture )
    {
        texture = locatorTexture;
        gamePreferences = new GamePreferences ();
        this.playerRef = playerRef;
    }

    @Override public void draw ( Batch batch, float parentAlpha )
    {
        if ( playerRef.getYPos () > gamePreferences.getHeightResolution () + 90 || playerRef.getYPos () < -90 ||
             playerRef.getXPos () > gamePreferences.getWidthResolution () + 35 || playerRef.getXPos () < -35 ) {
            batch.draw (texture, x, y, 25, 25 );
        }
    }

    @Override public void act ( float delta )
    {
        if ( playerRef.getYPos () > gamePreferences.getHeightResolution () + 90 || playerRef.getYPos () < -90 ||
             playerRef.getXPos () > gamePreferences.getWidthResolution () + 35 || playerRef.getXPos () < -35 ) {
            // Player is off screen
            if ( playerRef.getYPos () > gamePreferences.getHeightResolution() ) {
                y = gamePreferences.getHeightResolution () - 30;
            } else if ( playerRef.getYPos() < -30 ) {
                y = 30;
            } else {
                y = playerRef.getYPos ();
            }

            if ( playerRef.getXPos () < -30 ) {
                x = 30;
            } else if ( playerRef.getXPos () > gamePreferences.getWidthResolution () ) {
                x = gamePreferences.getWidthResolution () - 30;
            } else {
                x = playerRef.getXPos ();
            }
        }
    }
}
