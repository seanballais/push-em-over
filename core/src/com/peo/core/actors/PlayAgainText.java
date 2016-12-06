package com.peo.core.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.peo.utils.GamePreferences;

public class PlayAgainText extends Actor
{
    private Texture playAgainTexture;
    private GamePreferences gamePreferences;

    public PlayAgainText ()
    {
        playAgainTexture = new Texture ( Gdx.files.internal ( "img/actors/play-again.png" ) );
        gamePreferences = new GamePreferences ();
    }

    @Override public void draw ( Batch batch, float parentAlpha )
    {
        batch.draw (
            playAgainTexture,
            ( gamePreferences.getWidthResolution () / 2 ) - ( playAgainTexture.getWidth () / 2 ),
            Math.round ( gamePreferences.getHeightResolution () * 0.25 )
        );
    }
}
