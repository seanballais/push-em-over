package com.pushemover.util;

import java.awt.Point;
import java.util.Random;

public class FlockSeparation
{
    private Point[] generateBoids ( int generationWidth, int generationHeight, int numPoints ) {
        Random rand = new Random ();
        Point [] boids = new Point [ numPoints ];
        for ( int ctr = 0; ctr < numPoints; ctr++ ) {
            boids [ ctr ] = new Point ();
            boids [ ctr ].x = rand.nextInt ( generationWidth );
            boids [ ctr ].y = rand.nextInt ( generationHeight );
        }

        return boids;
    }

    private Point steerBoid ( Point[] platformPoints, int pointIndex, float separationDistance ) {
        Point newBoidPosition = new Point();
        int numCloseBoids = 0;

        for ( Point boidPoint : platformPoints ) {
            float dist = VectorOperations.distanceBetween ( platformPoints[pointIndex], boidPoint );
            if ( ( dist > 0 ) && ( dist < separationDistance ) ) {

            }
        }
    }
}
