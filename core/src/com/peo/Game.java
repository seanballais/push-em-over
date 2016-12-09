package com.peo;

import com.peo.core.managers.ScreenEnum;
import com.peo.core.managers.ScreenManager;

public class Game extends com.badlogic.gdx.Game
{
	@Override public void create ()
    {
        ScreenManager.getInstance ().initialize ( this );
        ScreenManager.getInstance ().show ( ScreenEnum.SPLASH_SCREEN, this );
    }
}
