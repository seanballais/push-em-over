package com.pushemover.handlers;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.pushemover.enums.ScreenEnum;
import com.pushemover.screens.AbstractScreen;

public class ScreenHandler
{
    private static ScreenHandler instance;
    private Game game;

    private ScreenHandler ()
    {
        super ();
    }

    public static ScreenHandler getInstance ()
    {
        if ( instance == null ) {
            instance = new ScreenHandler ();
        }

        return instance;
    }

    public void initialize ( Game game )
    {
        this.game = game;
    }

    public void showScreen ( ScreenEnum screenEnum, Game game )
    {
        Screen currScreen = game.getScreen ();

        AbstractScreen newScreen = screenEnum.getScreen ( game );
        game.setScreen ( newScreen );

        if ( currScreen != null ) {
            currScreen.dispose ();
        }
    }
}
