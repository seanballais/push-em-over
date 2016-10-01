package com.pushemover.game.desktop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.pushemover.game.PushEmOver;
import com.pushemover.preferences.GamePreferences;

public class DesktopLauncher
{
	public static void main ( String[] arg )
    {
    	LwjglApplicationConfiguration config = new LwjglApplicationConfiguration ();
		config.title = "Push Em Over";
		config.width = LwjglApplicationConfiguration.getDesktopDisplayMode ().width;
        config.height = LwjglApplicationConfiguration.getDesktopDisplayMode ().height;

        new LwjglApplication ( new PushEmOver (), config );

        GamePreferences gpref = new GamePreferences ();
        Graphics.DisplayMode mode = Gdx.graphics.getDisplayMode ();
        if ( gpref.isFullscreen () ) {
            Gdx.graphics.setWindowedMode ( gpref.getWidthResolution (), gpref.getHeightResolution () );
            Gdx.graphics.setFullscreenMode( mode );
        } else {
            Gdx.graphics.setWindowedMode ( gpref.getWidthResolution (), gpref.getHeightResolution () );
        }
	}
}
