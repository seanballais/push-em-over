package com.peo.core.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class GameScreen extends AbstractScreen
{
    private GameScreenWorld gameWorld;
    private GameScreenRenderer gameRenderer;

    public GameScreen ( Game game )
    {
        super ( game );

        gameWorld = new GameScreenWorld ();
        gameRenderer = new GameScreenRenderer ();
    }

    @Override public void render ( float delta )
    {
        gameWorld.update ( delta );
        gameRenderer.render ();
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
