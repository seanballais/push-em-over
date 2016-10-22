package com.pushemover.utils.ds;

import java.util.ArrayList;

public class GraphNode
{
    private ArrayList <GraphNode> neighbours;
    public boolean selected;
    public int x;
    public int y;

    public GraphNode ( int x, int y )
    {
        neighbours = new ArrayList <GraphNode> ();
        selected = false;
        this.x = x;
        this.y = y;
    }

    public void addNeighbour ( GraphNode node )
    {
        if ( !neighbours.contains ( node ) ) {
            neighbours.add ( node );
        }
    }

    public void removeNeighbour ( GraphNode node )
    {
        neighbours.remove ( node );
    }

    public void removeNeighbourWithIndex ( int index )
    {
        neighbours.remove ( index );
    }

    public GraphNode getNeighbourWithIndex ( int index )
    {
        return neighbours.get( index );
    }

    public double distanceToNeighbour ( GraphNode node )
    {
        int xDist = Math.abs ( x - node.x );
        int yDist = Math.abs ( y - node.y );

        return Math.sqrt ( Math.pow( xDist, 2 ) + Math.pow ( yDist, 2 ) );
    }

    public int getNumNeighbours ()
    {
        return neighbours.size ();
    }
}
