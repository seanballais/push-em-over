package com.pushemover.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.pushemover.actors.Platform;
import com.pushemover.handlers.PlatformHandler;
import com.pushemover.utils.Constants;

import java.util.ArrayList;

public class GameScreen extends AbstractScreen
{
    private World gameWorld;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera camera;
    private Stage game_stage;
    private ArrayList < Platform > platforms;
    private PlatformHandler pHandler;

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
        delta = Math.min ( 0.06f, Gdx.graphics.getDeltaTime () );

        camera.update ();

        Gdx.gl.glClearColor ( 111/255f, 169/255f, 235/255f, 1 );
        Gdx.gl.glClear ( GL20.GL_COLOR_BUFFER_BIT );

        Matrix4 cameraCopy = camera.combined.cpy ();
        debugRenderer.render ( gameWorld, cameraCopy.scl ( Constants.BOX_TO_WORLD ) );

        game_stage.act ( delta );

        // Return the platform to the top if it went below the screen already.
        for ( int ctr = 0; ctr < platforms.size (); ctr++ ) {
            pHandler.loopPosition ( platforms.get ( ctr ) );
        }

        game_stage.draw ();

        gameWorld.step ( 1/60f, 6, 2 );

        if ( Gdx.input.isKeyPressed ( Input.Keys.ESCAPE ) ) {
            game.setScreen ( new MainMenu ( game ) );
        }
    }

    @Override public void show ()
    {
        game_stage = new Stage ();
        pHandler = new PlatformHandler ();
        gameWorld = new World ( new Vector2 ( 0, -10 ), true );
        camera = new OrthographicCamera ();
        debugRenderer = new Box2DDebugRenderer ();
        platforms = pHandler.getPlatforms ( gameWorld );

        for ( int ctr = 0; ctr < platforms.size (); ctr++ ) {
            game_stage.addActor ( platforms.get( ctr ) );
        }

        camera.setToOrtho ( false );
    }

    @Override public void hide ()
    {
        Gdx.app.debug ( "Game", "Dispose game screen" );
    }
}
