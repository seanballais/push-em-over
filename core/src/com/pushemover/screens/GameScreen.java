package com.pushemover.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.pushemover.actors.Platform;

import java.util.Random;

public class GameScreen extends AbstractScreen
{
    private Stage game_stage;
    private Platform[] platforms;
    private boolean started;

    public GameScreen ( Game game )
    {
        super(game);

        game_stage = new Stage ();
        started = false;

        platforms = new Platform [ 10 ];
        for ( int ctr = 0; ctr < platforms.length; ctr++ ) {
            platforms [ ctr ] = new Platform ();
            game_stage.addActor ( platforms[ ctr ] );
        }
    }

    @Override public void dispose ()
    {
        game_stage.dispose ();
    }

    @Override public void render ( float delta )
    {
        delta = Math.min ( 0.06f, Gdx.graphics.getDeltaTime () );

        Gdx.gl.glClearColor ( 111/255f, 169/255f, 235/255f, 1 );
        Gdx.gl.glClear ( GL20.GL_COLOR_BUFFER_BIT );

        handlePlatformPositions ();

        game_stage.act ( delta );
        game_stage.draw ();

        if ( Gdx.input.isKeyPressed ( Input.Keys.ESCAPE ) ) {
            game.setScreen ( new MainMenu ( game ) );
        }
    }

    @Override public void show ()
    {

    }

    @Override public void hide ()
    {
        Gdx.app.debug ( "Game", "Dispose game screen" );
    }

    private void handlePlatformPositions ()
    {
        Random rand = new Random ();
        int col_width = Gdx.graphics.getWidth () / platforms.length;
        int row_height = Gdx.graphics.getHeight () / ( platforms.length + 7 );
        boolean [] used_row = new boolean [ platforms.length + 5 ];
        for ( int ctr = 0; ctr < platforms.length / 2; ctr++ ) {
            if (!started) {
                platforms[ctr].setXPos( ( col_width + platforms [ ctr ].getTextureWidth () ) * ctr );
                platforms[ctr].setXPos( ( col_width + platforms [ ctr ].getTextureWidth () ) * ctr );

                int row = rand.nextInt ( platforms.length + 4 );
                while ( used_row [ row ] ) {
                    row = rand.nextInt ( platforms.length + 4 );
                }
                used_row[row] = true;

                platforms [ ctr ].setYPos ( row * row_height );
                platforms [ ctr + 5 ].setYPos ( ( row * row_height ) * 2 );
            } else {
                if ( platforms [ ctr ].getYPos () < ( -1 * ( platforms [ ctr ].getTextureHeight () ) ) ) {
                    platforms [ ctr ].setYPos ( Gdx.graphics.getHeight () + 100 );
                }

                if ( platforms [ ctr + 5 ].getYPos () < ( -1 * ( platforms [ ctr + 5 ].getTextureHeight () ) ) ) {
                    platforms [ ctr + 5 ].setYPos ( Gdx.graphics.getHeight () + 300 );
                }
            }
        }

        if ( !started ) {
            for ( int ctr = 0; ctr < platforms.length / 2; ctr++ ) {
                int newYDelta = rand.nextInt ( 4 ) + 1;
                platforms [ ctr ].setYDelta ( newYDelta );
                platforms [ ctr + 5 ].setYDelta ( newYDelta );
            }
        }

        started = true;
    }
}
