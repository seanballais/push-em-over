package com.peo.core.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.peo.utils.GamePreferences;

public class MenuImage extends Actor
{
    public Texture image;
    public GamePreferences gamePreferences;

    public MenuImage ( String path )
    {
        image = new Texture ( Gdx.files.internal ( path ) );
        gamePreferences = new GamePreferences ();
    }

    @Override public void draw ( Batch batch, float parentAlpha )
    {
        batch.draw (
            image,
            ( gamePreferences.getWidthResolution () / 2 ) - ( image.getWidth () / 2 ),
            ( gamePreferences.getHeightResolution () / 2 ) - ( image.getHeight () / 2 )
        );
    }
}
