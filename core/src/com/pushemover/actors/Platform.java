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
import com.pushemover.utils.Constants;

public class Platform extends Actor
{
    private static Texture platform_texture;
    private int deltaY;
    private Body platformBody;
    private Sprite platformSprite;

    public int x;
    public int y;

    public Platform ( int x, int y, int deltaY, World world, BodyDef platformBodyDef )
    {
        platform_texture = new Texture(Gdx.files.internal("data/img/game_screen/platform.png"));
        this.x = x;
        this.y = y;
        this.deltaY = deltaY;
        this.platformBody = world.createBody ( platformBodyDef );
        this.platformSprite = new Sprite ( platform_texture );

        GamePreferences gprefs = new GamePreferences ();
        PolygonShape platformBounds = new PolygonShape ();
        platformBounds.setAsBox ( gprefs.getWidthResolution () * Constants.WORLD_TO_BOX,
                                  gprefs.getHeightResolution () * Constants.WORLD_TO_BOX );
        platformBody.createFixture ( platformBounds, 1f );
    }

    public int getTextureHeight ()
    {
        return platform_texture.getHeight ();
    }
    public int getTextureWidth () { return platform_texture.getWidth (); }

    @Override public void draw ( Batch batch, float alpha )
    {
        super.draw ( batch, alpha );

        platformSprite.setRotation ( platformBody.getAngle () * 180 / ( float ) Math.PI ); // Convert radians to degrees
        platformSprite.setOrigin ( this.getTextureWidth () / 2, getTextureHeight () / 2 );
        platformSprite.setPosition ( platformBody.getPosition ().x - this.getTextureWidth () / 2,
                                     platformBody.getPosition ().y - this.getTextureHeight () / 2 );
        platformSprite.setSize ( this.getTextureWidth (), this.getTextureHeight () );
        platformSprite.draw ( batch );
    }

    @Override public void act ( float delta )
    {
        y -= deltaY;
    }
}
