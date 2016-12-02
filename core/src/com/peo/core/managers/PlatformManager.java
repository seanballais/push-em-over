package com.peo.core.managers;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.peo.core.actors.Platform;
import com.peo.utils.GamePreferences;

import java.util.ArrayList;
import java.util.Random;

public class PlatformManager
{
    private GamePreferences gamePreferences;
    public int numPlatforms;

    public PlatformManager ()
    {
        gamePreferences = new GamePreferences ();
        numPlatforms = 25;
    }

    public void setPlatforms ( Stage playStage )
    {
        int screenHeight = gamePreferences.getHeightResolution ();
        int screenWidth = gamePreferences.getWidthResolution ();
        int randomXPos;
        int randomYPos;
        for ( int ctr = 0; ctr < numPlatforms; ctr++ ) {
            randomXPos = new Random ().nextInt ( 10 ) * ( screenWidth / 10 );
            randomYPos = new Random ().nextInt ( 5 ) * ( screenHeight / 5 );

            playStage.addActor ( new Platform( randomXPos, randomYPos ) );
        }
    }
}
