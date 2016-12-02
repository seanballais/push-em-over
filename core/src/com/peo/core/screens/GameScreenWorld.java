package com.peo.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.peo.core.actors.Background;
import com.peo.core.actors.GenericPlayer;

public class GameScreenWorld
{
    private Background background;
    private GenericPlayer player1;
    private Stage playStage;

    public GameScreenWorld ()
    {
        playStage = new Stage ();

        background = new Background ();
        player1 = new GenericPlayer ( "Bob", new Color ( 0f, 102/255f, 204/255f, 1f ), 50, 50 );

        playStage.addActor ( background );
        playStage.addActor ( player1 );
    }

    public void update ( float delta )
    {
        Gdx.app.log ( "GameScreenWorld", "update" );
    }

    public Stage getPlayStage ()
    {
        return playStage;
    }
}