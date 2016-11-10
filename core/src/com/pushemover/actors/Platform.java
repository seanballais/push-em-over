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
    private int x;
    private int y;

    public Platform ( int x, int y )
    {
        platform_texture = new Texture(Gdx.files.internal("data/img/game_screen/platform.png"));
        this.x = x;
        this.y = y;
    }

    public int getTextureHeight ()
    {
        return platform_texture.getHeight ();
    }
    public int getTextureWidth () { return platform_texture.getWidth (); }

    @Override public void draw ( Batch batch, float alpha )
    {
        batch.draw ( platform_texture, x, y );
    }

    @Override public void act ( float delta )
    {
        y -= 2;
    }
}
