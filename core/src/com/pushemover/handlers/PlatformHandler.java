package com.pushemover.handlers;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
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

    public ArrayList < Platform > getPlatforms ( World gameWorld )
    {
        setPlatforms ( 20, gameWorld );
        return platforms;
    }

    public void loopPosition ( Platform platform )
    {
        if ( platform.y < ( -platform.getTextureHeight ()) ) {
            int screenWidth = gprefs.getWidthResolution ();
            int randomX = new Random ().nextInt ( 10 ) * ( screenWidth / 10 );

            platform.x = randomX;
            platform.y = gprefs.getHeightResolution () + platform.getTextureHeight ();
        }
    }

    private void setPlatforms ( int numPlatforms, World gameWorld )
    {
        BodyDef platformDef = new BodyDef ();

        int screenHeight = gprefs.getHeightResolution ();
        int screenWidth = gprefs.getWidthResolution ();
        for ( int ctr = 0; ctr < numPlatforms; ctr++ ) {
            int randomX = new Random ().nextInt ( 10 ) * ( screenWidth / 10 );
            int randomY = new Random ().nextInt ( 5 ) * ( screenHeight / 5 );
            platformDef.position.set ( ( float ) randomX, ( float ) randomY );

            platforms.add ( new Platform( randomX, randomY, 1, gameWorld, platformDef ) );
        }
    }
}
