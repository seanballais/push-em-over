package com.peo.core.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.peo.utils.Physics;

import java.util.HashMap;

public class GenericPlayer extends Actor
{
    private static Texture playerSpritesheet;
    private static Texture playerFuelSheet;
    private String playerName;
    private Color playerTextColor;
    private BitmapFont playerTextFont;
    private int x;
    private int y;
    private int width;
    private int height;
    private int fuelLength;
    private boolean canFly;
    private boolean alive;
    private float elapsedTime;
    private PlayerStateEnum playerState;
    private HashMap < String, Object > animations;
    private HashMap < String, TextureRegion > playerFuelBar;
    private World physicsWorldRef;
    private Body playerPhysicsBody;

    public GenericPlayer ( World physicsWorldRef, String playerName, Color playerTextColor, int x, int y, Texture texture )
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
        alive = true;
        playerState = PlayerStateEnum.NEUTRAL;
        playerSpritesheet = texture;
        playerFuelSheet = new Texture ( Gdx.files.internal ( "img/actors/playerfuel.png" ) );
        animations = new HashMap < String, Object > ();
        playerFuelBar = new HashMap < String, TextureRegion > ();

        setAnimations ();
        setFuelBar ();

        playerPhysicsBody = createBody ();
    }

    public void setPlayerState ( PlayerStateEnum playerState )
    {
        this.playerState = playerState;
    }
    public void setXPos ( int x ) { this.x = x; }
    public void setYPos ( int y ) { this.y = y; }
    public void setFuelLength ( int length ) { fuelLength = length; }
    public void kill () { alive = false; }
    public void resurrect () { alive = true; }
    public int getFuelLength () { return fuelLength; }
    public boolean isCanFly () { return canFly; }
    public boolean isAlive () { return alive; }
    public void resetTime () { elapsedTime = 0; }

    public boolean isAnimationDone ()
    {
        return ( ( Animation ) animations.get ( "dying" ) ).isAnimationFinished ( elapsedTime );
    }

    @Override public void draw ( Batch batch, float parentAlpha )
    {
        TextureRegion currFrame = ( TextureRegion ) animations.get ( "neutral" );

        // NOTE: PlayerStateEnum.NEUTRAL not checked because it is the default frame already.
        if ( playerState == PlayerStateEnum.DYING ) {
            currFrame = ( ( Animation ) animations.get ( "dying" ) ).getKeyFrame ( elapsedTime, false );
        }

        batch.draw ( currFrame, x - width / 2, y - height / 2 );
        playerTextFont.draw (
                batch,
                playerName,
                ( ( x - width / 2 + ( x - width / 2 + width ) ) / 2 ) - ( width / 2 ) + 5,
                y - height / 2 + height + 40
        );

        batch.draw ( playerFuelBar.get ( "barOutline" ), x - width, y + ( height - 25 ), 104, 12 );
        batch.draw ( playerFuelBar.get ( "barFilling" ), ( x - width ) + 2, y + ( height - 23 ), fuelLength, 8 );
    }

    @Override public void act ( float delta )
    {
        if ( playerState == PlayerStateEnum.DYING ) {
            elapsedTime += Gdx.graphics.getDeltaTime ();
        } else if ( playerState == PlayerStateEnum.NEUTRAL ) {
            elapsedTime = 0;
        }

        if ( fuelLength >= 100 ) {
            canFly = true;
        } else if ( fuelLength <= 0 ) {
            canFly = false;
        }
    }

    public Body getPlayerPhysicsBody ()
    {
        return playerPhysicsBody;
    }
    public int getYPos () { return y; }
    public int getXPos () { return x; }

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
        playerFixtureDef.restitution = 0.3f;

        Fixture playerFixture = playerBody.createFixture ( playerFixtureDef );

        playerBounds.dispose ();

        return playerBody;
    }

    private void setFuelBar ()
    {
        TextureRegion fuelBarOutline = new TextureRegion ( playerFuelSheet, 0, 0, 6, 15 );
        TextureRegion fuelBarFilling = new TextureRegion ( playerFuelSheet, 6, 2, 2, 11 );

        playerFuelBar.put ( "barOutline", fuelBarOutline );
        playerFuelBar.put ( "barFilling", fuelBarFilling );
    }

    private void setAnimations ()
    {
        Animation playerDying;
        TextureRegion playerNeutral = new TextureRegion ( playerSpritesheet, 0, 0, 50, 60 );

        TextureRegion[] playerDyingFrames = new TextureRegion [ 9 ];
        for ( int ctr = 0; ctr < playerDyingFrames.length; ctr++ ) {
            playerDyingFrames [ ctr ] = new TextureRegion (
                playerSpritesheet,
                ( ctr * 50 ) + 50,
                0,
                50,
                60
            );
        }

        playerDying = new Animation ( 0.1f, playerDyingFrames );
        animations.put ( "neutral", playerNeutral );
        animations.put ( "dying", playerDying );
    }
}
