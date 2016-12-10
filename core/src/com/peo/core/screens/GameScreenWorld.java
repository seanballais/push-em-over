package com.peo.core.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
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
import com.peo.core.managers.ScreenEnum;
import com.peo.core.managers.ScreenManager;
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
    private int p1Score;
    private int p2Score;
    private boolean movePlayer1;
    private boolean movePlayer2;
    private boolean exitDialogShowing;
    private boolean player1Hit;
    private boolean player2Hit;
    private Controller controller;
    private final Game game;
    private Scoreboard scoreboard;
    private Sound splatSound;
    private Sound hurraySound;
    private Locator player1Locator;
    private Locator player2Locator;

    public GameScreenWorld (final Game game )
    {
        this.game = game;
        gamePreferences = new GamePreferences();

        screenState = GameScreenStateEnum.COUNTDOWN;

        playStage = new Stage ();
        resultStage = new Stage ();
        countdownStage = new Stage ();
        physicsWorld = new World ( new Vector2 ( 0f, -10f ), true );
        exitDialogShowing = false;
        p1Score = 0;
        p2Score = 0;
        movePlayer1 = false;
        movePlayer2 = false;
        player1Hit = false;
        player2Hit = false;
        scoreboard = new Scoreboard ();
        splatSound = Gdx.audio.newSound ( Gdx.files.internal ( "audio/splat.mp3" ) );
        hurraySound = Gdx.audio.newSound ( Gdx.files.internal ( "audio/hurray.mp3" ) );

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

        player1Locator = new Locator ( player1, new Texture ( Gdx.files.internal ( "img/actors/locator.png" ) ) );
        player2Locator = new Locator ( player2, new Texture ( Gdx.files.internal ( "img/actors/locator-2.png" ) ) );

        playStage.addActor ( player1 );
        playStage.addActor ( player2 );

        trapManager = new TrapManager ( physicsWorld );
        trapManager.setTraps ( playStage );

        playStage.addActor ( player1Locator );
        playStage.addActor ( player2Locator );
        playStage.addActor ( scoreboard );

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
                    ScreenManager.getInstance ().show ( ScreenEnum.MAIN_SCREEN, game );
                    levelMusic.stop ();
                    levelMusic.dispose ();
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
                        player1Hit = true;
                    }

                    if ( ( contact.getFixtureA ().getBody () == player2.getPlayerPhysicsBody () &&
                            contact.getFixtureB ().getBody () == trap.getTrapPhysicsBody () ) ||
                            ( contact.getFixtureB ().getBody () == player2.getPlayerPhysicsBody () &&
                                contact.getFixtureA ().getBody () == trap.getTrapPhysicsBody () ) ) {
                        player2Hit = true;
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
        levelMusic.setVolume ( 0.5f );
        levelMusic.play ();
    }

    public void update ( float delta )
    {
        if ( announcerText.isAnimationDone () && screenState == GameScreenStateEnum.COUNTDOWN ) {
            screenState = GameScreenStateEnum.PLAY;
        }

        if ( player1.isAlive () && player2.isAlive () && screenState != GameScreenStateEnum.COUNTDOWN && screenState != GameScreenStateEnum.RESULT ) {
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

            if ( player1.getYPos () < -90 || player1Hit ) {
                player1.kill ();
                p2Score++;
                scoreboard.setPlayer2 ( p2Score );
                movePlayer1 = true;
            }

            if ( player2.getYPos () < -90 || player2Hit ) {
                player2.kill ();
                p1Score++;
                scoreboard.setPlayer1 ( p1Score );
                movePlayer2 = true;
            }

            if ( movePlayer1 ) {
                player1Hit = false;
                resetPlayer1 ();

                splatSound.play ( 0.4f );
            }

            if ( movePlayer2 ) {
                player2Hit = false;
                resetPlayer2 ();

                splatSound.play ( 0.4f );
            }

            if ( p1Score == 15 || p2Score == 15 ) {
                if ( p1Score > p2Score ) {
                    player2.kill ();
                } else if ( p1Score < p2Score ) {
                    player1.kill ();
                }

                screenState = GameScreenStateEnum.RESULT;
                hurraySound.play ( 0.8f );
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
            resetPlayer1 ();
            resetPlayer2 ();
            reset ();
            scoreboard.setPlayer1 ( 0 );
            scoreboard.setPlayer2 ( 0 );
            p1Score = 0;
            p2Score = 0;
            hurraySound.stop ();
        }

        if ( Gdx.input.isKeyPressed ( Input.Keys.ESCAPE ) ) {
            Gdx.input.setInputProcessor ( exitStage );
            exitDialog.show ( exitStage );
        }
    }

    private void resetPlayer1 ()
    {
        int x1Pos = new Random().nextInt ( 10 ) * ( gamePreferences.getWidthResolution () / 10 );

        player1.resurrect ();
        player1.setPlayerState ( PlayerStateEnum.WALKING );
        player1.getPlayerPhysicsBody ().setTransform (
                ( float ) x1Pos / Physics.PPM,
                ( float ) gamePreferences.getHeightResolution () / Physics.PPM,
                player1.getPlayerPhysicsBody ().getAngle ()
        );
        player1.setYPos ( Math.round ( player1.getPlayerPhysicsBody ().getPosition ().y ) );
        player1.setFuelLength ( 100 );
        player1.getPlayerPhysicsBody ().setLinearVelocity ( 0f, 1f );
        movePlayer1 = false;
    }

    private void resetPlayer2 ()
    {
        int x2Pos = new Random().nextInt ( 10 ) * ( gamePreferences.getWidthResolution () / 10 );
        player2.resurrect ();
        player2.setPlayerState ( PlayerStateEnum.WALKING );
        player2.getPlayerPhysicsBody ().setTransform (
                ( float ) x2Pos / Physics.PPM,
                ( float ) gamePreferences.getHeightResolution () / Physics.PPM,
                player1.getPlayerPhysicsBody ().getAngle ()
        );
        player2.setYPos ( Math.round ( player2.getPlayerPhysicsBody ().getPosition ().y ) );
        player2.setFuelLength ( 100 );
        player2.getPlayerPhysicsBody ().setLinearVelocity ( 0f, 1f );
        movePlayer2 = false;
    }

    private void reset ()
    {
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
