package com.pushemover.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.TimeUtils;
import com.pushemover.actors.Platform;
import com.pushemover.actors.Player;
import com.pushemover.enums.ScreenEnum;
import com.pushemover.handlers.CollisionDetectionHandler;
import com.pushemover.handlers.PlatformHandler;
import com.pushemover.handlers.ScreenHandler;
import com.pushemover.preferences.GamePreferences;

import java.util.ArrayList;

public class GameScreen extends AbstractScreen
{
    private World gameWorld;
    private OrthographicCamera camera;
    private Stage game_stage;
    private ArrayList < Platform > platforms;
    private PlatformHandler pHandler;
    private Player player;
    private double accumulator;
    private double currentTime;
    private float step;

    public GameScreen ( Game game )
    {
        super ( game );
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

        delta = Math.min ( 0.06f, Gdx.graphics.getDeltaTime () );

        camera.update ();

        Gdx.gl.glClearColor ( 111/255f, 169/255f, 235/255f, 1 );
        Gdx.gl.glClear ( GL20.GL_COLOR_BUFFER_BIT );

        double newTime = TimeUtils.millis () / 1000.0;
        double frameTime = Math.min ( newTime - currentTime, 0.25f );

        currentTime = newTime;

        while ( accumulator >= step ) {
            gameWorld.step ( step, 6, 2 );
            accumulator -= step;
            pHandler.updatePlatforms ();
            game_stage.act ( delta );
            game_stage.draw ();
        }
    }

    @Override public void show ()
    {
        GamePreferences gprefs = new GamePreferences ();
        step = 1.0f / 60f;
        game_stage = new Stage ();
        gameWorld = new World ( new Vector2 ( 0, -9.8f ), true );
        camera = new OrthographicCamera ( gprefs.getWidthResolution (), gprefs.getHeightResolution () );

        pHandler = new PlatformHandler ();
        pHandler.setPlatforms ( 20, gameWorld );
        platforms = pHandler.getPlatforms ();
        player = new Player( gameWorld );
        gameWorld.setContactListener ( new CollisionDetectionHandler( player, pHandler ) );

        for ( int ctr = 0; ctr < platforms.size (); ctr++ ) {
            game_stage.addActor ( platforms.get( ctr ) );
        }

        game_stage.addActor ( player );

        camera.setToOrtho ( false );
    }

    @Override public void hide ()
    {
        Gdx.app.debug ( "Game", "Dispose game screen" );
    }
}
