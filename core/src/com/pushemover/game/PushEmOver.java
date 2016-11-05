package com.pushemover.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.pushemover.handlers.ScreenHandler;
import com.pushemover.screens.MainMenu;

public class PushEmOver extends Game
{
    private ScreenHandler sHandler;

    public PushEmOver ()
    {
        sHandler = new ScreenHandler ( this );
    }

	@Override public void create ()
    {
    	sHandler.switchToMain ();
	}

	@Override public void render ()
    {
        if ( Gdx.input.isKeyPressed ( Input.Keys.ESCAPE ) ) {
            if ( sHandler.isInGame () ) {
                sHandler.switchToMain ();
            } else if ( sHandler.isInMenu() ) {
                Gdx.app.exit ();
            }
        } else if ( Gdx.input.isKeyPressed ( Input.Keys.ANY_KEY ) ) {
            if ( sHandler.isInMenu () ) {
                sHandler.switchToIntro ();
            } else if ( sHandler.isInIntro () ) {
                sHandler.switchToGame ();
            }
        }
    }
}
