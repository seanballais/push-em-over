package com.peo.core.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.peo.utils.GamePreferences;

public class AnnouncerText extends Actor
{
    private Texture textSpritesheet;
    private GamePreferences gamePreferences;
    private TextureRegion [] frames;
    private int frameNum;
    private float timeDelta;

    public AnnouncerText ()
    {
        textSpritesheet = new Texture ( Gdx.files.internal ( "img/actors/game-start.png" ) );
        gamePreferences = new GamePreferences ();
        frames = new TextureRegion [ 3 ];
        frameNum = 0;
        timeDelta = 1250f;

        for ( int frameCount = 0; frameCount < frames.length; frameCount++ ) {
            frames [ frameCount ] = new TextureRegion (
                    textSpritesheet, 0, frameCount * 130, textSpritesheet.getWidth (), 130
            );
        }
    }

    @Override public void draw ( Batch batch, float parentAlpha )
    {
        if ( timeDelta <= 0 ) {
            timeDelta = 1250f;
            frameNum = Math.min ( frameNum + 1, 2 );
        } else {
            timeDelta -= Gdx.graphics.getDeltaTime ();
        }

        batch.draw (
            frames [ frameNum ],
            ( gamePreferences.getWidthResolution () / 2 ) - ( textSpritesheet.getWidth () / 2 ),
            gamePreferences.getHeightResolution () / 2
        );
    }
}
