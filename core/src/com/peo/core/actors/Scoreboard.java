package com.peo.core.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.peo.utils.GamePreferences;

public class Scoreboard extends Actor
{
    private BitmapFont scoreFont;
    private GamePreferences gamePreferences;
    private int [] score;

    public Scoreboard ()
    {
        score = new int [ 2 ];
        scoreFont = new BitmapFont ();
        scoreFont.setColor ( Color.WHITE );
        scoreFont.getData ().setScale ( 1.5f );

        gamePreferences = new GamePreferences ();
    }

    public void setPlayer1 ( int score )
    {
        this.score [ 0 ] = score;
    }

    public void setPlayer2 ( int score )
    {
        this.score [ 1 ] = score;
    }

    @Override public void draw ( Batch batch, float parentAlpha )
    {
        scoreFont.draw (
                batch,
                "Bob: " + score [ 0 ] + " Joe: " + score [ 1 ],
                50,
                gamePreferences.getHeightResolution () - 50
        );
    }
}
