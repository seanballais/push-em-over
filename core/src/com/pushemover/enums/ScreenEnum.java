package com.pushemover.enums;

import com.badlogic.gdx.Game;
import com.pushemover.screens.AbstractScreen;
import com.pushemover.screens.GameScreen;
import com.pushemover.screens.IntroScreen;
import com.pushemover.screens.MainMenu;

public enum ScreenEnum
{
    MAIN_MENU {
        public AbstractScreen getScreen ( Game game )
        {
            return new MainMenu ( game );
        }
    },

    INTRO {
        public AbstractScreen getScreen ( Game game )
        {
            return new IntroScreen ( game );
        }
    },

    GAME {
        public AbstractScreen getScreen ( Game game )
        {
            return new GameScreen ( game );
        }
    };

    public abstract AbstractScreen getScreen ( Game game );
}
