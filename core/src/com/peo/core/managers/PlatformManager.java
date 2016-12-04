package com.peo.core.managers;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.peo.core.actors.Platform;
import com.peo.utils.GamePreferences;
import com.peo.utils.Physics;

import java.util.ArrayList;
import java.util.Random;

public class PlatformManager
{
    private GamePreferences gamePreferences;
    private int [] highestX;
    private World physicsWorldRef;
    private ArrayList < Platform > platforms;
    public int numPlatforms;

    public PlatformManager ( World physicsWorldRef )
    {
        this.physicsWorldRef = physicsWorldRef;
        platforms = new ArrayList < Platform > ();
        gamePreferences = new GamePreferences ();
        highestX = new int [ 2 ];
        highestX [ 0 ] = 0;
        highestX [ 1 ] = 0;
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

            if ( ctr == 0 ) {
                highestX [ 0 ] = randomXPos;
            } else if ( ctr == 1 ) {
                highestX [ 1 ] = randomXPos;
            }

            platforms.add ( new Platform( physicsWorldRef, randomXPos, randomYPos ) );
            playStage.addActor ( platforms.get ( ctr ) );
        }
    }

    public int [] getXPoints ()
    {
        return highestX;
    }

    public void updatePlatformPositions ()
    {
        for ( Platform platform : platforms ) {
            platform.setXPos ( Math.round ( platform.getPlatformPhysicsBody ().getPosition ().x * Physics.PPM ) );
            platform.setYPos ( Math.round ( platform.getPlatformPhysicsBody ().getPosition ().y * Physics.PPM ) );
        }
    }
}
