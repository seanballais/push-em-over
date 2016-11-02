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

    public void updatePlatforms ()
    {
        int originY = gprefs.getHeightResolution () + platforms.get ( 0 ).getTextureHeight ();
        for ( Platform p : platforms ) {
            if ( ( int ) p.getBody ().getPosition ().y < -platforms.get ( 0 ).getTextureHeight () ) {
                int randomX = new Random ().nextInt ( 10 ) * ( gprefs.getWidthResolution () / 10 );
                p.getBody ().setTransform ( ( float ) randomX, ( float ) originY, 0f );
            }
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
            platformDef.type = BodyDef.BodyType.KinematicBody;

            platforms.add ( new Platform( randomX, randomY, 1, gameWorld, platformDef ) );
        }
    }
}
