/*
 * Created on Aug 30, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package scratch.biao;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.io.GraphMLFile;

/**
 * @author Yan-Biao Boey
 */
public class TestGraphML {

    public static void main(String[] args) {
    	GraphMLFile gmlFile = new GraphMLFile();
    	//Graph g = gmlFile.load("D:\\Research\\dawit.xml");
		Graph g = gmlFile.load("D:\\Research\\test2.xml");
    }
}
