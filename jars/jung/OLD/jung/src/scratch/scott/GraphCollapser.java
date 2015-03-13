/*
* Copyright (c) 2003, the JUNG Project and the Regents of the University 
* of California
* All rights reserved.
*
* This software is open-source under the BSD license; see either
* "license.txt" or
* http://jung.sourceforge.net/license.txt for a description.
*/
package scratch.scott;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import edu.uci.ics.jung.graph.DirectedEdge;
import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.graph.impl.SparseVertex;
import edu.uci.ics.jung.utils.GraphUtils;
import edu.uci.ics.jung.utils.MutableDouble;
import edu.uci.ics.jung.utils.UserData;

/**
 * @author Scott White
 */
public class GraphCollapser {
    private DirectedGraph mGraph;
    private Set mRootSet;
    private Set mCurrentBaseSet;
    private int mMaxIntermediateSize;
    private Set mNonVisitedSet;
    private String mEdgeWeightKey;

    public GraphCollapser(DirectedGraph g, Set rootSet, int maxIntermediateSetSize,String edgeWeightKey) {
        mRootSet = mCurrentBaseSet = rootSet;
        mGraph = g;
        mMaxIntermediateSize = Math.max(100,maxIntermediateSetSize);
        mEdgeWeightKey = edgeWeightKey;
        mNonVisitedSet = new HashSet();

        for (Iterator vIt = mGraph.getVertices().iterator(); vIt.hasNext();) {
            Vertex vertex = (Vertex) vIt.next();
            mNonVisitedSet.add(vertex);
        }

        for (Iterator rootIt = mRootSet.iterator(); rootIt.hasNext();) {
            Vertex root = (Vertex) rootIt.next();
            mNonVisitedSet.remove(root);
        }
    }

    public DirectedGraph collapseNext() {
        DirectedGraph newGraph = (DirectedGraph) mGraph.newInstance();
        Set intermediateSet = new HashSet();


        //Add root nodes to graph
        for (Iterator rootIt = mRootSet.iterator(); rootIt.hasNext();) {
            Vertex root = (Vertex) rootIt.next();
            root.copy(newGraph);
        }

        HashSet basesVisited = new HashSet();
        toplevel:
        //Add intermediate nodes to graph
        for (Iterator baseIt = mCurrentBaseSet.iterator(); baseIt.hasNext();) {
            Vertex base = (Vertex) baseIt.next();

            for (Iterator edgeIt = base.getOutEdges().iterator(); edgeIt.hasNext();) {
                DirectedEdge adjEdge = (DirectedEdge) edgeIt.next();
                Vertex neighbor = (Vertex) adjEdge.getOpposite(base);

                if (mNonVisitedSet.contains(neighbor)) {
                    intermediateSet.add(neighbor);
                    neighbor.copy(newGraph);
                    mNonVisitedSet.remove(neighbor);

                    if (intermediateSet.size() >= mMaxIntermediateSize) {
                        break toplevel;
                    }
                }
            }
           basesVisited.add(base);
        }

        //do this for performance
        if (intermediateSet.size() >= mMaxIntermediateSize) {
            mCurrentBaseSet.removeAll(basesVisited);
            mCurrentBaseSet.addAll(intermediateSet);
        } else {
            mCurrentBaseSet = intermediateSet;
        }

        if (intermediateSet.size() == 0 && mNonVisitedSet.size() == 0) {
            return null;
        } else if (intermediateSet.size() == 0 && mNonVisitedSet.size() != 0) {
            while (intermediateSet.size() >= mMaxIntermediateSize) {
                for (Iterator baseIt = mNonVisitedSet.iterator(); baseIt.hasNext();) {
                    Vertex base = (Vertex) baseIt.next();
                    intermediateSet.add(base);
                }
            }
        }

        //Add collapsed node to graph
        Vertex collapsedVertex = (Vertex) newGraph.addVertex(new SparseVertex());

        //Add edges incident to root
        for (Iterator rootIt = mRootSet.iterator(); rootIt.hasNext();) {
            Vertex root = (Vertex) rootIt.next();

            for (Iterator edgeIt = root.getOutEdges().iterator(); edgeIt.hasNext();) {
                DirectedEdge adjEdge = (DirectedEdge) edgeIt.next();
                Vertex neighbor = (Vertex) adjEdge.getOpposite(root);

                if (mRootSet.contains(neighbor) || intermediateSet.contains(neighbor)) {
                    adjEdge.copy(newGraph);
                } else {
                    Vertex newRoot = (Vertex)root.getEqualVertex(newGraph);
                    DirectedEdge edge = (DirectedEdge) newRoot.findEdge(collapsedVertex);
                    if (edge == null) {
                        if (adjEdge.getSource() == root) {
                            edge = (DirectedEdge) GraphUtils.addEdge(newGraph, newRoot,collapsedVertex);
                        } else {
                            edge = (DirectedEdge) GraphUtils.addEdge(newGraph, collapsedVertex,newRoot);
                        }
                        MutableDouble weight = (MutableDouble) adjEdge.getUserDatum(mEdgeWeightKey);
                        edge.setUserDatum(mEdgeWeightKey,new MutableDouble(weight.doubleValue()),UserData.SHARED);
                    } else {
                        MutableDouble newWeight = (MutableDouble) adjEdge.getUserDatum(mEdgeWeightKey);
                        MutableDouble existingWeight = (MutableDouble) edge.getUserDatum(mEdgeWeightKey);
                        existingWeight.add(newWeight.doubleValue());

                    }
                }
            }
        }

        //Add edges between intermediate set and collapsed node
        for (Iterator intermediateIt = intermediateSet.iterator(); intermediateIt.hasNext();) {
            Vertex intermediateNode = (Vertex) intermediateIt.next();

            for (Iterator edgeIt = intermediateNode.getOutEdges().iterator(); edgeIt.hasNext();) {
                DirectedEdge adjEdge = (DirectedEdge) edgeIt.next();
                Vertex neighbor = (Vertex) adjEdge.getOpposite(intermediateNode);

                if (mRootSet.contains(neighbor) || intermediateSet.contains(neighbor)) {
                    adjEdge.copy(newGraph);
                } else {
                    Vertex newIntermediateNode = 
                        (Vertex)intermediateNode.getEqualVertex(newGraph);
                    DirectedEdge edge = (DirectedEdge) newIntermediateNode.findEdge(collapsedVertex);
                    if (edge == null) {
                        if (adjEdge.getSource() == intermediateNode) {
                            edge = (DirectedEdge) GraphUtils.addEdge(newGraph, newIntermediateNode,collapsedVertex);
                        } else {
                            edge = (DirectedEdge) GraphUtils.addEdge(newGraph, collapsedVertex,newIntermediateNode);
                        }
                        MutableDouble weight = (MutableDouble) adjEdge.getUserDatum(mEdgeWeightKey);
                        edge.setUserDatum(mEdgeWeightKey,new MutableDouble(weight.doubleValue()),UserData.SHARED);
                    } else {
                        MutableDouble newWeight = (MutableDouble) adjEdge.getUserDatum(mEdgeWeightKey);
                        MutableDouble existingWeight = (MutableDouble) edge.getUserDatum(mEdgeWeightKey);
                        existingWeight.add(newWeight.doubleValue());
                    }
                }
            }

        }

        Set verticesToCollapse = new HashSet();
        verticesToCollapse.addAll(mGraph.getVertices());
        verticesToCollapse.removeAll(mRootSet);
        verticesToCollapse.removeAll(intermediateSet);

        //Add edges in between collapsed vertices
        for (Iterator collapsedIt = verticesToCollapse.iterator(); collapsedIt.hasNext();) {
            Vertex nodeToCollapse = (Vertex) collapsedIt.next();

            for (Iterator edgeIt = nodeToCollapse.getOutEdges().iterator(); edgeIt.hasNext();) {
                DirectedEdge adjEdge = (DirectedEdge) edgeIt.next();
                Vertex neighbor = (Vertex) adjEdge.getOpposite(nodeToCollapse);

                if (verticesToCollapse.contains(neighbor)) {
                    DirectedEdge edge = (DirectedEdge) collapsedVertex.findEdge(collapsedVertex);
                    if (edge == null) {
                        edge = (DirectedEdge) GraphUtils.addEdge(newGraph, collapsedVertex,collapsedVertex);
                        MutableDouble weight = (MutableDouble) adjEdge.getUserDatum(mEdgeWeightKey);
                        edge.setUserDatum(mEdgeWeightKey,new MutableDouble(weight.doubleValue()),UserData.SHARED);
                    } else {
                        MutableDouble newWeight = (MutableDouble) adjEdge.getUserDatum(mEdgeWeightKey);
                        MutableDouble existingWeight = (MutableDouble) edge.getUserDatum(mEdgeWeightKey);
                        existingWeight.add(newWeight.doubleValue());
                    }
                } else {
                    Vertex newNonCollapsableNode = (Vertex)neighbor.getEqualVertex(newGraph);
                    DirectedEdge edge = (DirectedEdge) collapsedVertex.findEdge(newNonCollapsableNode);
                    if (edge == null) {
                        edge = (DirectedEdge) GraphUtils.addEdge(newGraph, collapsedVertex,newNonCollapsableNode);
                        MutableDouble weight = (MutableDouble) adjEdge.getUserDatum(mEdgeWeightKey);
                        edge.setUserDatum(mEdgeWeightKey,weight,UserData.SHARED);
                    } else {
                        MutableDouble newWeight = (MutableDouble) adjEdge.getUserDatum(mEdgeWeightKey);
                        MutableDouble existingWeight = (MutableDouble) edge.getUserDatum(mEdgeWeightKey);
                        existingWeight.add(newWeight.doubleValue());
                    }
                }
            }
        }

        return newGraph;
    }

//    Vertex lookup(DirectedGraph newGraph, Vertex originalNode) {
//        int originalId = mGraph.getVertexID(originalNode);
//        return (Vertex) newGraph.getVertexByID(originalId);
//
//    }
}
