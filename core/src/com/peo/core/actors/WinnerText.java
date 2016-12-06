package com.peo.core.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.peo.utils.GamePreferences;

public class WinnerText extends Actor
{
    private Texture winnerTextTexture; // Bad fookeen idea, Sean! - Sean (Dec. 6, 2016)
    private GamePreferences gamePreferences;
    private GenericPlayer playerRef;

    public WinnerText ( Texture winnerTexture, GenericPlayer player )
    {
        winnerTextTexture = winnerTexture;
        gamePreferences = new GamePreferences ();
        playerRef = player;
    }

    @Override public void draw ( Batch batch, float parentAlpha )
    {
        if ( playerRef.isAlive () ) {
            batch.draw (
                    winnerTextTexture,
                    ( gamePreferences.getWidthResolution () / 2 ) - ( winnerTextTexture.getWidth () / 2 ),
                    ( gamePreferences.getHeightResolution () / 2 ) - ( winnerTextTexture.getHeight () / 2 )
            );
        }
    }
}
