package com.pushemover.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class IntroScreen extends AbstractScreen
{
    private TextureRegion texture_intro;
    private SpriteBatch batch;
    private float time = 0;

    public IntroScreen (Game game )
    {
        super ( game );
    }

    @Override public void show ()
    {
        texture_intro = new TextureRegion ( new Texture (Gdx.files.internal ( "data/intro.png" ) ), 0, 0, 480, 320 );
        batch = new SpriteBatch ();
        batch.getProjectionMatrix ().setToOrtho2D( 0, 0, 480, 320 );
    }

    @Override public void render ( float delta )
    {
        Gdx.gl.glClear ( GL20.GL_COLOR_BUFFER_BIT );
        batch.begin ();
        batch.draw ( texture_intro, 0, 0 );
        batch.end ();

        time += delta;
        if ( time > 1 ) {
            if ( Gdx.input.isKeyPressed ( Input.Keys.ANY_KEY ) ) {
                game.setScreen ( new GameScreen ( game ) );
            }
        }
    }

    @Override public void hide ()
    {
        Gdx.app.debug ( "Intro", "Dispose intro" );
        batch.dispose ();
        texture_intro.getTexture ().dispose ();
    }
}
