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

import edu.uci.ics.jung.graph.decorators.Decorator;

import java.util.Set;
import java.util.LinkedHashSet;
import java.util.Collections;

/**
 * @author Scott White
 */
public class RegistryDecoratorSet {
    private Set mDecorators;
    private Decorator mDefaultDecorator;

    public RegistryDecoratorSet() {
        mDecorators = new LinkedHashSet();
    }

    public void addDecoratorAsDefault(Decorator decorator) {
        if (!mDecorators.isEmpty()) {
            mDefaultDecorator = decorator;
        }
        addDecorator(decorator);
    }

    public void addDecorator(Decorator decorator) {
        if (mDecorators.isEmpty()) {
            mDefaultDecorator = decorator;
        }
        mDecorators.add(decorator);
    }

    public boolean containsDecorator(Decorator decorator) {
        return mDecorators.contains(decorator);
    }

    public Decorator getDefaultDecorator() {
        return mDefaultDecorator;
    }

    public Set getDecorators() {
        return Collections.unmodifiableSet(mDecorators);
    }

    public void setDefaultDecorator(Decorator decorator) {
        if (!mDecorators.contains(decorator)) {
            throw new IllegalArgumentException("Specified decoratow with key " + decorator.getKey() + " could not be found.");
        }

        mDefaultDecorator = decorator;
    }
}
