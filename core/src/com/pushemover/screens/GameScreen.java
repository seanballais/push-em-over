package com.pushemover.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.pushemover.actors.Platform;
import com.pushemover.handlers.PlatformHandler;

import java.util.ArrayList;

public class GameScreen extends AbstractScreen
{
    private Stage game_stage;
    private ArrayList < Platform > platforms;

    public GameScreen ( Game game )
    {
        super ( game );

        game_stage = new Stage ();
        platforms = new PlatformHandler ().getPlatforms ();

        for ( Platform platform : platforms ) {
            game_stage.addActor ( platform );
        }
    }

    @Override public void dispose ()
    {
        game_stage.dispose ();
    }

    @Override public void render ( float delta )
    {
        delta = Math.min ( 0.06f, Gdx.graphics.getDeltaTime () );

        Gdx.gl.glClearColor ( 111/255f, 169/255f, 235/255f, 1 );
        Gdx.gl.glClear ( GL20.GL_COLOR_BUFFER_BIT );

        game_stage.act ( delta );
        game_stage.draw ();

        if ( Gdx.input.isKeyPressed ( Input.Keys.ESCAPE ) ) {
            game.setScreen ( new MainMenu ( game ) );
        }
    }

    @Override public void show ()
    {

    }

    @Override public void hide ()
    {
        Gdx.app.debug ( "Game", "Dispose game screen" );
    }
}
