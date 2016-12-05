package com.peo.core.managers;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.peo.core.actors.Trap;
import com.peo.utils.GamePreferences;
import com.peo.utils.Physics;

import java.util.ArrayList;
import java.util.Random;

public class TrapManager
{
    private GamePreferences gamePreferences;
    private World physicsWorldRef;
    private ArrayList < Trap > traps;
    public int numPlatforms;

    public TrapManager ( World physicsWorldRef )
    {
        this.physicsWorldRef = physicsWorldRef;
        traps = new ArrayList < Trap > ();
        gamePreferences = new GamePreferences ();
        numPlatforms = 12;
    }

    public void setTraps ( Stage playStage )
    {
        int screenHeight = gamePreferences.getHeightResolution ();
        int screenWidth = gamePreferences.getWidthResolution ();
        int randomXPos;
        int randomYPos;
        for ( int ctr = 0; ctr < numPlatforms; ctr++ ) {
            randomXPos = new Random ().nextInt ( 10 ) * ( screenWidth / 10 ) + 25;
            randomYPos = new Random ().nextInt ( 5 ) * ( screenHeight / 5 ) + 10;

            traps.add ( new Trap ( physicsWorldRef, randomXPos, randomYPos ) );
            playStage.addActor ( traps.get ( ctr ) );
        }
    }

    public ArrayList <Trap> getTraps ()
    {
        return traps;
    }

    public void updateTrapPositions ()
    {
        for ( Trap trap : traps) {
            trap.setXPos ( Math.round ( trap.getTrapPhysicsBody ().getPosition ().x * Physics.PPM ) );
            trap.setYPos ( Math.round ( trap.getTrapPhysicsBody ().getPosition ().y * Physics.PPM ) );
        }
    }
}