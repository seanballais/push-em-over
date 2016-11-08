package com.pushemover.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.pushemover.preferences.GamePreferences;
import com.pushemover.utils.Physics;

public class Player extends Actor
{
    private static Texture playerTexture;
    private Body playerBody;
    private Sprite playerSprite;

    public Player ( World world )
    {
        playerTexture = new Texture(Gdx.files.internal("data/img/game_screen/player.png"));
        this.playerSprite = new Sprite ( playerTexture );

        GamePreferences gprefs = new GamePreferences ();
        BodyDef playerBodyDef = new BodyDef ();
        float positionXMeters = Physics.toMeters ( gprefs.getWidthResolution () / 2 );
        float positionYMeters = Physics.toMeters ( gprefs.getHeightResolution () );
        playerBodyDef.position.set ( positionXMeters, positionYMeters );
        playerBodyDef.type = BodyDef.BodyType.DynamicBody;
        playerBodyDef.fixedRotation = true;
        this.playerBody = world.createBody ( playerBodyDef );

        PolygonShape playerBounds = new PolygonShape ();
        playerBounds.setAsBox ( Physics.toMeters ( getTextureWidth () ),
                                Physics.toMeters ( getTextureHeight () ) );
        playerBody.createFixture ( playerBounds, 1f );
        playerBody.setLinearVelocity ( 0.0f, -100f );
        playerBody.setUserData ( playerSprite );
    }

    public int getTextureHeight ()
    {
        return playerTexture.getHeight ();
    }
    public int getTextureWidth () { return playerTexture.getWidth (); }
    public Body getBody () { return playerBody; }
    public Sprite getSprite () { return playerSprite; }

    @Override public void draw ( Batch batch, float alpha )
    {
        super.draw ( batch, alpha );

        playerSprite.setRotation ( playerBody.getAngle () * 180 / ( float ) Math.PI ); // Convert radians to degrees
        playerSprite.setOrigin ( this.getTextureWidth () / 2, getTextureHeight () / 2 );
        playerSprite.setPosition ( Physics.toPixels ( playerBody.getPosition ().x ) - ( float ) this.getTextureWidth () / 2,
                                   Physics.toPixels ( playerBody.getPosition ().y ) - ( float ) this.getTextureHeight () / 2 );
        playerSprite.setSize ( this.getTextureWidth (), this.getTextureHeight () );
        playerSprite.draw ( batch );
    }

    @Override public void act ( float delta )
    {
        float newXPos = Physics.toPixels ( playerBody.getPosition ().x );
        float newYPos = Physics.toPixels ( playerBody.getPosition ().y );
        playerSprite.setPosition ( newXPos, newYPos );
    }
}