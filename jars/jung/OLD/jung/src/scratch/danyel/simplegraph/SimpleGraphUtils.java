/*
 * Created on Oct 16, 2005
 */
package scratch.danyel.simplegraph;

/**
 * 
 * @author danyelf
 */
public class SimpleGraphUtils {
	
	public static int degree( SimpleGraph sg, Object vert ) {
		return sg.getVertex(vert).degree();
	}	

}
