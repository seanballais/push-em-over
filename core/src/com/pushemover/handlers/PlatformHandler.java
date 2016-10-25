package com.pushemover.handlers;

import com.pushemover.actors.Platform;
import com.pushemover.game.PlatformGenerator;
import com.pushemover.utils.ds.Graph;
import com.pushemover.utils.ds.GraphNode;

import java.util.ArrayList;
import java.util.Random;

public class PlatformHandler
{
    private ArrayList < Platform > platforms;

    public PlatformHandler ()
    {
        platforms = new ArrayList < Platform > ();
    }

    public ArrayList < Platform > getPlatforms ()
    {
        addPointsToPlatforms();

        return platforms;
    }

    private void addPointsToPlatforms ()
    {
        PlatformGenerator pGenerator = new PlatformGenerator ();
        Graph generatedGraph = pGenerator.getGeneratedGraph ( new Random ().nextInt ( 10 ) + 15 );

        GraphNode node;
        Platform platform;
        for ( int ctr = 0; ctr < generatedGraph.numNodes (); ctr++ ) {
            platform = new Platform ();

            node = generatedGraph.getNodeWithIndex ( ctr );
            platform.x = node.x;
            platform.y = node.y;

            platforms.add ( platform );
        }
    }
}
