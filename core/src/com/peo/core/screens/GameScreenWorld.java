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
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
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
    private boolean processingP1Hit;
    private boolean processingP2Hit;
    private Controller controller;
    private final Game game;
    private Scoreboard scoreboard;
    private Sound splatSound;
    private Sound hurraySound;
    private Locator player1Locator;
    private Locator player2Locator;
    private FitViewport viewport;
    private Random random;

    public GameScreenWorld (final Game game )
    {
        this.game = game;
        gamePreferences = new GamePreferences();

        screenState = GameScreenStateEnum.COUNTDOWN;
        gameCamera = new OrthographicCamera ();
        gameCamera.setToOrtho ( false, gamePreferences.getWidthResolution (), gamePreferences.getHeightResolution () );
        viewport = new FitViewport (
            gamePreferences.getWidthResolution (),
            gamePreferences.getHeightResolution (),
            gameCamera
        );
        viewport.setScreenPosition ( 0, 0 );
        gameCamera.position.set (
                gamePreferences.getWidthResolution () / 2f,
                gamePreferences.getHeightResolution () / 2f,
                0
        );

        random = new Random ();

        playStage = new Stage ( viewport );
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
        processingP1Hit = false;
        processingP2Hit = false;
        scoreboard = new Scoreboard ();
        splatSound = Gdx.audio.newSound ( Gdx.files.internal ( "audio/splat.mp3" ) );
        hurraySound = Gdx.audio.newSound ( Gdx.files.internal ( "audio/hurray.mp3" ) );

        for ( Controller c : Controllers.getControllers () ) {
            controller = c;
        }

        background = new Background ();
        //playStage.addActor ( background );

        platformManager = new PlatformManager ( physicsWorld );
        platformManager.setPlatforms ( playStage );

        p1XPos = platformManager.getXPoints () [ 0 ];
        p2XPos = platformManager.getXPoints () [ 1 ];
        player1 = new GenericPlayer (
                physicsWorld, "Bob", new Color ( 0f, 102/255f, 204/255f, 1f ),
                p1XPos,
                gamePreferences.getHeightResolution () + 300,
                new Texture ( Gdx.files.internal ( "img/actors/player-spritesheet.png" ) )
        );
        player2 = new GenericPlayer (
                physicsWorld, "Joe", new Color ( 0f, 102/255f, 204/255f, 1f ),
                p2XPos,
                gamePreferences.getHeightResolution () + 300,
                new Texture ( Gdx.files.internal ( "img/actors/player-spritesheet-2.png" ) )
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

        deltaTime = 3500f;

        Skin dialogSkin = new Skin ();

        Pixmap pixmap = new Pixmap ( 100, 50, Pixmap.Format.RGBA8888 );
        pixmap.setColor ( new Color ( 111/255f, 169/255f, 235/255f, 1 ) );
        pixmap.fill ();

        dialogSkin.add ( "blue", new Texture ( pixmap ) );

        BitmapFont font = new BitmapFont ();
        font.getData ().setScale ( 1.5f );

        dialogSkin.add ( "default", font );

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle ();
        textButtonStyle.up = dialogSkin.newDrawable ( "blue", new Color ( 111/255f, 169/255f, 235/255f, 1 ) );
        textButtonStyle.down = dialogSkin.newDrawable ( "blue", new Color ( 111/255f, 169/255f, 235/255f, 1 ) );
        textButtonStyle.checked = dialogSkin.newDrawable ( "blue", new Color ( 111/255f, 169/255f, 235/255f, 1 ) );
        textButtonStyle.over = dialogSkin.newDrawable ( "blue", Color.LIGHT_GRAY );
        textButtonStyle.font = dialogSkin.getFont ( "default" );

        dialogSkin.add ( "default", textButtonStyle );

        Window.WindowStyle windowStyle = new Window.WindowStyle ();
        windowStyle.titleFont = font;
        windowStyle.background = dialogSkin.newDrawable ( "blue", new Color ( 111/255f, 169/255f, 235/255f, 1 ) );
        windowStyle.stageBackground = dialogSkin.newDrawable ( "blue", new Color ( 111/255f, 169/255f, 235/255f, 1 ) );
        windowStyle.titleFontColor = Color.WHITE;

        dialogSkin.add ( "default", windowStyle );

        Label.LabelStyle label = new Label.LabelStyle ();
        label.background = dialogSkin.newDrawable ( "blue", new Color ( 111/255f, 169/255f, 235/255f, 1 ) );
        label.font = font;
        label.fontColor = Color.WHITE;

        dialogSkin.add ( "default", label );

        exitDialog = new Dialog (
            "",
            dialogSkin
        ) {
            @Override protected void result ( Object object ) {
                if ( ( Boolean ) object ) {
                    ScreenManager.getInstance ().show ( ScreenEnum.MAIN_SCREEN, game );
                    levelMusic.stop ();
                    levelMusic.dispose ();
                    hurraySound.stop ();
                    hurraySound.dispose ();
                }
            }

            @Override
            public float getPrefHeight() {
                return 100f;
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

        if ( screenState != GameScreenStateEnum.COUNTDOWN && screenState != GameScreenStateEnum.RESULT ) {
            float impulse = player1.getPlayerPhysicsBody ().getMass () * 0.30f;

            if ( controller != null && player1.isAlive () ) {
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

                        player1.setFuelLength ( Math.max ( player1.getFuelLength () - ( random.nextInt ( 3 ) + 3 ), 0 ) );
                    }
                } else {
                    if ( player1.getFuelLength () < 100 ) {
                        player1.setFuelLength ( Math.min ( player1.getFuelLength () + 1, 100 ) );
                    }
                }
            }

            if ( controller == null && Gdx.input.isKeyPressed ( GamePreferences.getInstance().getLeftKey ( 0 ) ) && player1.isAlive () ) {
                player1.getPlayerPhysicsBody ().applyLinearImpulse (
                        new Vector2 ( -impulse, 0 ),
                        player1.getPlayerPhysicsBody ().getWorldCenter (),
                        true
                );
            }

            if ( Gdx.input.isKeyPressed ( GamePreferences.getInstance().getLeftKey ( 1 ) ) && player2.isAlive () ) {
                player2.getPlayerPhysicsBody ().applyLinearImpulse (
                        new Vector2 ( -impulse, 0 ),
                        player2.getPlayerPhysicsBody ().getWorldCenter (),
                        true
                );
            }

            if ( controller == null && Gdx.input.isKeyPressed ( GamePreferences.getInstance().getRightKey ( 0 ) ) && player1.isAlive () ) {
                player1.getPlayerPhysicsBody ().applyLinearImpulse (
                        new Vector2 ( impulse, 0 ),
                        player1.getPlayerPhysicsBody ().getWorldCenter (),
                        true
                );
            }

            if ( Gdx.input.isKeyPressed ( GamePreferences.getInstance().getRightKey ( 1 ) ) && player2.isAlive () ) {
                player2.getPlayerPhysicsBody ().applyLinearImpulse (
                        new Vector2 ( impulse, 0 ),
                        player2.getPlayerPhysicsBody ().getWorldCenter (),
                        true
                );
            }

            if ( controller == null && Gdx.input.isKeyPressed ( GamePreferences.getInstance().getJumpKey ( 0 ) ) && player1.isAlive () ) {
                if ( player1.getFuelLength () < 100 && !player1.isCanFly () ) {
                    player1.setFuelLength ( Math.min ( player1.getFuelLength () + 1, 100 ) );
                } else if ( player1.isCanFly () ) {
                    player1.getPlayerPhysicsBody().applyLinearImpulse(
                            new Vector2(0, impulse * 2),
                            player1.getPlayerPhysicsBody().getWorldCenter(),
                            true
                    );

                    player1.setFuelLength ( Math.max ( player1.getFuelLength () - ( random.nextInt ( 3 ) + 3 ), 0 ) );
                }
            }

            if ( Gdx.input.isKeyPressed ( GamePreferences.getInstance().getJumpKey ( 1 ) ) && player2.isAlive () ) {
                if ( player2.getFuelLength () < 100 && !player2.isCanFly () ) {
                    player2.setFuelLength ( Math.min ( player2.getFuelLength () + 1, 100 ) );
                } else if ( player2.isCanFly () ) {
                    player2.getPlayerPhysicsBody ().applyLinearImpulse (
                            new Vector2 ( 0, impulse * 2 ),
                            player2.getPlayerPhysicsBody ().getWorldCenter (),
                            true
                    );

                    player2.setFuelLength ( Math.max ( player2.getFuelLength () - ( random.nextInt ( 3 ) + 3 ), 0 ) );
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

            if ( ( player1.getYPos () < -90 || player1Hit ) && !processingP1Hit ) {
                player1.kill ();
                p2Score++;
                scoreboard.setPlayer2 ( p2Score );
                movePlayer1 = true;
                splatSound.play ( 0.4f );
                processingP1Hit = true;
            }

            if ( ( player2.getYPos () < -90 || player2Hit ) && !processingP2Hit ) {
                player2.kill ();
                p1Score++;
                scoreboard.setPlayer1 ( p1Score );
                movePlayer2 = true;
                splatSound.play ( 0.4f );
                processingP2Hit = true;
            }

            if ( movePlayer1 && processingP1Hit ) {
                player1.setPlayerState ( PlayerStateEnum.DYING );

                if ( player1.isAnimationDone () ) {
                    player1Hit = false;
                    resetPlayer1 ();
                    player1.setPlayerState ( PlayerStateEnum.NEUTRAL );
                    processingP1Hit = false;
                    player1.resetTime ();
                }
            }

            if ( movePlayer2 && processingP2Hit ) {
                player2.setPlayerState ( PlayerStateEnum.DYING );

                if ( player2.isAnimationDone () ) {
                    player2Hit = false;
                    resetPlayer2 ();
                    player2.setPlayerState ( PlayerStateEnum.NEUTRAL );
                    processingP2Hit = false;
                    player2.resetTime ();
                }
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
            player1.setPlayerState ( PlayerStateEnum.NEUTRAL );
            player2.setPlayerState ( PlayerStateEnum.NEUTRAL );
            reset ();
            scoreboard.setPlayer1 ( 0 );
            scoreboard.setPlayer2 ( 0 );
            p1Score = 0;
            p2Score = 0;
            hurraySound.stop ();
            player1Hit = false;
            player2Hit = false;
            processingP1Hit = false;
            processingP2Hit = false;
            player1.resetTime ();
            player2.resetTime ();
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
        player1.setPlayerState ( PlayerStateEnum.NEUTRAL );
        player1.getPlayerPhysicsBody ().setTransform (
                ( float ) x1Pos / Physics.PPM,
                ( float ) ( gamePreferences.getHeightResolution () + 300 ) / Physics.PPM,
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
        player2.setPlayerState ( PlayerStateEnum.NEUTRAL );
        player2.getPlayerPhysicsBody ().setTransform (
                ( float ) x2Pos / Physics.PPM,
                ( float ) ( gamePreferences.getHeightResolution () + 300 ) / Physics.PPM,
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
    public OrthographicCamera getCamera () { return gameCamera; }
    public GameScreenStateEnum getScreenState () { return screenState; }
    public FitViewport getViewport () { return viewport; }
}
