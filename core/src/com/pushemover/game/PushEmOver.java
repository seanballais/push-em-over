package com.pushemover.game;

import com.badlogic.gdx.Game;
import com.pushemover.enums.ScreenEnum;
import com.pushemover.handlers.ScreenHandler;

public class PushEmOver extends Game
{
	@Override public void create ()
    {
        ScreenHandler.getInstance ().initialize ( this );
        ScreenHandler.getInstance ().showScreen ( ScreenEnum.MAIN_MENU, this );
    }
}
