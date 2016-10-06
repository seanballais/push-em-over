package com.pushemover.util;

import java.awt.Point;

public class PVector
{
    private float x;
    private float y;

    public PVector ( Point p )
    {
        this.x = p.getX ();
        this.y = p.getY ();
    }

    public void add ( Point p )
    {
        this.x += p.x;
        this.y += p.y;
    }

    public void subtract ( Point p )
    {
        this.x -= p.x;
        this.y -= p.y;
    }

    public void multiply ( float scalarValue )
    {
        this.x *= scalarValue;
        this.y *= scalarValue;
    }

    public void divide ( float scalarValue )
    {
        this.x /= scalarValue;
        this.y /= scalarValue;
    }

    public void limit ( int maxMagnitude )
    {

    }

    public Point normalize ()
    {
        float mag = magnitude ();
        if ( mag != 0 && mag != 1 ) {
            divide ( mag );
        }
    }

    public double magnitude ()
    {
        return Math.hypot ( x, y );
    }

    public static double distanceBetween ( Point p ) {
        int xDistance = this.x - p.x;
        int yDistance = this.y - p.y;

        return Math.hypot ( xDistance, yDistance );
}
