package com.peo.core.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.peo.utils.GamePreferences;

public class TransparentBackground extends Actor
{
    private static Texture transparentBackground;
    private GamePreferences gamePreferences;

    public TransparentBackground ()
    {
        transparentBackground = new Texture ( Gdx.files.internal ( "img/actors/bg-result.png" ) );
        gamePreferences = new GamePreferences();
    }

    @Override public void draw ( Batch batch, float parentAlpha )
    {
        batch.draw ( transparentBackground, 0, 0, gamePreferences.getWidthResolution (), gamePreferences.getHeightResolution () );
    }

    @Override public void act ( float delta ) {}
}
