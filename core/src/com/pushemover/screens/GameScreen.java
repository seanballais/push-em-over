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
import com.pushemover.handlers.PlatformHandler;
import com.pushemover.handlers.ScreenHandler;
import com.pushemover.preferences.GamePreferences;
import com.pushemover.utils.Physics;

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

        step = 1.0f / 60f;

        gameWorld = new World ( new Vector2 ( 0, -9.8f ), true );
        pHandler = new PlatformHandler ();
        player = new Player( gameWorld );
        gameWorld.setContactListener ( new CollisionDetectionHandler( player, pHandler ) );
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

        if ( Gdx.input.isKeyPressed ( Input.Keys.W ) && player.isOnGround () ) { // TODO: Improve this part.
            player.getBody ().applyLinearImpulse ( new Vector2 ( 0, 161f ), player.getBody ().getPosition (), true );
        }

        if ( Gdx.input.isKeyPressed ( Input.Keys.A ) ) {
            player.getBody ().applyLinearImpulse ( new Vector2 ( -9.8f, 0 ), player.getBody ().getPosition (), true );
        }

        if ( Gdx.input.isKeyPressed ( Input.Keys.D ) ) {
            player.getBody ().applyLinearImpulse ( new Vector2 ( 9.8f, 0 ), player.getBody ().getPosition (), true );
        }

        delta = Math.min ( 0.06f, Gdx.graphics.getDeltaTime () );

        camera.update ();

        Gdx.gl.glClearColor ( 111/255f, 169/255f, 235/255f, 1 );
        Gdx.gl.glClear ( GL20.GL_COLOR_BUFFER_BIT );

        double newTime = TimeUtils.millis () / 1000.0;
        double frameTime = Math.min ( newTime - currentTime, 0.25f );

        currentTime = newTime;
        accumulator += delta;
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
        game_stage = new Stage ();
        camera = new OrthographicCamera ( Physics.toMeters ( gprefs.getWidthResolution () ),
                                          Physics.toMeters ( gprefs.getHeightResolution () ) );

        pHandler.setPlatforms ( 20, gameWorld );
        platforms = pHandler.getPlatforms ();
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
