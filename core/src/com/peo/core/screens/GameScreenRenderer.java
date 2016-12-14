package com.peo.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.peo.core.actors.GameScreenStateEnum;
import com.peo.utils.GamePreferences;

public class GameScreenRenderer
{
    private GameScreenWorld gameWorld;
    private Box2DDebugRenderer box2DdebugRenderer;
    private Stage playStage;
    private Stage resultStage;
    private Stage countdownStage;
    private GamePreferences gamePreferences;

    public GameScreenRenderer ( GameScreenWorld gameWorld, Stage playStage, Stage resultStage, Stage countdownStage )
    {
        this.gameWorld = gameWorld;
        box2DdebugRenderer = new Box2DDebugRenderer ();

        this.playStage = playStage;
        this.resultStage = resultStage;
        this.countdownStage = countdownStage;
        gamePreferences = new GamePreferences ();
    }

    public void render ()
    {
        Gdx.gl.glClearColor ( 111/255f, 169/255f, 235/255f, 1 );
        Gdx.gl.glClear ( GL20.GL_COLOR_BUFFER_BIT );

        gameWorld.getViewport ().update (
            gamePreferences.getWidthResolution (),
            gamePreferences.getHeightResolution (),
            true
        );

        if ( gameWorld.getScreenState () == GameScreenStateEnum.COUNTDOWN ) {
            playStage.draw ();
            countdownStage.act ();
            countdownStage.draw ();
        } else if ( gameWorld.getScreenState () == GameScreenStateEnum.PLAY ) {
            playStage.act ();
            playStage.draw ();
        } else if ( gameWorld.getScreenState () == GameScreenStateEnum.RESULT ) {
            playStage.draw ();
            resultStage.act ();
            resultStage.draw ();
        }

        gameWorld.getExitStage ().act ();
        gameWorld.getExitStage ().draw ();
    }

    public void dispose ()
    {
        playStage.dispose ();
    }
}
