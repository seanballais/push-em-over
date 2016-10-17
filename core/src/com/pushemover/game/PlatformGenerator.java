package com.pushemover.game;

import com.pushemover.preferences.GamePreferences;
import com.pushemover.utils.ds.Graph;
import com.pushemover.utils.ds.GraphNode;

public class PlatformGenerator
{
    private Graph graph;
    private GamePreferences gprefs;

    public PlatformGenerator ()
    {
        graph = new Graph ();
        gprefs = new GamePreferences ();
    }

    private GraphNode [][] generateScreenPoints ()
    {
        int numNodeRows = ( int ) Math.ceil ( gprefs.getHeightResolution() / 50 ) - 1;
        int numNodeCols = ( int ) Math.ceil ( gprefs.getWidthResolution () / 200 ) - 1;

        GraphNode [][] points = new GraphNode [ numNodeRows ] [ numNodeCols ];
        for ( int rowIndex = 0; rowIndex < numNodeRows; rowIndex++ ) {
            for ( int colIndex = 0; colIndex < numNodeCols; colIndex++ ) {
                points [ rowIndex ] [ colIndex ] = new GraphNode ( colIndex * 200, rowIndex * 50 );
            }
        }

        return points;
    }
}
