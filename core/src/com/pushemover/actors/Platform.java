package com.pushemover.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Platform extends Actor {
    private Texture platform_texture;

    public Platform() {
        platform_texture = new Texture(Gdx.files.internal("data/img/game_screen/platform.png"));
    }
}
