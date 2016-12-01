package com.peo.core.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.peo.utils.GamePreferences;

public class Background extends Actor
{
    private TextureRegion[] screenBgFrames;
    private GamePreferences gamePreferences;
    private int bgFrameCount;
    private int frameTimer;

    public Background ()
    {
        screenBgFrames = new TextureRegion[8];
        gamePreferences = new GamePreferences();
        bgFrameCount = 0;
        frameTimer = 20;

        int frameNumber;
        int frameXPos;
        int frameYPos;
        int numIter = 0;
        Texture screenBgSpriteSheet = new Texture(Gdx.files.internal("img/main-menu/bg/bg-spritesheet.jpg"));
        for ( int frameRowCount = 0; frameRowCount < 2; frameRowCount++ ) {
            for ( int frameCount = 0; frameCount < 4; frameCount++ ) {
                frameNumber = numIter++;
                frameXPos = frameCount * 1366;
                frameYPos = frameRowCount * 768;
                screenBgFrames [ frameNumber ] = new TextureRegion (
                        screenBgSpriteSheet, frameXPos, frameYPos, 1366, 768
                );
            }
        }
    }

    @Override public void draw ( Batch batch, float parentAlpha )
    {
        if ( frameTimer <= 0 ) {
            bgFrameCount = ( bgFrameCount + 1 ) % 7;
            frameTimer = 20;
        }

        batch.draw (
                screenBgFrames [ bgFrameCount ], 0, 0,
                gamePreferences.getWidthResolution (),
                gamePreferences.getHeightResolution ()
        );
    }

    @Override public void act ( float delta )
    {
        frameTimer--;
    }
}
