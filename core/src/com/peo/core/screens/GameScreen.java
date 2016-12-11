package com.peo.core.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.peo.utils.GamePreferences;

public class GameScreen extends AbstractScreen
{
    private GameScreenWorld gameWorld;
    private GameScreenRenderer gameRenderer;
    private GamePreferences gamePreferences;

    public GameScreen ( Game game )
    {
        super ( game );

        gameWorld = new GameScreenWorld ( game );
        gameRenderer = new GameScreenRenderer (
            gameWorld,
            gameWorld.getPlayStage (),
            gameWorld.getResultStage (),
            gameWorld.getCountdownStage ()
        );

        gamePreferences = new GamePreferences ();
    }

    @Override public void render ( float delta )
    {
        gameWorld.update ( delta );
        gameRenderer.render ();
    }

    @Override public void resize ( int width, int height )
    {
        gamePreferences.setWidthResolution ( width );
        gamePreferences.setHeightResolution ( height );
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
        gameRenderer.dispose ();
    }
}
