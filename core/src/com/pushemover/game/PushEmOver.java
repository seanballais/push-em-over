package com.pushemover.game;

import com.badlogic.gdx.Game;
import com.pushemover.screens.MainMenu;

public class PushEmOver extends Game
{
	@Override public void create ()
    {
    	setScreen ( new MainMenu ( this ) );
	}
}
