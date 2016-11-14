package com.pushemover.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.pushemover.actors.Platform;
import com.pushemover.actors.Player;
import com.pushemover.enums.ScreenEnum;
import com.pushemover.handlers.CollisionHandler;
import com.pushemover.handlers.PlatformHandler;
import com.pushemover.handlers.ScreenHandler;
import com.pushemover.preferences.GamePreferences;

import java.util.ArrayList;
import java.util.Random;

public class GameScreen extends AbstractScreen
{
    private OrthographicCamera camera;
    private Stage game_stage;
    private PlatformHandler pHandler;
    private Player player;
    private ArrayList < Platform > platforms;
    private GamePreferences gprefs;
    private CollisionHandler collisionHandler;
    private double accumulator;
    private float step;

    public GameScreen ( Game game )
    {
        super ( game );

        step = 1.0f / 60f;

        gprefs = new GamePreferences ();
        pHandler = new PlatformHandler ();
        int numPlatforms = 20;
        pHandler.setPlatforms ( numPlatforms );
        platforms = pHandler.getPlatforms ();

        int y = 0;
        Platform initPlayerPlatformSpawn = platforms.get ( new Random ().nextInt ( numPlatforms - 1 ) );
        while ( y < gprefs.getHeightResolution () / 2 ) {
            initPlayerPlatformSpawn = platforms.get ( new Random ().nextInt ( numPlatforms - 1 ) );
            y = initPlayerPlatformSpawn.getYPos ();
        }

        player = new Player( initPlayerPlatformSpawn.getXPos () + initPlayerPlatformSpawn.getTextureWidth () / 2,
                             initPlayerPlatformSpawn.getYPos () + initPlayerPlatformSpawn.getTextureHeight () * 2 );
    }

    @Override public void dispose ()
    {
        game_stage.dispose ();
    }

    @Override public void render ( float delta )
    {
        if ( Gdx.input.isKeyPressed ( Input.Keys.ESCAPE ) ) {
            ScreenHandler.getInstance().showScreen ( ScreenEnum.MAIN_MENU, game );
        }

        // Player presses
        // Player 0 refers to player 1.
        if ( Gdx.input.isKeyPressed ( gprefs.getRightKey ( 0 ) ) ) {
            player.moveXPos ( 7 );
        }

        if ( Gdx.input.isKeyPressed ( gprefs.getLeftKey ( 0 ) ) ) {
            player.moveXPos ( -7 );
        }

        delta = Math.min ( 0.06f, Gdx.graphics.getDeltaTime () );

        // Update physics
        collisionHandler.handleCollisions ();

        camera.update ();

        Gdx.gl.glClearColor ( 111/255f, 169/255f, 235/255f, 1 );
        Gdx.gl.glClear ( GL20.GL_COLOR_BUFFER_BIT );

        // TODO: If time permits, fix this time step function.
        //double newTime = TimeUtils.millis () / 1000.0;
        //double frameTime = Math.min ( newTime - currentTime, 0.25f );
        //double currentTime;
        //currentTime = newTime;
        accumulator += delta;
        while ( accumulator >= step ) {
            accumulator -= step;
            pHandler.updatePlatforms ();
            game_stage.act ( delta );
            game_stage.draw ();

            player.moveXPos ( 0 );
        }
    }

    @Override public void show ()
    {
        game_stage = new Stage ();
        camera = new OrthographicCamera ();

        collisionHandler = new CollisionHandler ( player, platforms );
        for ( Platform p : platforms ) {
            game_stage.addActor ( p );
        }

        game_stage.addActor ( player );

        camera.setToOrtho ( false );
    }

    @Override public void hide ()
    {
        Gdx.app.debug ( "Game", "Dispose game screen" );
    }
}
