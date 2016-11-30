package com.peo.core.managers;

import com.badlogic.gdx.Game;
import com.peo.core.screens.AbstractScreen;
import com.peo.core.screens.GameScreen;

public enum ScreenEnum
{
    GAME_SCREEN {
        public AbstractScreen getScreen ( Game game )
        {
            return new GameScreen ( game );
        }
    };

    public abstract AbstractScreen getScreen ( Game game );
}
