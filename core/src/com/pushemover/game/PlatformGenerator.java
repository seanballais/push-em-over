package com.pushemover.game;

import com.pushemover.preferences.GamePreferences;
import com.pushemover.utils.ds.Graph;
import com.pushemover.utils.ds.GraphNode;

import java.util.Random;

public class PlatformGenerator
{
    private Graph graph;
    private GamePreferences gprefs;

    public PlatformGenerator ()
    {
        graph = new Graph ();
        gprefs = new GamePreferences ();
    }

    public Graph getGeneratedGraph ( int numPoints )
    {
        return getPlatformPoints ( generateGraphArray (), numPoints );
    }

    private GraphNode [][] generateGraphArray ()
    {
        int numNodeRows = ( int ) Math.ceil ( gprefs.getHeightResolution() / 50 ) - 1;
        int numNodeCols = ( int ) Math.ceil ( gprefs.getWidthResolution () / 200 ) - 1;

        return applyPointsToNodes( numNodeRows, numNodeCols, 200, 50 );
    }

    private Graph getPlatformPoints ( GraphNode [][] nodes, int numPoints )
    {
        int randomInitRow = new Random ().nextInt ( nodes.length );
        int randomInitCol = new Random ().nextInt ( nodes [ 0 ].length );

        Graph platformGraph = new Graph ();
        for ( GraphNode currNode = nodes [ randomInitRow ][ randomInitCol ] ; numPoints > 0; numPoints-- ) {
            platformGraph.addNode ( currNode );
            currNode.selected = true;

            // Make sure the next node has not been selected yet.
            while ( currNode.getNeighbourWithIndex( new Random ().nextInt ( currNode.getNumNeighbours() ) ).selected ) {
                currNode = currNode.getNeighbourWithIndex( new Random ().nextInt ( currNode.getNumNeighbours() ) );
            }
        }

        return platformGraph;
    }

    private GraphNode [][] applyPointsToNodes ( int numRows, int numCols, int xDist, int yDist )
    {
        GraphNode [][] points = new GraphNode [ numRows ] [ numCols ];
        for ( int rowIndex = 0; rowIndex < numRows; rowIndex++ ) {
            for ( int colIndex = 0; colIndex < numCols; colIndex++ ) {
                points [ rowIndex ] [ colIndex ] = new GraphNode ( colIndex * xDist, rowIndex * yDist );
            }
        }

        return applyNodeNeighbours ( points );
    }

    private GraphNode [][] applyNodeNeighbours ( GraphNode [][] nodes )
    {
        for ( int rowIndex = 0; rowIndex < nodes.length; rowIndex++ ) {
            for ( int colIndex = 0; colIndex < nodes [ rowIndex ].length; colIndex++ ) {
                addNeighboursToNode ( nodes, colIndex, rowIndex );
            }
        }

        return nodes;
    }

    private void addNeighboursToNode ( GraphNode [][] nodes, int nodeCol, int nodeRow )
    {
        // Add neighbours to node at any direction. Should a node have a duplicate neighbour, the node will
        // check if it has a neighbour of the same type already before adding.
        //
        // Graphic (ASCII art) below for future reference.
        //
        //       n4   n3  n2
        //         \  |  /
        //          \ | /
        //           \|/
        //    n5_____ N _____n1
        //           /|\
        //          / | \
        //         /  |  \
        //       n6   n7  n8
        // Note: Every n is a neighbour of the node N.

        // Add the rightmost neighbour relative to the current node.
        int neighbourCol = Math.min ( nodeCol + 1, nodes [ 0 ].length);
        nodes [ nodeRow ][ nodeCol ].addNeighbour ( nodes [ nodeRow ][ neighbourCol ] );

        // Add the upper right neighbour relative to the current node
        int neighbourRow = Math.abs ( nodeRow - 1 );
        nodes [ nodeRow ][ nodeCol ].addNeighbour ( nodes [ neighbourRow ][ nodeCol ] );

        // Add the top neighbour relative to the current node
        nodes [ nodeRow ][ nodeCol ].addNeighbour ( nodes [ neighbourRow ][ neighbourCol ] );

        // Add the upper left neighbour relative to the current node
        neighbourCol = Math.abs ( nodeCol - 1 );
        nodes [ nodeRow ][ nodeCol ].addNeighbour ( nodes [ neighbourRow ][ neighbourCol ] );

        // Add the leftmost neighbour relative to the current node
        nodes [ nodeRow ][ nodeCol ].addNeighbour ( nodes [ nodeRow ][ neighbourCol ] );

        // Add the bottom left neighbour relative to the current node
        neighbourRow = Math.min ( nodeRow + 1, nodes.length );
        nodes [ nodeRow ][ nodeCol ].addNeighbour ( nodes [ neighbourRow ][ neighbourCol ] );

        // Add the bottom neighbour relative to the current node
        nodes [ nodeRow ][ nodeCol ].addNeighbour ( nodes [ neighbourRow ][ nodeCol ]);

        // Add the bottom right neighbour relative to the current node
        neighbourCol = Math.min ( nodeCol + 1, nodes [ 0 ].length );
        nodes [ nodeRow ][ nodeCol ].addNeighbour ( nodes [ neighbourRow ][ neighbourCol ] );
    }
}
