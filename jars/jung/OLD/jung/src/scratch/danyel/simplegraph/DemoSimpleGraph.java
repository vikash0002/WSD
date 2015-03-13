/*
 * Created on Oct 16, 2005
 */
package scratch.danyel.simplegraph;

/**
 * 
 * @author danyelf
 */
public class DemoSimpleGraph {
	
	public static void main( String[] s ) {
		SimpleGraph sg = new SimpleGraph();
		sg.add( "Test");
		sg.add( "Me");
		sg.add( "Please");
		sg.addEdge( "Test", "Me" );
		sg.addEdge("Test", "Please");
		try {
			sg.addEdge("Test", "badly");
			System.out.println("Shouldn't get here!");
		} catch( IllegalArgumentException iae ) {
			System.out.println("Can't add illegal edges");
		} 
		System.out.println("Degree of test is: " + SimpleGraphUtils.degree( sg, "Test"));
		
	}

}
