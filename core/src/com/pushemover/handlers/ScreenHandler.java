package com.pushemover.handlers;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.pushemover.screens.GameScreen;
import com.pushemover.screens.IntroScreen;
import com.pushemover.screens.MainMenu;

public class ScreenHandler
{
    private Game game;
    private GameScreen gameScreen;
    private IntroScreen introScreen;
    private MainMenu mainMenuScreen;
    private Screen currScreen;

    public ScreenHandler ( Game game )
    {
        this.game = game;
        gameScreen = new GameScreen ( this.game );
        introScreen = new IntroScreen ( this.game );
        mainMenuScreen = new MainMenu ( this.game );
    }

    private void setScreen ( Screen newScreen )
    {
        currScreen = newScreen;
        game.setScreen ( newScreen );
    }

    private Screen getCurrScreen ()
    {
        return currScreen;
    }

    public void switchToGame ()
    {
        setScreen ( gameScreen );
    }

    public void switchToIntro ()
    {
        setScreen ( introScreen );
    }

    public void switchToMain ()
    {
        setScreen ( mainMenuScreen );
    }

    public boolean isInGame ()
    {
        return getCurrScreen () == gameScreen;
    }

    public boolean isInIntro ()
    {
        return getCurrScreen () == introScreen;
    }

    public boolean isInMenu ()
    {
        return getCurrScreen () == mainMenuScreen;
    }
}
