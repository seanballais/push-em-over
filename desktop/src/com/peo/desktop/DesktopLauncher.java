package com.peo.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.peo.Game;
import com.peo.utils.GamePreferences;

public class DesktopLauncher
{
	public static void main (String[] arg)
	{
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration ();
        config.title = "Push Em Over";
        config.width = LwjglApplicationConfiguration.getDesktopDisplayMode ().width;
        config.height = LwjglApplicationConfiguration.getDesktopDisplayMode ().height;

		new LwjglApplication ( new Game (), config );

        GamePreferences gPrefs = new GamePreferences ();
        Graphics.DisplayMode mode = Gdx.graphics.getDisplayMode ();
        if ( gPrefs.isFullscreen () ) {
            Gdx.graphics.setWindowedMode ( gPrefs.getWidthResolution (), gPrefs.getHeightResolution () );
            Gdx.graphics.setFullscreenMode ( mode );
        } else {
            Gdx.graphics.setWindowedMode ( gPrefs.getWidthResolution (), gPrefs.getHeightResolution () );
        }
    }
}
