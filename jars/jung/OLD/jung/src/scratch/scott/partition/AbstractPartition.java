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

import java.util.*;

/**
 * @author Scott White
 */
public abstract class AbstractPartition {
    private ArchetypeGraph mGraph;
    private List mElements;
    private Map mElementToClassMap;

    public AbstractPartitionClass addNewPartitionClass() {
        AbstractPartitionClass pc = createNewPartitionClass();
        mElements.add(pc);
        return pc;
    }

    public Set addNewPartitionClasses(int numClasses) {
        Set classes = new HashSet();
        for (int i=0;i<numClasses;i++) {
            classes.add(addNewPartitionClass());
        }
        return classes;
    }

    abstract AbstractPartitionClass createNewPartitionClass();

    public ArchetypeGraph getUnderlyingGraph() { return mGraph; }

    List getElements() { return Collections.unmodifiableList(mElements); }

    public AbstractPartitionClass getNthClass(int n) {
        return (AbstractPartitionClass) mElements.get(n-1);
    }

    protected Map getElementToClassMap() {
        return mElementToClassMap;
    }

    public AbstractPartitionClass getElement(UserDataContainer udc) {
        return (AbstractPartitionClass) mElementToClassMap.get(udc);

    }

    protected void initialize(ArchetypeGraph g) {
        mElements = new ArrayList();
        mGraph = g;
        mElementToClassMap = new HashMap();
    }

    public void classify(UserDataContainer udc,AbstractPartitionClass aPartitionClass) {
        AbstractPartitionClass currentClass = getElement(udc);
        currentClass.remove(udc);
        aPartitionClass.add(udc);
    }

    public void classify(Set elements,AbstractPartitionClass aPartitionClass) {
        for (Iterator eIt=elements.iterator(); eIt.hasNext();) {
            classify((UserDataContainer) eIt.next(),aPartitionClass);
        }
    }

    public int numClasses() {
        return mElements.size();
    }

}
