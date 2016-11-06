package com.pushemover.handlers;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.pushemover.actors.Platform;
import com.pushemover.actors.Player;

public class CollisionDetectionHandler implements ContactListener
{
    private Player player;
    private PlatformHandler pHandler;

    public CollisionDetectionHandler ( Player player, PlatformHandler pHandler )
    {
        this.player = player;
        this.pHandler = pHandler;
    }

    @Override public void beginContact ( Contact contact )
    {
        for ( Platform p : pHandler.getPlatforms() ) {
            if ( ( contact.getFixtureA ().getBody () == player.getBody () ) &&
                 ( contact.getFixtureB ().getBody () == p.getBody () ) ) {
                player.getBody().setLinearVelocity ( 0.0f, -5f );
            }
        }
    }

    @Override public void endContact ( Contact contact )
    {
        for ( Platform p : pHandler.getPlatforms() ) {
            if ( ( contact.getFixtureA ().getBody () == player.getBody () ) &&
                    ( contact.getFixtureB ().getBody () == p.getBody () ) ) {
                player.getBody().setLinearVelocity ( 0.0f, -100f );
            }
        }
    }

    @Override public void preSolve ( Contact contact, Manifold oldManifold )
    {

    }

    @Override public void postSolve ( Contact contact, ContactImpulse impulse )
    {

    }
}
