package com.peo.core.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.HashMap;

public class GenericPlayer extends Actor
{
    private static Texture playerSpritesheet;
    private String playerName;
    private Color playerTextColor;
    private int x;
    private int y;
    private int width;
    private int height;
    private float elapsedTime;
    private PlayerStateEnum playerState;
    private HashMap < String, Object > animations;
    private Color bodyColor;

    public GenericPlayer ( String playerName, Color playerTextColor, int x, int y )
    {
        this.x = x;
        this.y = y;
        this.playerName = playerName;
        this.playerTextColor = playerTextColor;
        width = 125;
        height = 150;
        elapsedTime = 0;
        playerState = PlayerStateEnum.WALKING;
        playerSpritesheet = new Texture ( Gdx.files.internal ( "img/actors/player-spritesheet.jpg" ) );
        animations = new HashMap < String, Object > ();
        bodyColor = new Color ( 255f, 255f, 79f, 1f );

        setAnimations ();
    }

    public void setPlayerState ( PlayerStateEnum playerState )
    {
        this.playerState = playerState;
    }

    @Override public void draw ( Batch batch, float parentAlpha )
    {
        TextureRegion currFrame = ( TextureRegion ) animations.get ( "walking" );

        // NOTE: PlayerStateEnum.WALKING not checked because it is the default frame already.
        if ( playerState == PlayerStateEnum.SAD ) {
            currFrame = ( TextureRegion ) animations.get ( "hit" );
        } else if ( playerState == PlayerStateEnum.PUNCHING ) {
            currFrame = ( ( Animation ) animations.get ( "punching" ) ).getKeyFrame ( elapsedTime, true );
        } else if ( playerState == PlayerStateEnum.FALLING ) {
            currFrame = ( ( Animation ) animations.get ( "falling" ) ).getKeyFrame ( elapsedTime, true );
        }

        batch.draw ( currFrame, x, y );
    }

    @Override public void act ( float delta )
    {
        elapsedTime += Gdx.graphics.getDeltaTime ();
    }

    private void setAnimations ()
    {
        Animation playerPunchingAnimation;
        Animation playerFallingAnimation;
        TextureRegion playerSad;
        TextureRegion playerWalking;

        // Set up punching animation frames
        TextureRegion [] punchingFrames = new TextureRegion [ 3 ];
        for ( int frameCounter = 0; frameCounter < punchingFrames.length; frameCounter++ ) {
            punchingFrames [ frameCounter ] = new TextureRegion (
                    playerSpritesheet, frameCounter * 125, 0, width, height
            );
        }

        playerPunchingAnimation = new Animation ( 0.20f, punchingFrames );
        animations.put ( "punching", playerPunchingAnimation );

        // Set up the falling animation frames
        TextureRegion [] fallingFrames = new TextureRegion [ 2 ];
        for ( int frameCounter = 0; frameCounter < fallingFrames.length; frameCounter++ ) {
            fallingFrames [ frameCounter ] = new TextureRegion (
                    playerSpritesheet, frameCounter * 125, 150, width, height
            );
        }

        playerFallingAnimation = new Animation ( 0.150f, fallingFrames );
        animations.put ( "falling", playerFallingAnimation );

        // Emotions texture regions
        playerWalking = new TextureRegion ( playerSpritesheet, 0, 300, width, height );
        playerSad = new TextureRegion ( playerSpritesheet, 125, 300, width, height );
        animations.put ( "walking", playerWalking );
        animations.put ( "hit", playerSad );
    }
}
