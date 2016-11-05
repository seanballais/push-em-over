package com.pushemover.handlers;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.Array;

public class InputHandler implements InputProcessor
{
    public class InputState
    {
        public boolean pressed;
        public boolean down;
        public boolean released;
        public int key;

        public InputState ( int key )
        {
            this.key = key;
            pressed = false;
            down = false;
            released = false;
        }
    }

    public Array < InputState > inputStates = new Array < InputState > ();

    public InputHandler ()
    {
        for ( int keyCode = 0; keyCode < 256; keyCode++ ) {
            inputStates.add ( new InputState( keyCode ) );
        }
    }

    @Override public boolean keyDown ( int keyCode )
    {
        inputStates.get ( keyCode ).pressed = true;
        inputStates.get ( keyCode ).down = true;

        return false;
    }

    @Override public boolean keyUp ( int keyCode )
    {
        inputStates.get ( keyCode ).down = false;
        inputStates.get ( keyCode ).released = true;

        return false;
    }

    public boolean isKeyPressed ( int key )
    {
        return inputStates.get ( key ).pressed;
    }

    public boolean isKeyDown ( int key )
    {
        return inputStates.get ( key ).down;
    }

    public boolean isKeyReleased ( int key )
    {
        return inputStates.get ( key ).released;
    }

    public void update ()
    {
        for ( int keyCode = 0; keyCode < 256; keyCode++ ) {
            InputState k = inputStates.get ( keyCode );
            k.pressed = false;
            k.released = false;
        }
    }
}
