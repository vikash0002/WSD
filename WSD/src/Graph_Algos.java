//import com.sun.corba.se.impl.orbutil.graph.Graph;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.collections15.Transformer;
import edu.uci.ics.jung.algorithms.importance.BetweennessCentrality;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;


public class Graph_Algos
{
static int edgeCount_Directed = 0;   // This works with the inner MyEdge class
 class MyNode
{
  //static int edgeCount = 0;   // This works with the inner MyEdge class
  String id;
  public MyNode(String id)
  {
  this.id = id;
  }
  public String toString()
  {
  return "V"+id;
  }
}

 class MyLink
{
   double weight;
   int id;

   public MyLink(double weight)
   {
    this.id = edgeCount_Directed++;
    this.weight = weight;
   }

   public String toString()
   {
    return "E"+id;
   }
}
//used to construct graph and call graph algorithm used in JUNG
 public ArrayList<Double> BetweenNess_Centrality_Score(LinkedList<String> Distinct_nodes, LinkedList<String> source_vertex, LinkedList<String> target_vertex, LinkedList<Double> Edge_Weight) throws FileNotFoundException, UnsupportedEncodingException
{
//CREATING weighted directed graph
        //PrintWriter writer = new PrintWriter("betweennessCentrality.txt", "UTF-8");
        ArrayList<Double> scores = new ArrayList<Double>();
        Graph<MyNode, MyLink> g = new DirectedSparseGraph<Graph_Algos.MyNode, Graph_Algos.MyLink>();
        //create node objects
        Hashtable<String, MyNode> Graph_Nodes = new Hashtable<String, Graph_Algos.MyNode>();
        LinkedList<MyNode> Source_Node = new LinkedList<Graph_Algos.MyNode>();
        LinkedList<MyNode> Target_Node = new LinkedList<Graph_Algos.MyNode>();
        LinkedList<MyNode> Graph_Nodes_Only = new LinkedList<Graph_Algos.MyNode>();
        //LinkedList<MyLink> Graph_Links = new LinkedList<Graph_Algos.MyLink>();
        //create graph nodes
        for(int i=0;i<Distinct_nodes.size();i++)
        {
            String node_name = Distinct_nodes.get(i);
            MyNode data = new MyNode(node_name);
            Graph_Nodes.put(node_name, data);
            Graph_Nodes_Only.add(data);
        }
        //Now convert all source and target nodes into objects
        for(int t=0;t<source_vertex.size();t++)
        {
            Source_Node.add(Graph_Nodes.get(source_vertex.get(t)));
            Target_Node.add(Graph_Nodes.get(target_vertex.get(t)));
        }
        //Now add nodes and edges to the graph
        for(int i=0;i<Edge_Weight.size();i++)
        {
            g.addEdge(new MyLink(Edge_Weight.get(i)),Source_Node.get(i), Target_Node.get(i), EdgeType.DIRECTED);
        }
        Transformer<MyLink, Double> wtTransformer = new Transformer<MyLink,Double>()
        {
                public Double transform(MyLink link)
                {
                return link.weight;
                }
           };
        edu.uci.ics.jung.algorithms.scoring.BetweennessCentrality<MyNode,MyLink> BC1 = new edu.uci.ics.jung.algorithms.scoring.BetweennessCentrality<MyNode, MyLink>(g,wtTransformer);
        //Calculating Betweenness Centrality score of nodes
        for(int i=0;i<Graph_Nodes_Only.size();i++)
        {
            System.out.println(i + "   Graph Node "+Graph_Nodes_Only.get(i)+" Betweenness Centrality "+BC1.getVertexScore(Graph_Nodes_Only.get(i)));
            scores.add(BC1.getVertexScore(Graph_Nodes_Only.get(i)));
           
            /*writer.print("shubh = " + BC1.getVertexScore(Graph_Nodes_Only.get(i)).toString());
            writer.print("\n");*/
        }
                //Calculating Betweenness centrality score of edges:
/*        Collection<MyLink> link1 = g.getEdges();
        Iterator<MyLink> keys1 = link1.iterator();
        while(keys1.hasNext())
        
        {
        MyLink link2 = keys1.next();
        System.out.println("Graph Edge "+ link2+" Betweenness centrality score "+BC1.getEdgeScore(link2));
        }
*/
    return scores;
}
/* public  ArrayList<Double>  x(double [][]g) throws FileNotFoundException, UnsupportedEncodingException
{
/*LinkedList<String> Distinct_Vertex = new LinkedList<String>();
LinkedList<String> Source_Vertex = new LinkedList<String>();
LinkedList<String> Target_Vertex = new LinkedList<String>();
LinkedList<Double> Edge_Weight = new LinkedList<Double>();
//add the distinct vertexes
Distinct_Vertex.add("A");
Distinct_Vertex.add("B");
Distinct_Vertex.add("C");
Distinct_Vertex.add("D");
Distinct_Vertex.add("E");
Distinct_Vertex.add("F");
Distinct_Vertex.add("G");

Source_Vertex.add("A"); Target_Vertex.add("B"); Edge_Weight.add(754.0);
Source_Vertex.add("B"); Target_Vertex.add("C"); Edge_Weight.add(5459.0);
Source_Vertex.add("C"); Target_Vertex.add("D"); Edge_Weight.add(0.57);
Source_Vertex.add("D"); Target_Vertex.add("B"); Edge_Weight.add(1545.0);
Source_Vertex.add("C"); Target_Vertex.add("A"); Edge_Weight.add(1.3);
Source_Vertex.add("A"); Target_Vertex.add("D"); Edge_Weight.add(0.3);
Source_Vertex.add("D"); Target_Vertex.add("F"); Edge_Weight.add(0.2);
Source_Vertex.add("D"); Target_Vertex.add("E"); Edge_Weight.add(0.8);
Source_Vertex.add("E"); Target_Vertex.add("G"); Edge_Weight.add(0.4);
Source_Vertex.add("F"); Target_Vertex.add("E"); Edge_Weight.add(0.6);
Source_Vertex.add("G"); Target_Vertex.add("F"); Edge_Weight.add(0.2);
//System.out.println("Graph \n "+g);
System.out.println("Betweenness calculation ");
Graph_Algos gb1 = new Graph_Algos();
 ArrayList<Double> ans = gb1.getCentralityScores(g);
//gb1.BetweenNess_Centrality_Score(Distinct_Vertex, Source_Vertex, Target_Vertex, Edge_Weight);
 return ans;
    
}*/
 public  ArrayList<Double> getCentralityScores(double [][]g) throws FileNotFoundException, UnsupportedEncodingException
{
    
    LinkedList<String> Distinct_Vertex = new LinkedList<>();
    LinkedList<String> Source_Vertex = new LinkedList<String>();
    LinkedList<String> Target_Vertex = new LinkedList<String>();
    LinkedList<Double> Edge_Weight = new LinkedList<Double>();
    int nodesCount = GlobalClass.labels.size();
    int nodeNo = 0;
    System.out.println("nodesSize ===============================================================" + nodesCount);
    for(int j=0; j<GlobalClass.labels.size(); j++){
        System.out.println(" node no =  "+String.valueOf(nodeNo));
        Distinct_Vertex.add(String.valueOf(nodeNo));
        nodeNo++;
    }
    
    
    for(int i=0; i<nodesCount; i++){
        for(int j=0; j<nodesCount; j++){
            if(i == j){
                g[i][j] = 0;
            }
            else if(g[i][j] !=0) {
                Source_Vertex.add(String.valueOf(i)); Target_Vertex.add(String.valueOf(j)); Edge_Weight.add(g[i][j]);
            }
            System.out.print( g[i][j] + "  ");
        }
        System.out.println("\n");
    }
    /*System.out.println("DDDDDDDDD");
    for(int i=0;i<nodesCount;i++){
        System.out.println(Distinct_Vertex.get(i));
    }
    System.out.println("ssssssss");
    for(int i=0;i<nodesCount*nodesCount;i++){
        System.out.println(Source_Vertex.get(i));
    }
    System.out.println("ttttttttt");
    for(int i=0;i<nodesCount*nodesCount;i++){
        System.out.println(Target_Vertex.get(i));
    }
    System.out.println("eeeeeeeee");
    for(int i=0;i<nodesCount*nodesCount;i++){
        System.out.println(Edge_Weight.get(i));
    }*/
    ArrayList<Double> ans = BetweenNess_Centrality_Score(Distinct_Vertex, Source_Vertex, Target_Vertex, Edge_Weight);

    
    
    
    
    
    return ans;
}
public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException
{
// TODO Auto-generated method stub
//let the nodes of graph are: {A, B, C, D, E, F, G}
//Directed edges are: {AB=0.7, BC=0.9, CD=0.57, DB=1.0, CA=1.3, AD=0.3, DF=0.2, DE=0.8, EG=0.4, FE=0.6, GF=0.2}
LinkedList<String> Distinct_Vertex = new LinkedList<String>();
LinkedList<String> Source_Vertex = new LinkedList<String>();
LinkedList<String> Target_Vertex = new LinkedList<String>();
LinkedList<Double> Edge_Weight = new LinkedList<Double>();
//add the distinct vertexes
Distinct_Vertex.add("A");
Distinct_Vertex.add("B");
Distinct_Vertex.add("C");
Distinct_Vertex.add("D");
Distinct_Vertex.add("E");
Distinct_Vertex.add("F");
Distinct_Vertex.add("G");
Distinct_Vertex.add("h");
Distinct_Vertex.add("i");
Distinct_Vertex.add("j");
Distinct_Vertex.add("k");
Distinct_Vertex.add("l");
Distinct_Vertex.add("m");
Distinct_Vertex.add("n");
Distinct_Vertex.add("o");
Distinct_Vertex.add("p");
Distinct_Vertex.add("q");
Distinct_Vertex.add("r");
Distinct_Vertex.add("s");
Distinct_Vertex.add("t");
Distinct_Vertex.add("u");
Distinct_Vertex.add("v");
Distinct_Vertex.add("w");
Distinct_Vertex.add("x");
Distinct_Vertex.add("y");
Distinct_Vertex.add("z");

Source_Vertex.add("A"); Target_Vertex.add("B"); Edge_Weight.add(1.491654876777717 );
Source_Vertex.add("B"); Target_Vertex.add("C"); Edge_Weight.add(1.6094452164341003);
Source_Vertex.add("C"); Target_Vertex.add("D"); Edge_Weight.add(0.916290731874155);
Source_Vertex.add("D"); Target_Vertex.add("B"); Edge_Weight.add(0.0);
Source_Vertex.add("C"); Target_Vertex.add("A"); Edge_Weight.add(1.6094374147441003);
Source_Vertex.add("A"); Target_Vertex.add("D"); Edge_Weight.add(0.0);
Source_Vertex.add("D"); Target_Vertex.add("F"); Edge_Weight.add(1.2039728043259361);
Source_Vertex.add("D"); Target_Vertex.add("E"); Edge_Weight.add(0.916290731874155 );
Source_Vertex.add("E"); Target_Vertex.add("G"); Edge_Weight.add(2.0794415416798357);
Source_Vertex.add("F"); Target_Vertex.add("E"); Edge_Weight.add(1.540445040947149);
Source_Vertex.add("G"); Target_Vertex.add("h"); Edge_Weight.add(1.6999991156341003);
Source_Vertex.add("G"); Target_Vertex.add("i"); Edge_Weight.add(1.6087542156341003);
Source_Vertex.add("G"); Target_Vertex.add("j"); Edge_Weight.add(1.6085421659741003);
Source_Vertex.add("G"); Target_Vertex.add("k"); Edge_Weight.add(1.9966698776341003);
Source_Vertex.add("G"); Target_Vertex.add("l"); Edge_Weight.add(0.6087745826341003);
Source_Vertex.add("G"); Target_Vertex.add("m"); Edge_Weight.add(1.4141412156341003);
Source_Vertex.add("G"); Target_Vertex.add("n"); Edge_Weight.add(1.020101156341003);
Source_Vertex.add("G"); Target_Vertex.add("o"); Edge_Weight.add(1.6546542156341003);
Source_Vertex.add("G"); Target_Vertex.add("p"); Edge_Weight.add(1.7485942156341003);


//System.out.println("Graph \n "+g);
System.out.println("Betweenness calculation ");
Graph_Algos gb = new Graph_Algos();
//gb.x();
gb.BetweenNess_Centrality_Score(Distinct_Vertex, Source_Vertex, Target_Vertex, Edge_Weight);
}
}