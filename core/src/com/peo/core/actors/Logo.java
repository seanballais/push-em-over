package com.peo.core.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.peo.utils.GamePreferences;

public class Logo extends Actor
{
    private Texture logo;
    private GamePreferences gamePreferences;

    public Logo ()
    {
        logo = new Texture ( "img/actors/logo.png" );
        gamePreferences = new GamePreferences ();
    }

    @Override public void draw ( Batch batch, float parentAlpha ) {
        batch.draw (
            logo,
            ( gamePreferences.getWidthResolution () / 2 ) - ( logo.getWidth () / 2 ),
            ( gamePreferences.getHeightResolution () / 2 ) - ( logo.getWidth () / 2 )
        );
    }
}
