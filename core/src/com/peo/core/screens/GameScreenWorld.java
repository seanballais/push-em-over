package com.peo.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.peo.core.actors.Background;
import com.peo.core.actors.GenericPlayer;
import com.peo.core.managers.PlatformManager;
import com.peo.core.managers.TrapManager;
import com.peo.utils.GamePreferences;
import com.peo.utils.Physics;

public class GameScreenWorld
{
    private Background background;
    private GenericPlayer player1;
    private GenericPlayer player2;
    private PlatformManager platformManager;
    private TrapManager trapManager;
    private GamePreferences gamePreferences;
    private World physicsWorld;
    private Stage playStage;
    private OrthographicCamera gameCamera;

    public GameScreenWorld ()
    {
        gamePreferences = new GamePreferences();

        playStage = new Stage ();
        physicsWorld = new World ( new Vector2 ( 0f, -20f ), true );

        background = new Background ();
        playStage.addActor ( background );

        platformManager = new PlatformManager ( physicsWorld );
        platformManager.setPlatforms ( playStage );

        int p1XPos = platformManager.getXPoints () [ 0 ];
        int p2XPos = platformManager.getXPoints () [ 1 ];
        player1 = new GenericPlayer (
                physicsWorld, "Bob", new Color ( 0f, 102/255f, 204/255f, 1f ),
                p1XPos,
                gamePreferences.getHeightResolution () + 50
        );
        player2 = new GenericPlayer (
                physicsWorld, "Joe", new Color ( 0f, 102/255f, 204/255f, 1f ),
                p2XPos,
                gamePreferences.getHeightResolution () + 50
        );

        playStage.addActor ( player1 );
        playStage.addActor ( player2 );

        trapManager = new TrapManager ( physicsWorld );
        trapManager.setTraps ( playStage );

        gameCamera = new OrthographicCamera ();
        gameCamera.setToOrtho ( false, gamePreferences.getWidthResolution (), gamePreferences.getHeightResolution () );
    }

    public void update ( float delta )
    {
        Gdx.app.log ( "GameScreenWorld", "update" );

        float impulse = player1.getPlayerPhysicsBody ().getMass () * 0.35f;
        if ( Gdx.input.isKeyPressed ( GamePreferences.getInstance().getLeftKey ( 0 ) ) ) {
            player1.getPlayerPhysicsBody ().applyLinearImpulse (
                    new Vector2 ( -impulse, 0 ),
                    player1.getPlayerPhysicsBody ().getWorldCenter (),
                    true
            );
        }

        if ( Gdx.input.isKeyPressed ( GamePreferences.getInstance().getLeftKey ( 1 ) ) ) {
            player2.getPlayerPhysicsBody ().applyLinearImpulse (
                    new Vector2 ( -impulse, 0 ),
                    player2.getPlayerPhysicsBody ().getWorldCenter (),
                    true
            );
        }

        if ( Gdx.input.isKeyPressed ( GamePreferences.getInstance().getRightKey ( 0 ) ) ) {
            player1.getPlayerPhysicsBody ().applyLinearImpulse (
                    new Vector2 ( impulse, 0 ),
                    player1.getPlayerPhysicsBody ().getWorldCenter (),
                    true
            );
        }

        if ( Gdx.input.isKeyPressed ( GamePreferences.getInstance().getRightKey ( 1 ) ) ) {
            player2.getPlayerPhysicsBody ().applyLinearImpulse (
                    new Vector2 ( impulse, 0 ),
                    player2.getPlayerPhysicsBody ().getWorldCenter (),
                    true
            );
        }

        if ( Gdx.input.isKeyPressed ( GamePreferences.getInstance().getJumpKey ( 0 ) ) ) {
            player1.getPlayerPhysicsBody ().applyLinearImpulse (
                    new Vector2 ( 0, impulse * 2 ),
                    player1.getPlayerPhysicsBody ().getWorldCenter (),
                    true
            );
        }

        if ( Gdx.input.isKeyPressed ( GamePreferences.getInstance().getJumpKey ( 1 ) ) ) {
            player2.getPlayerPhysicsBody ().applyLinearImpulse (
                    new Vector2 ( 0, impulse * 2 ),
                    player2.getPlayerPhysicsBody ().getWorldCenter (),
                    true
            );
        }

        physicsWorld.step ( Gdx.graphics.getDeltaTime (), 6, 2 );
        player1.setXPos ( Math.round ( player1.getPlayerPhysicsBody ().getPosition ().x * Physics.PPM ) );
        player1.setYPos ( Math.round ( player1.getPlayerPhysicsBody ().getPosition ().y * Physics.PPM ) );
        player2.setXPos ( Math.round ( player2.getPlayerPhysicsBody ().getPosition ().x * Physics.PPM ) );
        player2.setYPos ( Math.round ( player2.getPlayerPhysicsBody ().getPosition ().y * Physics.PPM ) );
        platformManager.updatePlatformPositions ();
        trapManager.updateTrapPositions ();

        gameCamera.update ();
    }

    public Stage getPlayStage ()
    {
        return playStage;
    }
    public World getPhysicsWorld () { return physicsWorld; }
    public Camera getGameCamera () { return gameCamera; }
}
