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
 * An interface for readable and writeable one-to-one decorations of arbitrary elements.
 * (This is similar to classes such as StringLabeller
 * in the JUNG 1.x libraries.)  Constrains the decorations to be unique (that is,
 * each decoration may be used by only one element, and therefore the decorations
 * uniquely identify the elements and vice versa). 
 * If you want an unconstrained mapping from 
 * elements to decorations, see <code>Decoration</code> or 
 * <code>SettableDecoration</code>.
 * 
 * <p>The behavior of implementations of this interface when a duplicate 
 * assignment is made (either two elements are assigned the same decoration, or
 * one element is assigned to two decorations) is undefined.  Implementors
 * may choose to throw exceptions for either of these conditions, both, or neither.</p>
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
 * SettableBidiDecoration<Vertex, String> vertex_labels = new DualHashBidiMap<Vertex,String>();
 * SettableBidiDecoration<Edge, Integer> edge_ids = 
 *      new SettableBidiDecoration<Edge, Integer>(BidiMap<Edge, Integer> bm) 
 *      {
 *          public Integer get(Edge e) { return bm.get(e); }
 *          public Edge getKey(Integer i) { return bm.getKey(i); }
 *          public Integer put(Edge e, Integer i) { return bm.put(e, i); }
 *      };
 * </pre>
 * 
 * @see BidiDecoration
 * @see Decoration
 * @see SettableDecoration
 * 
 * @author Joshua O'Madadhain
 */
public interface SettableBidiDecoration<K, V> extends BidiDecoration<K, V>, SettableDecoration<K,V> {}
