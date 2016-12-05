package com.peo.core.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.peo.utils.Physics;

import java.util.HashMap;

public class GenericPlayer extends Actor
{
    private Texture playerSpritesheet;
    private String playerName;
    private Color playerTextColor;
    private BitmapFont playerTextFont;
    private int x;
    private int y;
    private int width;
    private int height;
    private int fuelLength;
    private boolean canFly;
    private float elapsedTime;
    private PlayerStateEnum playerState;
    private HashMap < String, Object > animations;
    private World physicsWorldRef;
    private Body playerPhysicsBody;
    private ShapeRenderer fuelDrawer;

    public GenericPlayer ( World physicsWorldRef, String playerName, Color playerTextColor, int x, int y )
    {
        this.x = x;
        this.y = y;
        this.playerName = playerName;
        this.playerTextColor = playerTextColor;
        this.physicsWorldRef = physicsWorldRef;
        fuelLength = 100;
        playerTextFont = new BitmapFont ();
        playerTextFont.setColor ( this.playerTextColor );
        playerTextFont.getData ().setScale ( 1.5f );
        width = 50;
        height = 60;
        elapsedTime = 0;
        canFly = true;
        playerState = PlayerStateEnum.WALKING;
        playerSpritesheet = new Texture ( Gdx.files.internal ( "img/actors/player-spritesheet.jpg" ) );
        animations = new HashMap < String, Object > ();
        fuelDrawer = new ShapeRenderer ();

        setAnimations ();

        playerPhysicsBody = createBody ();
    }

    public void setPlayerState ( PlayerStateEnum playerState )
    {
        this.playerState = playerState;
    }
    public void setXPos ( int x ) { this.x = x; }
    public void setYPos ( int y ) { this.y = y; }
    public void setFuelLength ( int length ) { fuelLength = length; }
    public int getFuelLength () { return fuelLength; }
    public boolean isCanFly () { return canFly; }

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

        batch.draw ( currFrame, x - width / 2, y - height / 2 );
        playerTextFont.draw (
                batch,
                playerName,
                ( ( x - width / 2 + ( x - width / 2 + width ) ) / 2 ) - ( width / 2 ) + 5,
                y - height / 2 + height + 20
        );

        fuelDrawer.begin ( ShapeRenderer.ShapeType.Line );
        fuelDrawer.setColor ( Color.DARK_GRAY );
        fuelDrawer.rect( x - width, y + ( height - 20 ), 100, 10 );
        fuelDrawer.end ();

        fuelDrawer.begin ( ShapeRenderer.ShapeType.Filled );
        fuelDrawer.setColor ( Color.GOLD );
        fuelDrawer.rect( x - width, y + ( height - 20 ), fuelLength, 10 );
        fuelDrawer.end ();
    }

    @Override public void act ( float delta )
    {
        elapsedTime += Gdx.graphics.getDeltaTime ();
    }

    public Body getPlayerPhysicsBody ()
    {
        return playerPhysicsBody;
    }

    private Body createBody ()
    {
        BodyDef playerBodyDef = new BodyDef ();
        playerBodyDef.type = BodyDef.BodyType.DynamicBody;
        playerBodyDef.position.set ( x / Physics.PPM, y / Physics.PPM );
        playerBodyDef.fixedRotation = true;

        Body playerBody = physicsWorldRef.createBody ( playerBodyDef );
        playerBody.setUserData ( this.playerName );

        PolygonShape playerBounds = new PolygonShape ();
        playerBounds.setAsBox ( ( width / Physics.PPM ) / 2, ( height / Physics.PPM ) / 2 );

        FixtureDef playerFixtureDef = new FixtureDef ();
        playerFixtureDef.shape = playerBounds;
        playerFixtureDef.density = 1f;
        playerFixtureDef.friction = 0.5f;

        Fixture playerFixture = playerBody.createFixture ( playerFixtureDef );

        playerBounds.dispose ();

        return playerBody;
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
                    playerSpritesheet, frameCounter * width, 0, width, height
            );
        }

        playerPunchingAnimation = new Animation ( 0.20f, punchingFrames );
        animations.put ( "punching", playerPunchingAnimation );

        // Set up the falling animation frames
        TextureRegion [] fallingFrames = new TextureRegion [ 2 ];
        for ( int frameCounter = 0; frameCounter < fallingFrames.length; frameCounter++ ) {
            fallingFrames [ frameCounter ] = new TextureRegion (
                    playerSpritesheet, frameCounter * width, 60, width, height
            );
        }

        playerFallingAnimation = new Animation ( 0.150f, fallingFrames );
        animations.put ( "falling", playerFallingAnimation );

        // Emotions texture regions
        playerWalking = new TextureRegion ( playerSpritesheet, 0, 120, width, height );
        playerSad = new TextureRegion ( playerSpritesheet, 50, 120, width, height );
        animations.put ( "walking", playerWalking );
        animations.put ( "hit", playerSad );
    }
}
