package com.peo.core.managers;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.peo.core.screens.AbstractScreen;

public class ScreenManager
{
    private static ScreenManager instance;
    private Game game;

    private ScreenManager ()
    {
        super ();
    }

    public static ScreenManager getInstance ()
    {
        if ( instance == null ) {
            instance = new ScreenManager ();
        }

        return instance;
    }

    public void initialize ( Game game )
    {
        this.game = game;
    }

    public void show ( ScreenEnum screenEnum, Game game )
    {
        AbstractScreen newScreen = screenEnum.getScreen ( game );
        game.setScreen ( newScreen );
    }
}
