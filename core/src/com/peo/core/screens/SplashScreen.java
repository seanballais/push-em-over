package com.peo.core.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.peo.core.actors.Logo;
import com.peo.core.managers.ScreenEnum;
import com.peo.core.managers.ScreenManager;
import com.peo.utils.GamePreferences;

public class SplashScreen extends AbstractScreen
{
    private Logo logo;
    private Stage stage;
    private float time;
    private Game game;
    private OrthographicCamera gameCamera;
    private GamePreferences gamePreferences;

    public SplashScreen ( Game game )
    {
        super ( game );

        this.game = game;
        gamePreferences = new GamePreferences ();

        gameCamera = new OrthographicCamera ();
        gameCamera.setToOrtho ( false, gamePreferences.getWidthResolution (), gamePreferences.getHeightResolution () );

        stage = new Stage ();
        stage.addActor ( new Logo () );
    }

    @Override public void render ( float delta )
    {
        time += delta;

        Gdx.gl.glClearColor ( 0, 0, 0, 1 );
        Gdx.gl.glClear ( GL20.GL_COLOR_BUFFER_BIT );

        gameCamera.update ();
        stage.act ();
        stage.draw ();

        if ( time >= 3.0f ) {
            ScreenManager.getInstance().initialize ( this.game );
            ScreenManager.getInstance ().show ( ScreenEnum.MAIN_SCREEN, this.game );
        }
    }

    @Override public void resize ( int width, int height )
    {
        Gdx.app.log ( "GameScreen", "resize () called" );
    }

    @Override public void show ()
    {
        Gdx.app.log ( "GameScreen", "show () called" );
    }

    @Override public void hide ()
    {
        Gdx.app.log ( "GameScreen", "hide () called" );
    }

    @Override public void pause ()
    {
        Gdx.app.log ( "GameScreen", "pause () called" );
    }

    @Override public void resume ()
    {
        Gdx.app.log ( "GameScreen", "resume () called" );
    }

    @Override public void dispose ()
    {
        Gdx.app.log ( "GameScreen", "dispose () called" );
    }
}
