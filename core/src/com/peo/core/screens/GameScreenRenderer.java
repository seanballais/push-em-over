package com.peo.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
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

    public GameScreenRenderer ( GameScreenWorld gameWorld, Stage playStage, Stage resultStage )
    {
        this.gameWorld = gameWorld;
        box2DdebugRenderer = new Box2DDebugRenderer ();

        this.playStage = playStage;
        this.resultStage = resultStage;
    }

    public void render ()
    {
        Gdx.gl.glClearColor ( 0, 0, 0, 1 );
        Gdx.gl.glClear ( GL20.GL_COLOR_BUFFER_BIT );

        System.out.println ( "Screen state: " + gameWorld.getScreenState () );

        if ( gameWorld.getScreenState () == GameScreenStateEnum.PLAY ) {
            playStage.act ();
            playStage.draw ();
        } else if ( gameWorld.getScreenState () == GameScreenStateEnum.RESULT ) {
            playStage.draw ();
            resultStage.act ();
            resultStage.draw ();
        }
    }

    public void dispose ()
    {
        playStage.dispose ();
    }
}
