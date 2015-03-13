/*
 * Created on Jan 6, 2002
 *
 */
package scratch.scott;

import java.io.*;
import java.util.*;
import cern.colt.matrix.*;
import cern.colt.matrix.impl.*;
import edu.uci.ics.jung.graph.impl.*;
import edu.uci.ics.jung.graph.*;
import edu.uci.ics.jung.algorithms.cluster.*;
import edu.uci.ics.jung.algorithms.shortestpath.*;

/**
 * @author Scott
 *
 */
public class UserExample {
	
		static ArrayList x = new ArrayList();
		static ArrayList y = new ArrayList();
		static ArrayList d = new ArrayList();

		public static void main(String[] args) throws IOException {
			CSVParser csv = new CSVParser();
			BufferedReader is = new BufferedReader(new FileReader("c:\\jdistance.csv"));//args[0]));
			DoubleMatrix2D m = process(csv, is);
			double intervalSize = 2000;//Double.parseDouble(args[1]);
			double maxDistance = 2638288;//Double.parseDouble(args[2]);
			double numberOfIntervals = (maxDistance/intervalSize);
			for (int a=0;a<numberOfIntervals;a++){
				double thresholdDistance = a*intervalSize;
				Graph g = constructGraph(thresholdDistance,m);
				int d = largestComponentDiameter(g);
				//System.out.println(d+","+thresholdDistance);
			}
		}
	
		protected static Graph constructGraph (double td, DoubleMatrix2D m){
			Graph g = new UndirectedSparseGraph();
			for (int x=1;x<m.size();x++){
				int n=1;
				while (x+n<129){
					double d = m.get(x+n,n);
					if (d <= td){
						ArchetypeVertex a = g.addVertex(new SparseVertex());
						ArchetypeVertex b = g.addVertex(new SparseVertex());
						ArchetypeEdge e = g.addEdge(new UndirectedSparseEdge((Vertex)(a),(Vertex)(b)));
					}
					n++;
				}
			}
			return g;
		}
	
		protected static int largestComponentDiameter (ArchetypeGraph g){
			//System.out.println(g.numEdges());
			UnweightedShortestPath usp = new UnweightedShortestPath((Graph)(g));
			WeakComponentClusterer B = new WeakComponentClusterer();
			ClusterSet C = B.extract((Graph)(g));
			C.sort();
			int Sz = C.size();
			System.out.println(g.numVertices() + " " + g.numEdges() + "# clusters: " + Sz);
			if (Sz == 0) return 0;
			Set largestCluster = C.getCluster(0);
			//System.out.println(largestCluster.size());
			List vertices = new ArrayList(largestCluster);
			int numVertices = largestCluster.size();
			int diameter = 0;
			for (int i = 0; i < numVertices - 1; i++) {
			  for (int j = i + 1; j < numVertices; j++) {
                Number n = usp.getDistance((Vertex) vertices.get(i), 
                        (Vertex) vertices.get(j));
                if (n != null && n.intValue() > diameter)
                    diameter = n.intValue();
			  }
			}
			return diameter;
		}
	
		protected static DoubleMatrix2D process(CSVParser csv, BufferedReader is) throws IOException {
				String line;
				DoubleMatrix2D m = new SparseDoubleMatrix2D(129,129);
				while ((line = is.readLine()) != null) {
					Iterator e = csv.parse(line);
					while (e.hasNext()) {
					  x.add(e.next());
					  y.add(e.next());
					  d.add(e.next());
					}
				}

			while (x.isEmpty() == false) {
			  int xValue = Integer.parseInt(x.get(0).toString());
			  int yValue = Integer.parseInt(y.get(0).toString());
			  double dist = Double.parseDouble(d.get(0).toString());
			  m.set(xValue,yValue,dist);
			  x.remove(0);
			  y.remove(0);
			  d.remove(0);
			}
			return m;
		}	
	}

