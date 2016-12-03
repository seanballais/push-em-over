package com.peo.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.peo.core.actors.Background;
import com.peo.core.actors.GenericPlayer;
import com.peo.core.managers.PlatformManager;

import java.awt.*;

public class GameScreenWorld
{
    private Background background;
    private GenericPlayer player1;
    private GenericPlayer player2;
    private PlatformManager platformManager;
    private World physicsWorld;
    private Stage playStage;

    public GameScreenWorld ()
    {
        playStage = new Stage ();
        physicsWorld = new World ( new Vector2 ( 0f, -20f ), true );

        background = new Background ();
        playStage.addActor ( background );

        platformManager = new PlatformManager ( physicsWorld );
        platformManager.setPlatforms ( playStage );

        Point p1Pos = platformManager.getHighestPoints () [ 0 ];
        Point p2Pos = platformManager.getHighestPoints () [ 1 ];
        player1 = new GenericPlayer (
                physicsWorld, "Bob", new Color ( 0f, 102/255f, 204/255f, 1f ), p1Pos.x, p1Pos.y
        );
        player2 = new GenericPlayer (
                physicsWorld, "Joe", new Color ( 0f, 102/255f, 204/255f, 1f ), p2Pos.x, p2Pos.y
        );

        playStage.addActor ( player1 );
        playStage.addActor ( player2 );
    }

    public void update ( float delta )
    {
        Gdx.app.log ( "GameScreenWorld", "update" );

        physicsWorld.step ( Gdx.graphics.getDeltaTime (), 6, 2 );
        player1.setXPos ( Math.round ( player1.getPlayerPhysicsBody ().getPosition ().x ) );
        player1.setYPos ( Math.round ( player1.getPlayerPhysicsBody ().getPosition ().y ) );
        player2.setXPos ( Math.round ( player2.getPlayerPhysicsBody ().getPosition ().x ) );
        player2.setYPos ( Math.round ( player2.getPlayerPhysicsBody ().getPosition ().y ) );
        platformManager.updatePlatformPositions ();
    }

    public Stage getPlayStage ()
    {
        return playStage;
    }
    public World getPhysicsWorld () { return physicsWorld; }
}
