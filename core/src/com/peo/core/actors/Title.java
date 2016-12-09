package com.peo.core.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.peo.utils.GamePreferences;

public class Title extends Actor
{
    private GamePreferences gamePreferences;
    private Texture titleTexture;

    public Title ()
    {
        titleTexture = new Texture ( Gdx.files.internal ( "img/actors/title.png" ) );
        gamePreferences = new GamePreferences ();
    }

    @Override public void draw ( Batch batch, float parentAlpha )
    {
        batch.draw (
            titleTexture,
            ( gamePreferences.getWidthResolution () / 2 ) - ( titleTexture.getWidth () / 2 ),
            gamePreferences.getHeightResolution () - ( titleTexture.getHeight () + 90 )
        );
    }
}
