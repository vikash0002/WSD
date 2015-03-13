/*
* Copyright (c) 2003, the JUNG Project and the Regents of the University 
* of California
* All rights reserved.
*
* This software is open-source under the BSD license; see either
* "license.txt" or
* http://jung.sourceforge.net/license.txt for a description.
*/
package scratch.scott.registry;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.decorators.Decorator;

import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

import scratch.scott.registry.RegistryDecoratorSet;

/**
 * @author Scott White
 */
public abstract class UserDataRegistry {
    private Map mDecoratorMap;
    private Graph mGraph;

    protected UserDataRegistry(Graph graph) {
        mDecoratorMap = new HashMap();
        mGraph = graph;
    }

    /*
     * Checks to see whether there is an entry in the map for this type. If not, creates one.
     */
    protected void checkDecoratorType(Class type) {
        if ( !hasDecoratorType(type)) {
            mDecoratorMap.put(type,new RegistryDecoratorSet());
        }
    }

    protected Graph getGraph() { return mGraph; }

    public boolean hasDecoratorType(Class type) {
        return mDecoratorMap.get(type) != null;
    }

    public void registerDecorator(Decorator decorator) {
        if (!allDataIsDecorated(decorator)) {
            throw new IllegalArgumentException("All user data must be decorated with this decorator before the decorator can be registererd.");
        }
        //checkDecoratorType(decorator.type());
        //RegistryDecoratorSet decoratorSet = (RegistryDecoratorSet) mDecoratorMap.get(decorator.type());
        //decoratorSet.addDecorator(decorator);
    }

    public void registerDecoratorAsDefault(Decorator decorator) {
        if (!allDataIsDecorated(decorator)) {
            throw new IllegalArgumentException("All user data must be decorated with this decorator before the decorator can be registererd.");
        }
        //checkDecoratorType(decorator.type());
        //RegistryDecoratorSet decoratorSet = (RegistryDecoratorSet) mDecoratorMap.get(decorator.type());
        //decoratorSet.addDecoratorAsDefault(decorator);
    }

    public RegistryDecoratorSet getDecorators(Class type) {
        return (RegistryDecoratorSet) mDecoratorMap.get(type);
    }

    public Decorator getDefaultDecorator(Class type) {

        RegistryDecoratorSet decoratorSet = (RegistryDecoratorSet) mDecoratorMap.get(type);
        return decoratorSet.getDefaultDecorator();
    }

    public Iterator getDecoratorIterator(Class type) {
        RegistryDecoratorSet decoratorSet = (RegistryDecoratorSet) mDecoratorMap.get(type);
        return decoratorSet.getDecorators().iterator();
    }

    public abstract boolean allDataIsDecorated(Decorator decorator);

}
