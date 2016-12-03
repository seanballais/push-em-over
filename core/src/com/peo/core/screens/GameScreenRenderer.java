package com.peo.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.peo.utils.GamePreferences;

public class GameScreenRenderer
{
    private GameScreenWorld gameWorld;
    private OrthographicCamera gameCamera;
    private GamePreferences gamePreferences;
    private Box2DDebugRenderer box2DdebugRenderer;
    private Stage playStage;

    public GameScreenRenderer ( GameScreenWorld gameWorld, Stage playStage )
    {
        this.gameWorld = gameWorld;
        box2DdebugRenderer = new Box2DDebugRenderer ();
        gamePreferences = new GamePreferences ();
        gameCamera = new OrthographicCamera ();
        gameCamera.setToOrtho ( false, gamePreferences.getWidthResolution (), gamePreferences.getHeightResolution () );
        this.playStage = playStage;
    }

    public void render ()
    {
        Gdx.app.log ( "GameScreenRenderer", "render" );

        Gdx.gl.glClearColor ( 0, 0, 0, 1 );
        Gdx.gl.glClear ( GL20.GL_COLOR_BUFFER_BIT );

        playStage.act ();
        playStage.draw ();
        box2DdebugRenderer.render ( gameWorld.getPhysicsWorld (), gameCamera.combined );
    }

    public void dispose ()
    {
        playStage.dispose ();
    }
}
