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

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @author Scott White
 */
public class TestVertexRegistry extends TestCase {

	public static Test suite() {
		return new TestSuite(TestVertexRegistry.class);
	}

	protected void setUp() {

	}

	public void testSimpleRegistryScenario() {
        /*
		DirectedSparseGraph graph = new DirectedSparseGraph();
		GraphUtils.addVertices(graph, 2);

		VertexRegistry registry = new VertexRegistry(graph);
		//this will be moved into graph

		// Now decorate vertices with their names
		StringDecorator nameLabeler =
			new StringDecorator("name", UserData.REMOVE);
		Indexer id = Indexer.getIndexer(graph);
		nameLabeler.setValue("Bob", id.getVertex(0));
		nameLabeler.setValue("Joe", id.getVertex(1));

		registry.registerDecorator(nameLabeler);
		//once all the vertices have been decorated then you can go ahead and register the decorator

		// Now decorate vertices with their ages

		StringDecorator ageLabeler =
			new StringDecorator("age", UserData.REMOVE);

		ageLabeler.setValue("37", id.getVertex(0));
		ageLabeler.setValue("54", id.getVertex(1));

		registry.registerDecoratorAsDefault(ageLabeler);
		//override the name labeler as the default labeler

		//Now we ask the registry. Does this vertex have any string labels?
		boolean hasDecorator = registry.hasDecoratorType(StringDecorator.class);

		if (hasDecorator) {
			StringDecorator defaultLabeler =
				(StringDecorator) registry.getDefaultDecorator(
					StringDecorator.class);
			Assert.assertEquals(
				defaultLabeler.getValue(id.getVertex(0)),
				"37");
			Assert.assertEquals(
				defaultLabeler.getValue(id.getVertex(1)),
				"54");


		}
        */
	}
}
