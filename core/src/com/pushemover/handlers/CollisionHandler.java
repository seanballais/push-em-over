package com.pushemover.handlers;

import com.pushemover.actors.Platform;
import com.pushemover.actors.Player;
import com.pushemover.enums.CollisionSideEnum;
import com.pushemover.physics.Collision;

import java.awt.Rectangle;
import java.util.ArrayList;

public class CollisionHandler
{
    private Player player;
    private ArrayList <Platform> platforms;

    public CollisionHandler ( Player player, ArrayList <Platform> platforms )
    {
        this.player = player;
        this.platforms = platforms;
    }

    public void handleCollisions ()
    {
        Rectangle playerBoundingBox = player.getBoundingBox ();
        Rectangle platformBoundingBox;
        for ( Platform p : platforms ) {
            platformBoundingBox = p.getBoundingBox ();

            if ( Collision.collision ( playerBoundingBox, platformBoundingBox ) == CollisionSideEnum.TOP ) {
                player.moveYPos ( p.getDeltaY () );
                player.setContactBuddy ( platformBoundingBox );
            }

            if ( Collision.collision ( playerBoundingBox, platformBoundingBox ) == CollisionSideEnum.NONE &&
                    player.getContactBuddy() == platformBoundingBox) {
                player.moveYPos ( 7 );
                player.setContactBuddy ( null );
            }
        }
    }
}
