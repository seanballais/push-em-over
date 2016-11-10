package com.pushemover.handlers;

import com.pushemover.actors.Platform;
import com.pushemover.preferences.GamePreferences;

import java.util.ArrayList;
import java.util.Random;

public class PlatformHandler
{
    private ArrayList < Platform > platforms;
    private GamePreferences gprefs;

    public PlatformHandler ()
    {
        platforms = new ArrayList < Platform > ();
        gprefs = new GamePreferences ();
    }

    public ArrayList < Platform > getPlatforms ()
    {
        return platforms;
    }

    public void updatePlatforms ()
    {
        int screenSpawnPointY = gprefs.getHeightResolution () + platforms.get ( 0 ).getTextureHeight ();
        int screenBottomLimit = -platforms.get ( 0 ).getTextureHeight () ;
        int randomXPos;
        for ( Platform p : platforms ) {
            if ( p.getYPos () < screenBottomLimit ) {
                randomXPos = new Random ().nextInt ( 10 ) * ( gprefs.getWidthResolution () / 10 );
                p.setPos ( randomXPos, screenSpawnPointY );
            }
        }
    }

    public void setPlatforms ( int numPlatforms )
    {
        int screenHeight = gprefs.getHeightResolution ();
        int screenWidth = gprefs.getWidthResolution ();
        int randomXPos;
        int randomYPos;
        for ( int ctr = 0; ctr < numPlatforms; ctr++ ) {
            randomXPos = new Random ().nextInt ( 10 ) * ( screenWidth / 10 );
            randomYPos = new Random ().nextInt ( 5 ) * ( screenHeight / 5 );

            platforms.add ( new Platform( randomXPos, randomYPos ) );
        }
    }
}
