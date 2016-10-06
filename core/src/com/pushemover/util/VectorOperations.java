package com.pushemover.util;

import java.awt.Point;

public class VectorOperations
{
    public static Point add ( Point p1, Point p2 )
    {
        return new Point ( ( p1.x + p2.x ), ( p1.y + p2.y ) );
    }

    public static Point subtract ( Point p1, Point p2 )
    {
        return new Point ( ( p1.x - p2.x ), ( p1.y - p2.y ) );
    }

    public static Point normalize ( Point p )
    {
        return new Point ( ( p.x / magnitude ( p ) ), ( p.y / magnitude ( p ) ) );
    }

    public static double magnitude ( Point p )
    {
        return Math.hypot ( p.x, p.y );
    }

    public static double distanceBetween ( Point p1, Point p2 ) {
        int xDistance = componentDifference ( p1.x, p2.x );
        int yDistance = componentDifference ( p1.y, p2.y );

        return Math.hypot ( xDistance, yDistance );
    }

    private static int componentDifference ( int c1, int c2 ) {
        return Math.max ( c1, c2 ) - Math.min ( c1, c2 );
    }
}
