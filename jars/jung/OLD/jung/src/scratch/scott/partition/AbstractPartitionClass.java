/*
* Copyright (c) 2003, the JUNG Project and the Regents of the University 
* of California
* All rights reserved.
*
* This software is open-source under the BSD license; see either
* "license.txt" or
* http://jung.sourceforge.net/license.txt for a description.
*/
package scratch.scott.partition;

import edu.uci.ics.jung.utils.UserDataContainer;
import edu.uci.ics.jung.graph.ArchetypeGraph;

import java.util.Collections;
import java.util.Set;
import java.util.HashSet;

/**
 * @author Scott White
 */
public abstract class AbstractPartitionClass {
    private AbstractPartition mUnderlyingPartition;
    private Set mElements;

    AbstractPartitionClass(AbstractPartition p) {
        mElements = new HashSet();
        mUnderlyingPartition = p;
    }

    protected void add(UserDataContainer udc) {
        mElements.add(udc);
    }

    public Set getElements() { return Collections.unmodifiableSet(mElements); }

    public AbstractPartition getUnderlyingPartition() { return mUnderlyingPartition; }

    public abstract ArchetypeGraph constructGraph();

    protected void remove(UserDataContainer udc) {
        mElements.remove(udc);
    }

    public int size() {
        return mElements.size();
    }
}
