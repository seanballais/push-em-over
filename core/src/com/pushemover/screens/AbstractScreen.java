package com.pushemover.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

public abstract class AbstractScreen implements Screen
{
    protected Game game;

    public AbstractScreen ( Game game )
    {
        this.game = game;
    }

    @Override public void resize ( int width, int height )
    {

    }

    @Override public void show ()
    {

    }

    @Override public void hide ()
    {

    }

    @Override public void pause ()
    {

    }

    @Override public void resume ()
    {

    }

    @Override public void dispose ()
    {

    }
}
