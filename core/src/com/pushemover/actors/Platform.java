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
import com.pushemover.utils.Physics;

public class Platform extends Actor
{
    private static Texture platform_texture;
    private Body platformBody;
    private Sprite platformSprite;

    public Platform ( World world, BodyDef platformBodyDef )
    {
        platform_texture = new Texture(Gdx.files.internal("data/img/game_screen/platform.png"));
        this.platformBody = world.createBody ( platformBodyDef );
        this.platformSprite = new Sprite ( platform_texture );

        PolygonShape platformBounds = new PolygonShape ();
        platformBounds.setAsBox ( Physics.toMeters ( getTextureWidth () ),
                                  Physics.toMeters ( getTextureHeight () ) );
        platformBody.createFixture ( platformBounds, 1f );
        platformBody.setLinearVelocity ( 0.0f, -5f );
        platformBody.setUserData ( platformSprite );

        platformBounds.dispose ();
    }

    public int getTextureHeight ()
    {
        return platform_texture.getHeight ();
    }
    public int getTextureWidth () { return platform_texture.getWidth (); }
    public Body getBody () { return platformBody; }
    public Sprite getSprite () { return platformSprite; }

    @Override public void draw ( Batch batch, float alpha )
    {
        super.draw ( batch, alpha );

        platformSprite.setRotation ( platformBody.getAngle () * 180 / ( float ) Math.PI ); // Convert radians to degrees
        platformSprite.setOrigin ( this.getTextureWidth () / 2, getTextureHeight () / 2 );
        platformSprite.setPosition ( Physics.toPixels ( platformBody.getPosition ().x ) - ( float ) this.getTextureWidth () / 2,
                                     Physics.toPixels ( platformBody.getPosition ().y ) - ( float ) this.getTextureHeight () / 2 );
        platformSprite.setSize ( Physics.toPixels ( Physics.toMeters ( this.getTextureWidth () ) ),
                                 Physics.toPixels ( Physics.toMeters ( this.getTextureHeight () ) ) );
        platformSprite.draw ( batch );
    }

    @Override public void act ( float delta )
    {
        float newXPos = Physics.toPixels ( platformBody.getPosition ().x );
        float newYPos = Physics.toPixels ( platformBody.getPosition ().y );
        platformSprite.setPosition ( newXPos, newYPos );
    }
}
