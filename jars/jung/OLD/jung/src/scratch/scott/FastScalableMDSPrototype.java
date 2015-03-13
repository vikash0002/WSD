/*
 * Created on Dec 26, 2001
 *
 */
package scratch.scott;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;

import cern.jet.stat.Descriptive;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.graph.filters.Filter;
import edu.uci.ics.jung.graph.filters.UnassembledGraph;
import edu.uci.ics.jung.graph.filters.impl.KNeighborhoodFilter;
import edu.uci.ics.jung.random.generators.Lattice2DGenerator;
import edu.uci.ics.jung.statistics.DegreeDistributions;
import edu.uci.ics.jung.visualization.FRLayout;
import edu.uci.ics.jung.visualization.GraphDraw;

/**
 * @author Scott
 *
 */
public class FastScalableMDSPrototype
{
	Graph mGraph;
	List mFiltrations;
	List mNodeDisjointPartition;
	int mNumFiltrations;
	int mAverageDegree;

	/**
	 * 
	 */
	public FastScalableMDSPrototype(Graph g, int k)
	{
		if (mNumFiltrations < 1)
		{
			throw new IllegalArgumentException("# filtrations must be greater than 1");
		}
		mNumFiltrations = k;
		mGraph = g;
		mFiltrations = new ArrayList();
		mNodeDisjointPartition = new ArrayList();
		mAverageDegree =
			(int) Descriptive.mean(
				DegreeDistributions.getIndegreeValues(mGraph.getVertices()));
	}

	private void computePositions()
	{
		for (int i = mNodeDisjointPartition.size() - 1; i >= 0; i--)
		{
			Set disjointVertexSet = (Set) mNodeDisjointPartition.get(i);
			int neighborhoodSize = computeNeighborhoodSize(i);
			for (Iterator vIt = disjointVertexSet.iterator(); vIt.hasNext();)
			{
				Vertex v = (Vertex) vIt.next();
				List neighborhoods = new ArrayList();
				for (int j = 0; j < i; j++)
				{
					Set filtration = (Set) mFiltrations.get(j);
					Set neighborhood =
						computeNeighborhood(filtration, v, neighborhoodSize);
					neighborhoods.add(neighborhood);
				}

			}
		}

	}

	private Set computeNeighborhood(
		Set filtration,
		Vertex v,
		int neighborhoodSize)
	{
		return null;
	}

	private int computeNeighborhoodSize(int i)
	{
		Set filtration = (Set) mFiltrations.get(i);
		return (int) mGraph.numVertices() * mAverageDegree / filtration.size();
	}

	private List createFiltrations()
	{

		Set previousFiltration = mGraph.getVertices();
		mFiltrations.add(previousFiltration);

		Set currentFiltration = null;
		int i = 0;
		do
		{
			Set disjointPartitionElement = new HashSet();
			int minDistance = (int) Math.pow(2, i);
			Set baseSet = new HashSet(previousFiltration);
			currentFiltration = new HashSet();
			while (!baseSet.isEmpty())
			{
				Vertex randomVertex = (Vertex) baseSet.iterator().next();
				currentFiltration.add(randomVertex);
				Filter nf =
					new KNeighborhoodFilter(
						randomVertex,
						minDistance,
						KNeighborhoodFilter.IN_OUT);
				UnassembledGraph ug = nf.filter(mGraph);
				Set verticesToRemove = ug.getUntouchedVertices();
				baseSet.removeAll(verticesToRemove);
				disjointPartitionElement.addAll(verticesToRemove);
			}
			if (currentFiltration.size() < 3)
				break;
			mNodeDisjointPartition.add(disjointPartitionElement);
			mFiltrations.add(currentFiltration);
			previousFiltration = currentFiltration;
			i++;
		}
		while (true);

		if (mFiltrations.size() > 2)
		{
			recalibrateFiltration(i, 3);
		}

		return mFiltrations;
	}

	private void recalibrateFiltration(int i, int desiredSize)
	{
		Set disjointPartitionI_1 = (Set) mNodeDisjointPartition.get(i - 1);
		Set filtrationI = (Set) mFiltrations.get(i);
		Set filtrationI_1 = (Set) mFiltrations.get(i - 1);
		int size = filtrationI.size();
		int numVerticesToMove = size - desiredSize;
		if (numVerticesToMove <= 0)
			return;
		List verticesToMove = new ArrayList(filtrationI);
		verticesToMove = verticesToMove.subList(0, numVerticesToMove);
		disjointPartitionI_1.addAll(verticesToMove);
		filtrationI.removeAll(verticesToMove);
		filtrationI_1.addAll(verticesToMove);
	}

	public static void main(String[] args)
	{
		Lattice2DGenerator generator = new Lattice2DGenerator(10, false);
		Graph g = (Graph) generator.generateGraph();
		GraphDraw gd = new GraphDraw(g);
		gd.getVisualizationViewer().setGraphLayout(new FRLayout(g));

		JFrame jf = new JFrame();
		jf.getContentPane().add(gd);

		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.pack();
		jf.show();
	}
}
