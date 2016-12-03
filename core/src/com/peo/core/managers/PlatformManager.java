package com.peo.core.managers;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.peo.core.actors.Platform;
import com.peo.utils.Box2DConversion;
import com.peo.utils.GamePreferences;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class PlatformManager
{
    private GamePreferences gamePreferences;
    private Point [] highestPoints;
    private World physicsWorldRef;
    private ArrayList < Platform > platforms;
    public int numPlatforms;

    public PlatformManager ( World physicsWorldRef )
    {
        this.physicsWorldRef = physicsWorldRef;
        platforms = new ArrayList < Platform > ();
        gamePreferences = new GamePreferences ();
        highestPoints = new Point [ 2 ];
        highestPoints [ 0 ] = new Point ( 0, 0 );
        highestPoints [ 1 ] = new Point ( 0, 0 );
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
            if ( randomYPos > highestPoints [ 1 ].y + 80 ) {
                // Plus 80 to compensate for the platform texture height.
                highestPoints [ 1 ].x = randomXPos + 80;
                highestPoints [ 1 ].y = randomYPos + 80;
            }

            if ( highestPoints [ 0 ].y < highestPoints [ 1 ].y ) {
                int tmpX = highestPoints [ 0 ].x;
                int tmpY = highestPoints [ 0 ].y;

                highestPoints [ 0 ].x = highestPoints [ 1 ].x;
                highestPoints [ 0 ].y = highestPoints [ 1 ].y;
                highestPoints [ 1 ].x = tmpX;
                highestPoints [ 1 ].y = tmpY;
            }

            platforms.add ( new Platform( physicsWorldRef, randomXPos, randomYPos ) );
            playStage.addActor ( platforms.get ( ctr ) );
        }
    }

    public Point [] getHighestPoints ()
    {
        return highestPoints;
    }

    public void updatePlatformPositions ()
    {
        for ( Platform platform : platforms ) {
            platform.setXPos ( Math.round ( platform.getPlatformPhysicsBody ().getPosition ().x ) );
            platform.setYPos ( Math.round ( platform.getPlatformPhysicsBody ().getPosition ().y ) );
        }
    }
}
