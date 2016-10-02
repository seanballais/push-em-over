package com.pushemover.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Platform extends Actor {
    private Texture platform_texture;
    private int x_pos;
    private int y_pos;
    private float y_delta;

    public Platform() {
        platform_texture = new Texture(Gdx.files.internal("data/img/game_screen/platform.png"));
        x_pos = Gdx.graphics.getWidth() / 2;
        y_pos = Gdx.graphics.getHeight() + 10;
        y_delta = -2;
    }

    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw(platform_texture, x_pos, y_pos);
    }

    @Override
    public void act(float delta) {
        y_pos += y_delta;
    }

    public void setXPos(int x_pos) {
        this.x_pos = x_pos;
    }

    public void setYPos(int y_pos) {
        this.y_pos = y_pos;
    }

    public void setYDelta(int y_delta) {
        this.y_delta = -1 * y_delta;
    }

    public int getXPos() {
        return x_pos;
    }

    public int getYPos() {
        return y_pos;
    }

    public int getTextureWidth()
    {
        return platform_texture.getWidth ();
    }

    public int getTextureHeight ()
    {
        return platform_texture.getHeight ();
    }
}
