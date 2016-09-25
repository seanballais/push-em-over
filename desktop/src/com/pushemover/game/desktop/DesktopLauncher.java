package com.pushemover.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.pushemover.game.PushEmOver;

public class DesktopLauncher
{
	public static void main ( String[] arg )
    {
    	LwjglApplicationConfiguration config = new LwjglApplicationConfiguration ();
		config.width = 800;
        config.height = 640;

        new LwjglApplication ( new PushEmOver (), config );
	}
}
