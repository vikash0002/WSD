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

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class TestMap<K,V> implements Map<K,V>, Decoration<K,V>
{

    public int size()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    public boolean isEmpty()
    {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean containsKey(Object key)
    {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean containsValue(Object value)
    {
        // TODO Auto-generated method stub
        return false;
    }

    public V get(Object key)
    {
        // TODO Auto-generated method stub
        return null;
    }

    public V put(K key, V value)
    {
        // TODO Auto-generated method stub
        return null;
    }

    public V remove(Object key)
    {
        // TODO Auto-generated method stub
        return null;
    }

    public void putAll(Map t)
    {
        // TODO Auto-generated method stub

    }

    public void clear()
    {
        // TODO Auto-generated method stub

    }

    public Set keySet()
    {
        // TODO Auto-generated method stub
        return null;
    }

    public Collection values()
    {
        // TODO Auto-generated method stub
        return null;
    }

    public Set entrySet()
    {
        // TODO Auto-generated method stub
        return null;
    }

    public static void main(String[] args)
    {
        Decoration<Object, Integer> object_string = new Decoration<Object, Integer>() {
            public Integer get(Object o)
            {
                return 1;
            }
        };
    }
    
}
