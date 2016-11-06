package com.pushemover.utils;

public class Physics
{
    public static float toMeters ( float px )
    {
        return px * Constants.PIXELS_TO_METERS;
    }
    public static float toPixels ( float m )
    {
        return m * Constants.METERS_TO_PIXELS;
    }
}
