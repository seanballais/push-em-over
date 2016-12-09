package com.peo.core.managers;

import com.badlogic.gdx.Game;
import com.peo.core.screens.AbstractScreen;
import com.peo.core.screens.GameScreen;
import com.peo.core.screens.MainMenuScreen;
import com.peo.core.screens.SplashScreen;

public enum ScreenEnum
{
    GAME_SCREEN {
        public AbstractScreen getScreen ( Game game )
        {
            return new GameScreen ( game );
        }
    },
    MAIN_SCREEN {
        public AbstractScreen getScreen ( Game game ) { return new MainMenuScreen ( game ); }
    },
    SPLASH_SCREEN {
        public AbstractScreen getScreen ( Game game ) { return new SplashScreen ( game ); }
    };

    public abstract AbstractScreen getScreen ( Game game );
}
