package com.peo.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;

public class GamePreferences
{
    private static GamePreferences gPrefInstance;
    private static Preferences prefs;

    public GamePreferences ()
    {
        prefs = Gdx.app.getPreferences( "PEO Preferences" );
    }

    public static GamePreferences getInstance ()
    {
        if ( gPrefInstance == null ) {
            gPrefInstance = new GamePreferences ();
        }

        return gPrefInstance;
    }

    public void setName ( int player_num, String player_name )
    {
        if ( player_num == 0 ) { // Player 1
            prefs.putString ( "p1_name", player_name);
        } else if ( player_num == 1 ) { // Player 2
            prefs.putString ( "p2_name", player_name );
        }
    }

    public void setJumpKey ( int player_num, int jump_key )
    {
        if ( player_num == 0 ) { // Player 1 Jump Key
            prefs.putInteger ( "p1_jump", jump_key );
        } else if ( player_num == 1 ) { // Player 2 Jump Key
            prefs.putInteger ( "p2_jump", jump_key );
        }
    }

    public void setLeftKey ( int player_num, int left_key )
    {
        if ( player_num == 0 ) { // Player 1 Left Key
            prefs.putInteger ( "p1_left", left_key );
        } else if ( player_num == 1 ) { // Player 2 Left Key
            prefs.putInteger ( "p2_left", left_key );
        }
    }

    public void setRightKey ( int player_num, int right_key )
    {
        if ( player_num == 0 ) { // Player 1 Right Key
            prefs.putInteger ( "p1_right", right_key );
        } else if ( player_num == 1 ) { // Player 2 Left Key
            prefs.putInteger ( "p2_right", right_key );
        }
    }

    public void setPunchKey ( int player_num, int punch_key )
    {
        if ( player_num == 0 ) { // Player 1 Punch Key
            prefs.putInteger ( "p1_punch", punch_key );
        } else if ( player_num == 1 ) { // Player 2 Left Key
            prefs.putInteger ( "p2_punch", punch_key );
        }
    }

    public void setWidthResolution ( int width )
    {
        prefs.putInteger ( "res_width", width );
    }

    public void setHeightResolution ( int height )
    {
        prefs.putInteger ( "res_height", height );
    }

    public void setFullscreen ( boolean fullscreen )
    {
        prefs.putBoolean ( "res_full", fullscreen );
    }

    public String getName ( int player_num )
    {
        if ( player_num == 0 ) { // Player 1 Name
            return prefs.getString ( "p1_name", "Player 1" );
        } else if ( player_num == 1 ) { // Player 2 Name
            return prefs.getString ( "p2_name", "Player 2" );
        }

        return "";
    }

    public int getJumpKey ( int player_num )
    {
        if ( player_num == 0 ) { // Player 1 Jump Key
            return prefs.getInteger ( "p1_jump", Input.Keys.W );
        } else if ( player_num == 1 ) { // Player 2 Jump Key
            return prefs.getInteger ( "p2_jump", Input.Keys.UP );
        }

        return -1;
    }

    public int getLeftKey ( int player_num )
    {
        if ( player_num == 0 ) { // Player 1 Left Key
            return prefs.getInteger ( "p1_left", Input.Keys.A );
        } else if ( player_num == 1 ) { // Player 2 Left Key
            return prefs.getInteger ( "p1_left", Input.Keys.LEFT );
        }

        return -1;
    }

    public int getRightKey ( int player_num )
    {
        if ( player_num == 0 ) { // Player 1 Right Key
            return prefs.getInteger ( "p1_right", Input.Keys.D );
        } else if ( player_num == 1 ) { // Player 2 Right Key
            return prefs.getInteger ( "p2_right", Input.Keys.RIGHT );
        }

        return -1;
    }

    public int getPunchKey ( int player_num )
    {
        if ( player_num == 0 ) { // Player 1 Punch Key
            return prefs.getInteger ( "p1_punch", Input.Keys.F );
        } else if ( player_num == 1 ) { // Player 2 Punch Key
            return prefs.getInteger ( "p2_punch", Input.Keys.SHIFT_RIGHT );
        }

        return -1;
    }

    public int getWidthResolution ()
    {
        return prefs.getInteger ( "res_width", Gdx.graphics.getWidth () );
    }

    public int getHeightResolution ()
    {
        return prefs.getInteger ( "res_height", Gdx.graphics.getHeight () );
    }

    public boolean isFullscreen ()
    {
        return prefs.getBoolean ( "res_full", true );
    } // Set fullscreen true by default next time

    public void save ()
    {
        prefs.flush ();
    }
}