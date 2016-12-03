package com.peo.core.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.peo.utils.Physics;

public class Platform extends Actor
{
    private static Texture platformTexture;
    private int x;
    private int y;
    private int width;
    private int height;
    private World physicsWorldRef;
    private Body platformPhysicsBody;

    public Platform ( World physicsWorldRef, int x, int y )
    {
        platformTexture = new Texture ( Gdx.files.internal ( "img/actors/platform.png" ) );
        this.x = x;
        this.y = y;
        this.physicsWorldRef = physicsWorldRef;
        this.width = platformTexture.getWidth ();
        this.height = platformTexture.getHeight ();
        platformPhysicsBody = createBody ();
    }

    @Override public void draw ( Batch batch, float parentAlpha )
    {
        batch.draw ( platformTexture, x, y );
    }

    public int setXPos ( int x ) { return this.x = x; }
    public int setYPos ( int y ) { return this.y = y; }
    public Body getPlatformPhysicsBody () { return platformPhysicsBody; }

    private Body createBody ()
    {
        BodyDef platformBodyDef = new BodyDef ();
        platformBodyDef.type = BodyDef.BodyType.StaticBody;
        platformBodyDef.position.set ( x / Physics.PPM, y / Physics.PPM );
        platformBodyDef.fixedRotation = true;

        Body platformBody = physicsWorldRef.createBody ( platformBodyDef );

        PolygonShape platformBounds = new PolygonShape ();
        platformBounds.setAsBox ( ( width / Physics.PPM ) / 2, ( height / Physics.PPM ) / 2 );

        FixtureDef platformFixtureDef = new FixtureDef ();
        platformFixtureDef.shape = platformBounds;
        platformFixtureDef.density = 1f;

        Fixture platformFixture = platformBody.createFixture ( platformFixtureDef );

        platformBounds.dispose ();

        return platformBody;
    }
}
