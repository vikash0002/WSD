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
 * An interface for readable and writeable decorations of arbitrary elements.
 * (This is a generalization of interfaces such as NumberEdgeValue
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
 * interface--that is, so that a <code>Map</code> instance can serve as a 
 * <code>SettableDecoration</code>.</p>
 * 
 * <p>Examples of ways to instantiate this interface include:
 * <pre>
 * SettableDecoration<Vertex, String> vertex_stringer = new HashMap<Vertex,String>();
 * SettableDecoration<MyEdge, Integer> edge_weight = 
 *      new Decoration<MyEdge, Integer>() 
 *      {
 *          public Integer get(MyEdge e) { return e.getWeight(); }
 *          public void put(MyEdge e, Integer i) { e.setWeight(i); }
 *      };
 * </pre>
 * (The second example assumes that you have defined a class called <code>MyEdge</code>
 * that provides <code>getWeight()</code> and <code>setWeight</code> methods.)
 * 
 * @see Decoration
 * @see BidiDecoration
 * @see SettableBidiDecoration
 * @author Joshua O'Madadhain
 */
public interface SettableDecoration<K,V> extends Decoration<K,V>
{
    V put(K key, V value);
}
