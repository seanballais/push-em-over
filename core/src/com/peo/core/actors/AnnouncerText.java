package com.peo.core.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.peo.utils.GamePreferences;

public class AnnouncerText extends Actor
{
    private Texture textSpritesheet;
    private GamePreferences gamePreferences;
    private TextureRegion [] frames;
    private Animation announcementText;
    private float elapsedTime;

    public AnnouncerText ()
    {
        textSpritesheet = new Texture ( Gdx.files.internal ( "img/actors/game-start.png" ) );
        gamePreferences = new GamePreferences ();
        frames = new TextureRegion [ 3 ];
        elapsedTime = 0f;

        for ( int frameCount = 0; frameCount < frames.length; frameCount++ ) {
            frames [ frameCount ] = new TextureRegion (
                    textSpritesheet, 0, frameCount * 130, textSpritesheet.getWidth (), 130
            );
        }

        announcementText = new Animation ( 1f, frames );
    }

    public boolean isAnimationDone ()
    {
        return announcementText.isAnimationFinished ( elapsedTime );
    }

    public void reset ()
    {
        elapsedTime = 0;
    }

    @Override public void draw ( Batch batch, float parentAlpha )
    {
        batch.draw (
            announcementText.getKeyFrame ( elapsedTime, false ),
            ( gamePreferences.getWidthResolution () / 2 ) - ( textSpritesheet.getWidth () / 2 ),
            gamePreferences.getHeightResolution () / 2
        );
    }

    @Override public void act ( float delta )
    {
        elapsedTime += Gdx.graphics.getDeltaTime ();
    }
}
