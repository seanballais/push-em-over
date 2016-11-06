package com.pushemover.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.pushemover.enums.ScreenEnum;
import com.pushemover.handlers.ScreenHandler;

public class MainMenu extends AbstractScreen
{
    private TextureRegion title;
    private SpriteBatch batch;
    private float time = 0;

    public MainMenu (Game game )
    {
        super ( game );
    }

    @Override public void show ()
    {
        title = new TextureRegion ( new Texture ( Gdx.files.internal ( "data/title.png" ) ), 0, 0, 480, 320 );
        batch = new SpriteBatch ();
        batch.getProjectionMatrix ().setToOrtho2D ( 0, 0, 480, 320 );
    }

    @Override public void render ( float delta )
    {
        if ( Gdx.input.isKeyPressed ( Input.Keys.SPACE ) ) {
            ScreenHandler.getInstance().showScreen ( ScreenEnum.GAME, game );
        }

        if ( Gdx.input.isKeyPressed ( Input.Keys.ESCAPE ) ) {
            Gdx.app.exit ();
        }

        Gdx.gl.glClear ( GL20.GL_COLOR_BUFFER_BIT );
        batch.begin ();
        batch.draw ( title, 0, 0 );
        batch.end ();
    }

    @Override public void hide ()
    {
        Gdx.app.debug ( "Push Em Over", "Dispose main menu" );
        batch.dispose ();
        title.getTexture ().dispose ();
    }
}
