package com.peo.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.peo.core.actors.*;
import com.peo.core.managers.PlatformManager;
import com.peo.core.managers.TrapManager;
import com.peo.utils.GamePreferences;
import com.peo.utils.Physics;

import java.util.Random;

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
    private Stage resultStage;
    private Stage countdownStage;
    private Stage exitStage;
    private OrthographicCamera gameCamera;
    private Music levelMusic;
    private GameScreenStateEnum screenState;
    private AnnouncerText announcerText;
    private Dialog exitDialog;
    private float deltaTime;
    private int p1XPos;
    private int p2XPos;
    private boolean exitDialogShowing;
    private Controller controller;

    public GameScreenWorld ()
    {
        gamePreferences = new GamePreferences();

        screenState = GameScreenStateEnum.COUNTDOWN;

        playStage = new Stage ();
        resultStage = new Stage ();
        countdownStage = new Stage ();
        physicsWorld = new World ( new Vector2 ( 0f, -10f ), true );
        exitDialogShowing = false;

        for ( Controller c : Controllers.getControllers () ) {
            controller = c;
        }

        background = new Background ();
        playStage.addActor ( background );

        platformManager = new PlatformManager ( physicsWorld );
        platformManager.setPlatforms ( playStage );

        p1XPos = platformManager.getXPoints () [ 0 ];
        p2XPos = platformManager.getXPoints () [ 1 ];
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

        resultStage.addActor ( new TransparentBackground () );
        resultStage.addActor ( new Title () );
        resultStage.addActor ( new PlayAgainText () );
        resultStage.addActor(
            new WinnerText ( new Texture ( Gdx.files.internal ( "img/actors/bob-wins.png" ) ), player1 )
        );
        resultStage.addActor(
            new WinnerText ( new Texture ( Gdx.files.internal ( "img/actors/joe-wins.png" ) ), player2 )
        );

        announcerText = new AnnouncerText ();

        countdownStage.addActor ( new TransparentBackground () );
        countdownStage.addActor ( announcerText );

        gameCamera = new OrthographicCamera ();
        gameCamera.setToOrtho ( false, gamePreferences.getWidthResolution (), gamePreferences.getHeightResolution () );

        deltaTime = 3500f;

        exitDialog = new Dialog (
            "Confirm Exit...",
            new Skin (
                Gdx.files.internal ( "skins/x2/uiskin.json" ),
                new TextureAtlas ( Gdx.files.internal ( "skins/x2/uiskin.atlas" ) )
            )
        ) {
            @Override protected void result ( Object object ) {
                if ( ( Boolean ) object ) {
                    Gdx.app.exit ();
                }
            }

            @Override
            public float getPrefHeight() {
                return 550f;
            }
        }.text ( "Are you sure you want to exit?" )
         .button ( "Exit", true )
         .button ( "Cancel", false );
        exitStage = new Stage ();

        physicsWorld.setContactListener ( new ContactListener () {
            @Override
            public void beginContact ( Contact contact ) {
                for ( Trap trap : trapManager.getTraps () ) {
                    if ( ( contact.getFixtureA ().getBody () == player1.getPlayerPhysicsBody () &&
                            contact.getFixtureB ().getBody () == trap.getTrapPhysicsBody () ) ||
                            ( contact.getFixtureB ().getBody () == player1.getPlayerPhysicsBody () &&
                                contact.getFixtureA ().getBody () == trap.getTrapPhysicsBody () ) ) {
                        player1.setPlayerState ( PlayerStateEnum.FALLING );
                        player1.kill ();

                        screenState = GameScreenStateEnum.RESULT;
                    }

                    if ( ( contact.getFixtureA ().getBody () == player2.getPlayerPhysicsBody () &&
                            contact.getFixtureB ().getBody () == trap.getTrapPhysicsBody () ) ||
                            ( contact.getFixtureB ().getBody () == player2.getPlayerPhysicsBody () &&
                                contact.getFixtureA ().getBody () == trap.getTrapPhysicsBody () ) ) {
                        player2.setPlayerState ( PlayerStateEnum.FALLING );
                        player2.kill ();

                        screenState = GameScreenStateEnum.RESULT;
                    }
                }
            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });

        levelMusic = Gdx.audio.newMusic ( Gdx.files.internal ( "audio/bg-music-2.mp3" ) );
        levelMusic.setLooping ( true );
        levelMusic.setVolume ( 0.35f );
        levelMusic.play ();
    }

    public void update ( float delta )
    {
        if ( announcerText.isAnimationDone () && screenState == GameScreenStateEnum.COUNTDOWN ) {
            screenState = GameScreenStateEnum.PLAY;
        }

        if ( player1.isAlive () && player2.isAlive () && screenState != GameScreenStateEnum.COUNTDOWN ) {
            float impulse = player1.getPlayerPhysicsBody ().getMass () * 0.30f;

            if ( controller != null ) {
                if ( controller.getAxis ( 0 ) <= -1.0f ) {
                    player1.getPlayerPhysicsBody ().applyLinearImpulse (
                            new Vector2 ( -impulse, 0 ),
                            player1.getPlayerPhysicsBody ().getWorldCenter (),
                            true
                    );
                }

                if ( controller.getAxis ( 0 ) >= 1.0f ) {
                    player1.getPlayerPhysicsBody ().applyLinearImpulse (
                            new Vector2 ( impulse, 0 ),
                            player1.getPlayerPhysicsBody ().getWorldCenter (),
                            true
                    );
                }

                if ( controller.getButton ( 0 ) ) {
                    if ( player1.getFuelLength () < 100 && !player1.isCanFly () ) {
                        player1.setFuelLength ( Math.min ( player1.getFuelLength () + 1, 100 ) );
                    } else if ( player1.isCanFly () ) {
                        player1.getPlayerPhysicsBody().applyLinearImpulse(
                                new Vector2(0, impulse * 2),
                                player1.getPlayerPhysicsBody().getWorldCenter(),
                                true
                        );

                        player1.setFuelLength ( Math.max ( player1.getFuelLength () - 2, 0 ) );
                    }
                } else {
                    if ( player1.getFuelLength () < 100 ) {
                        player1.setFuelLength ( Math.min ( player1.getFuelLength () + 1, 100 ) );
                    }
                }


            }

            if ( controller == null && Gdx.input.isKeyPressed ( GamePreferences.getInstance().getLeftKey ( 0 ) ) ) {
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

            if ( controller == null && Gdx.input.isKeyPressed ( GamePreferences.getInstance().getRightKey ( 0 ) ) ) {
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

            if ( controller == null && Gdx.input.isKeyPressed ( GamePreferences.getInstance().getJumpKey ( 0 ) ) ) {
                if ( player1.getFuelLength () < 100 && !player1.isCanFly () ) {
                    player1.setFuelLength ( Math.min ( player1.getFuelLength () + 1, 100 ) );
                } else if ( player1.isCanFly () ) {
                    player1.getPlayerPhysicsBody().applyLinearImpulse(
                            new Vector2(0, impulse * 2),
                            player1.getPlayerPhysicsBody().getWorldCenter(),
                            true
                    );

                    player1.setFuelLength ( Math.max ( player1.getFuelLength () - 2, 0 ) );
                }
            }

            if ( Gdx.input.isKeyPressed ( GamePreferences.getInstance().getJumpKey ( 1 ) ) ) {
                if ( player2.getFuelLength () < 100 && !player2.isCanFly () ) {
                    player2.setFuelLength ( Math.min ( player2.getFuelLength () + 1, 100 ) );
                } else if ( player2.isCanFly () ) {
                    player2.getPlayerPhysicsBody ().applyLinearImpulse (
                            new Vector2 ( 0, impulse * 2 ),
                            player2.getPlayerPhysicsBody ().getWorldCenter (),
                            true
                    );

                    player2.setFuelLength ( Math.max ( player2.getFuelLength () - 2, 0 ) );
                }
            }

            if ( controller == null && !Gdx.input.isKeyPressed ( GamePreferences.getInstance ().getJumpKey ( 0 ) ) ) {
                if ( player1.getFuelLength () < 100 ) {
                    player1.setFuelLength ( Math.min ( player1.getFuelLength () + 1, 100 ) );
                }
            }

            if ( !Gdx.input.isKeyPressed ( GamePreferences.getInstance().getJumpKey ( 1 ) ) ) {
                if ( player2.getFuelLength () < 100 ) {
                    player2.setFuelLength ( Math.min ( player2.getFuelLength () + 1, 100 ) );
                }
            }

            if ( player1.getYPos () < -90 ) {
                player1.kill ();

                screenState = GameScreenStateEnum.RESULT;
            }

            if ( player2.getYPos () < -90 ) {
                player2.kill ();

                screenState = GameScreenStateEnum.RESULT;
            }

            physicsWorld.step ( Gdx.graphics.getDeltaTime (), 6, 2 );
            player1.setXPos ( Math.round ( player1.getPlayerPhysicsBody ().getPosition ().x * Physics.PPM ) );
            player1.setYPos ( Math.round ( player1.getPlayerPhysicsBody ().getPosition ().y * Physics.PPM ) );
            player2.setXPos ( Math.round ( player2.getPlayerPhysicsBody ().getPosition ().x * Physics.PPM ) );
            player2.setYPos ( Math.round ( player2.getPlayerPhysicsBody ().getPosition ().y * Physics.PPM ) );
            platformManager.updatePlatformPositions ();
            trapManager.updateTrapPositions ();
        }

        gameCamera.update ();

        if ( Gdx.input.isKeyPressed ( Input.Keys.ENTER ) && screenState == GameScreenStateEnum.RESULT ) {
            reset ();
        }

        if ( Gdx.input.isKeyPressed ( Input.Keys.ESCAPE ) && !exitDialogShowing ) {
            exitDialogShowing = true;
            Gdx.input.setInputProcessor ( exitStage );
            exitDialog.show ( exitStage );
        }
    }

    private void reset ()
    {
        int x1Pos = new Random().nextInt ( 10 ) * ( gamePreferences.getWidthResolution () / 10 );
        int x2Pos = new Random().nextInt ( 10 ) * ( gamePreferences.getWidthResolution () / 10 );

        player1.resurrect ();
        player2.resurrect ();
        player1.setPlayerState ( PlayerStateEnum.WALKING );
        player2.setPlayerState ( PlayerStateEnum.WALKING );
        player1.getPlayerPhysicsBody ().setTransform (
            ( float ) x1Pos / Physics.PPM,
            ( float ) ( gamePreferences.getHeightResolution () - 100 ) / Physics.PPM,
            player1.getPlayerPhysicsBody ().getAngle ()
        );
        player2.getPlayerPhysicsBody ().setTransform (
                ( float ) x2Pos / Physics.PPM,
                ( float ) ( gamePreferences.getHeightResolution () - 100 ) / Physics.PPM,
                player1.getPlayerPhysicsBody ().getAngle ()
        );
        player1.setYPos ( Math.round ( player1.getPlayerPhysicsBody ().getPosition ().y ) );
        player2.setYPos ( Math.round ( player2.getPlayerPhysicsBody ().getPosition ().y ) );
        player1.setFuelLength ( 100 );
        player2.setFuelLength ( 100 );
        player1.getPlayerPhysicsBody ().setLinearVelocity ( 0f, 1f );
        player2.getPlayerPhysicsBody ().setLinearVelocity ( 0f, 1f );

        announcerText.reset ();
        screenState = GameScreenStateEnum.COUNTDOWN;
    }

    public Stage getPlayStage ()
    {
        return playStage;
    }
    public Stage getResultStage () { return  resultStage; }
    public Stage getCountdownStage () { return countdownStage; }
    public Stage getExitStage () { return exitStage; }
    public GameScreenStateEnum getScreenState () { return screenState; }
}
