package com.pushemover.utils.ds;

import java.util.ArrayList;

public class Graph
{
    private ArrayList <GraphNode> nodes;

    public Graph ()
    {
        nodes = new ArrayList <GraphNode> ();
    }

    public void addNode ( GraphNode node )
    {
        nodes.add ( node );
    }

    public void removeNode ( GraphNode node )
    {
        nodes.remove ( node );
    }

    public void removeNodeWithIndex ( int index )
    {
        nodes.remove ( index );
    }

    public GraphNode getNodeWithIndex ( int index )
    {
        return nodes.get ( index );
    }
}