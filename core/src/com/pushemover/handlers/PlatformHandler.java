package com.pushemover.handlers;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.pushemover.actors.Platform;
import com.pushemover.preferences.GamePreferences;
import com.pushemover.utils.Constants;
import com.pushemover.utils.Physics;

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
        float originY = gprefs.getHeightResolution () + platforms.get ( 0 ).getTextureHeight ();
        float originYMeters = Physics.toMeters ( originY );
        for ( Platform p : platforms ) {
            if ( ( int ) p.getBody ().getPosition ().y < -platforms.get ( 0 ).getTextureHeight () ) {
                float randomX = new Random ().nextInt ( 10 ) * ( gprefs.getWidthResolution () / 10 );
                float randomXMeters = Physics.toMeters ( randomX );
                p.getBody ().setTransform ( randomXMeters, originYMeters, 0f );
            }
        }
    }

    public void setPlatforms ( int numPlatforms, World gameWorld )
    {
        BodyDef platformDef = new BodyDef ();

        int screenHeight = gprefs.getHeightResolution ();
        int screenWidth = gprefs.getWidthResolution ();
        for ( int ctr = 0; ctr < numPlatforms; ctr++ ) {
            float randomX = new Random ().nextInt ( 10 ) * ( screenWidth / 10 );
            float randomY = new Random ().nextInt ( 5 ) * ( screenHeight / 5 );
            float randomXMeters = Physics.toMeters ( randomX );
            float randomYMeters = Physics.toMeters ( randomY );
            platformDef.position.set ( randomXMeters, randomYMeters );
            platformDef.type = BodyDef.BodyType.KinematicBody;

            platforms.add ( new Platform( gameWorld, platformDef ) );
        }
    }
}
