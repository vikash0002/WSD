package scratch.scott.demo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.ui.RefineryUtilities;

import cern.colt.list.DoubleArrayList;
import edu.uci.ics.jung.graph.UndirectedGraph;
import edu.uci.ics.jung.graph.decorators.Indexer;
import edu.uci.ics.jung.random.generators.BarabasiAlbertGenerator;
import edu.uci.ics.jung.statistics.DegreeDistributions;
import edu.uci.ics.jung.statistics.GraphStatistics;
import edu.uci.ics.jung.utils.GraphUtils;
import edu.uci.ics.jung.visualization.FRLayout;
import edu.uci.ics.jung.visualization.GraphDraw;
import edu.uci.ics.jung.visualization.PluggableRenderer;
import edu.uci.ics.jung.visualization.VisualizationViewer;


/**
 * @author Scott White
 */
public class GraphDistributionsDemo extends JFrame {
    public GraphDistributionsDemo(String title) {
        super(title);
    }

    public static void main(String[] args) {

        BarabasiAlbertGenerator graphGenerator = new BarabasiAlbertGenerator(3,3);
        graphGenerator.evolveGraph(100);
        UndirectedGraph graph = (UndirectedGraph) graphGenerator.generateGraph();

        VisualizationViewer vv = new VisualizationViewer(new FRLayout(graph), new PluggableRenderer());
        vv.setBackground(Color.WHITE);

        Indexer id = Indexer.getIndexer(graph);
        
        double[] degrees = DegreeDistributions.getIndegreeValues(graph.getVertices()).elements();
        ChartPanel degreeChartPanel = createHistogramChart(degrees, "Degree Distribution", 10);

        Map cc_map = GraphStatistics.clusteringCoefficients(graph);
        DoubleArrayList cc = GraphUtils.vertexMapToDAL(cc_map, id);
//        double[] ccs = GraphStatistics.clusteringCoefficients(graph).elements();
        double[] ccs = cc.elements();
        ChartPanel ccChartPanel = createHistogramChart(ccs, "Clustering Coefficient Distribution", 10);

        JPanel chartPanel = new JPanel(new FlowLayout());
        chartPanel.add(degreeChartPanel);
        chartPanel.add(ccChartPanel);
        vv.add(chartPanel,BorderLayout.SOUTH);

        GraphDistributionsDemo demo = new GraphDistributionsDemo("Demo");
        demo.setContentPane(vv);
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);

        while (true) {
            try { Thread.sleep(1000); } catch (Exception e) { }
            //graphGenerator.evolveGraph(10);
            //demo.repaint();
        }

    }

    private static ChartPanel createHistogramChart(double[] values, String title, int numBins) {
        HistogramDataset dataset = new HistogramDataset();
        dataset.addSeries("", values, numBins);
        JFreeChart chart = ChartFactory.createHistogram(title, null, null, dataset, PlotOrientation.VERTICAL, true, false, false);
        chart.getXYPlot().setForegroundAlpha(0.75f);
//        chart.setLegend(null);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(500, 270));
        return chartPanel;
    }

}
