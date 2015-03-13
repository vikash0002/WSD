/*
 * Created on Apr 3, 2006
 *
 * Copyright (c) 2006, the JUNG Project and the Regents of the University 
 * of California
 * All rights reserved.
 *
 * This software is open-source under the BSD license; see either
 * "license.txt" or
 * http://jung.sourceforge.net/license.txt for a description.
 */
package scratch.joshua.jung_2_0.decoration;

/**
 * An interface for read-only decorations of arbitrary elements.
 * (This is a generalization of interfaces such as VertexPaintFunction and EdgeStringer
 * in the JUNG 1.x libraries.)  Does not place any constraints on the 
 * uniqueness of decorations; if you want a one-to-one mapping from 
 * elements to decorations, see <code>BidiDecoration</code> or 
 * <code>SettableBidiDecoration</code>.
 * 
 * <p>The purpose of this interface is to provide a simple unifying mechanism
 * for accessing element (meta)data, which may be variously stored in 
 * instance fields, auxiliary data structures such as <code>Map</code> instances,
 * or the JUNG user data repository.</p>
 * 
 * <p>This interface is designed so as to be compatible with the <code>Map</code>
 * interface--that is, so that a <code>Map</code> instance can serve as a <code>Decoration</code>.</p>
 * 
 * <p>Examples of ways to instantiate this interface include:
 * <pre>
 * Decoration<Vertex, String> vertex_stringer = new HashMap<Vertex,String>();
 * Decoration<Edge, Integer> unit_edge_weight = 
 *      new Decoration<Edge, Integer>() 
 *      {
 *          public Integer get(Edge e) { return 1; }
 *      };
 * </pre>
 * 
 * @see SettableDecoration
 * @see BidiDecoration
 * @see SettableBidiDecoration
 * @author Joshua O'Madadhain
 */
public interface Decoration<K, V>
{
    /**
     * Returns the decoration (value) associated with the specified element <code>key</code>.
     */
    V get(K key);
}
