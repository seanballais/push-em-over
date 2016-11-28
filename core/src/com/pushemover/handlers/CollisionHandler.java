package com.pushemover.handlers;

import com.pushemover.actors.Platform;
import com.pushemover.actors.Player;
import com.pushemover.physics.Collision;

import java.awt.Point;
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

            if ( Collision.contact ( playerBoundingBox, platformBoundingBox ) ) {
                Point penetrationVector = getPenetrationVector ( playerBoundingBox, platformBoundingBox );
                player.adjustXPos ( penetrationVector.x );
                player.adjustYPos ( penetrationVector.y );
            }
        }
    }

    private Point getPenetrationVector ( Rectangle boundingBoxSource, Rectangle boundingBoxTarget )
    {
        Point penetrationVector = new Point ();
        Point movementVector = getPlayerMovementVector ();
        Rectangle bbIntersection = ( Rectangle ) boundingBoxSource.createIntersection ( boundingBoxTarget );

        if ( Math.abs ( movementVector.x ) >= Math.abs ( movementVector.y ) ) {
            penetrationVector.x = ( int ) -Math.signum ( movementVector.x ) * Math.abs ( bbIntersection.x );
            penetrationVector.y = ( int ) -Math.signum ( movementVector.y ) *
                    Math.abs ( ( bbIntersection.x / movementVector.y ) / movementVector.x );
        } else if ( Math.abs ( movementVector.x ) < Math.abs ( movementVector.y ) ) {
            penetrationVector.y = ( int ) -Math.signum ( movementVector.y ) * Math.abs ( bbIntersection.y );
            penetrationVector.x = ( int ) -Math.signum ( movementVector.x ) *
                    Math.abs ( ( bbIntersection.y / movementVector.x ) / movementVector.y );
        }

        return penetrationVector;
    }

    private Point getPlayerMovementVector ()
    {
        int movementVectorX =
                Math.max ( player.getPlayerX(), player.getPrevX () ) -
                Math.min ( player.getPlayerX(), player.getPrevX () )
        ;

        int movementVectorY =
                Math.max ( player.getPlayerY(), player.getPrevY () ) -
                Math.min ( player.getPlayerY(), player.getPrevY () )
        ;

        return new Point ( movementVectorX, movementVectorY );
    }
}
