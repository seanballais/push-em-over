package com.pushemover.preferences;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;

public class GamePreferences
{
    private static Preferences prefs;

    public Preferences ()
    {
        prefs = Gdx.app.getPreferences( "PEO Preferences" );
    }

    public static void setJumpKey ( int player_num, int jump_key )
    {
        if ( player_num == 0 ) { // Player 1 Jump Key
            prefs.putInteger ( "p1_jump", jump_key );
        } else if ( player_num == 1 ) { // Player 2 Jump Key
            prefs.putInteger ( "p2_jump", jump_key );
        }
    }

    public static void setLeftKey ( int player_num, int left_key )
    {
        if ( player_num == 0 ) { // Player 1 Left Key
            prefs.putInteger ( "p1_left", left_key );
        } else if ( player_num == 1 ) { // Player 2 Left Key
            prefs.putInteger ( "p2_left", left_key );
        }
    }

    public static void setRightKey ( int player_num, int right_key )
    {
        if ( player_num == 0 ) { // Player 1 Right Key
            prefs.putInteger ( "p1_right", right_key );
        } else if ( player_num == 1 ) { // Player 2 Left Key
            prefs.putInteger ( "p2_right", right_key );
        }
    }

    public static void setPunchKey ( int player_num, int punch_key )
    {
        if ( player_num == 0 ) { // Player 1 Punch Key
            prefs.putInteger ( "p1_punch", punch_key );
        } else if ( player_num == 1 ) { // Player 2 Left Key
            prefs.putInteger ( "p2_punch", punch_key );
        }
    }

    public static int getJumpKey ( int player_num )
    {
        if ( player_num == 0 ) { // Player 1 Jump Key
            return prefs.getInteger ( "p1_jump", Input.Keys.W );
        } else if ( player_num == 1 ) { // Player 2 Jump Key
            return prefs.getInteger ( "p2_jump", Input.Keys.UP );
        }
    }

    public static int getLeftKey ( int player_num )
    {
        if ( player_num == 0 ) { // Player 1 Left Key
            return prefs.getInteger ( "p1_left", Input.Keys.A )
        } else if ( player_num == 1 ) { // Player 2 Left Key
            return prefs.getInteger ( "p1_left", Input.Keys.LEFT );
        }
    }

    public static int getRightKey ( int player_num )
    {
        if ( player_num == 0 ) { // Player 1 Right Key
            return prefs.getInteger ( "p1_right", Input.Keys.D )
        } else if ( player_num == 1 ) { // Player 2 Right Key
            return prefs.getInteger ( "p1_right", Input.Keys.RIGHT );
        }
    }

    public static int getPunchKey ( int player_num )
    {
        if ( player_num == 0 ) { // Player 1 Punch Key
            return prefs.getInteger ( "p1_punch", Input.Keys.F )
        } else if ( player_num == 1 ) { // Player 2 Punch Key
            return prefs.getInteger ( "p1_punch", Input.Keys.SHIFT_RIGHT );
        }
    }
}