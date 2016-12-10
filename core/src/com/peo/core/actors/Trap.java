package com.peo.core.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.peo.utils.Physics;

public class Trap extends Actor
{
    private static Texture trapTexture;
    private int x;
    private int y;
    private int width;
    private int height;
    private World physicsWorldRef;
    private Body trapPhysicsBody;

    public Trap ( World physicsWorldRef, int x, int y )
    {
        trapTexture = new Texture ( Gdx.files.internal ( "img/actors/trap.png" ) );
        this.x = x;
        this.y = y;
        this.physicsWorldRef = physicsWorldRef;
        this.width = trapTexture.getWidth ();
        this.height = trapTexture.getHeight ();
        trapPhysicsBody = createBody ();
    }

    @Override public void draw ( Batch batch, float parentAlpha )
    {
        batch.draw ( trapTexture, x - width / 2, y - height / 2 );
    }

    public void setXPos ( int x ) { this.x = x; }
    public void setYPos ( int y ) { this.y = y; }
    public int getXPos () { return x; }
    public int getYPos () { return y; }
    public Body getTrapPhysicsBody() { return trapPhysicsBody; }

    private Body createBody ()
    {
        BodyDef trapBodyDef = new BodyDef ();
        trapBodyDef.type = BodyDef.BodyType.StaticBody;
        trapBodyDef.position.set ( x / Physics.PPM, y / Physics.PPM );
        trapBodyDef.fixedRotation = true;

        Body trapBody = physicsWorldRef.createBody ( trapBodyDef );
        trapBody.setUserData ( this );

        PolygonShape trapBounds = new PolygonShape ();
        trapBounds.setAsBox ( ( width / Physics.PPM ) / 2, ( height / Physics.PPM ) / 2 );

        FixtureDef trapFixtureDef = new FixtureDef ();
        trapFixtureDef.shape = trapBounds;
        trapFixtureDef.density = 1f;

        Fixture platformFixture = trapBody.createFixture ( trapFixtureDef );

        trapBounds.dispose ();

        return trapBody;
    }
}
