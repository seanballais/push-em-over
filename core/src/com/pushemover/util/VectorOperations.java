package com.pushemover.util;

import java.awt.Point;

public class VectorOperations
{
    public Point add ( Point p1, Point p2 )
    {
        return new Point ( ( p1.x + p2.x ), ( p1.y + p2.y ) );
    }

    public Point subtract ( Point p1, Point p2 )
    {
        return new Point ( ( p1.x - p2.x ), ( p1.y - p2.y ) );
    }

    public int vectorDistanceBetween ( Point p1, Point p2 ) {
        int xDistance = componentDifference ( p1.x, p2.x );
        int yDistance = componentDifference ( p1.y, p2.y );

        return ( int ) Math.sqrt( xDistance + yDistance );
    }

    public int componentDifference ( int c1, int c2 ) {
        return Math.max ( c1, c2 ) - Math.min ( c1, c2 );
    }
}
