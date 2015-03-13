package scratch.tom.simpleGraph;

public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) {

        /*
         * Make a graph that is compile-time type-safe as an
         * 'undirected graph of Integer vertices'.
         */
        Graph<Integer, UndirectedEdge<Integer>> undirectedGraph =
            new DefaultGraph<Integer, UndirectedEdge<Integer>>();
        
        undirectedGraph.addEdge(new UndirectedEdge<Integer>(1,2));
        undirectedGraph.addEdge(new UndirectedEdge<Integer>(2,3));
        undirectedGraph.addEdge(new UndirectedEdge<Integer>(3,4));
        undirectedGraph.addEdge(new UndirectedEdge<Integer>(4,1));
        
        // note these are compile time errors: (cool!)
        //undirectedGraph.addEdge(new DirectedEdge<Integer>(1,3));
        //undirectedGraph.addEdge(new UndirectedEdge<String>("1", "2"));
        
        /*
         * make a Graph that is compile-time type-safe as a
         * 'directed graph od String vertices'
          * 
         * An instance of this graph can be passed to a method that
         * requires a Graph<?, DirectedEdge<?>, in other words, a
         * 'directed graph', without us needing to declare a 'DirectedGraph'
         * interface.
        */
        Graph<String, DirectedEdge<String>> directedGraph =
            new DefaultGraph<String, DirectedEdge<String>>();
        
        directedGraph.addEdge(new DirectedEdge<String>("a", "b"));
        directedGraph.addEdge(new DirectedEdge<String>("b", "c"));
        directedGraph.addEdge(new DirectedEdge<String>("c", "d"));
        directedGraph.addEdge(new DirectedEdge<String>("d", "a"));
        
        
        //try a static method call:
        GraphUtils.doSomethingThatNeedsDirectedGraph(directedGraph);
        // compile time error:
        //GraphUtils.doSomethingThatNeedsDirectedGraph(undirectedGraph);
        
        // make an instance so I can call an instance method:
        Main main = new Main();
        main.needDirectedGraphHere(directedGraph);
        // compile-time error:
        //main.needDirectedGraphHere(undirectedGraph);
        
        // edges connect vertex types that subclass Number
        Graph<Number, DirectedEdge<Number>> relatedTypesDirectedGraph =
            new DefaultGraph<Number, DirectedEdge<Number>>();
        // add edge that connects Integer and Double vertices:
        relatedTypesDirectedGraph.addEdge(new DirectedEdge<Number>(1, 5.0));
        
        // edges connect vertex types of any kind
        Graph<Object, UndirectedEdge<Object>> unrelatedTypesGraph =
            new DefaultGraph<Object, UndirectedEdge<Object>>();
        // add edge that connects String and Integer vertices:
        unrelatedTypesGraph.addEdge(new UndirectedEdge<Object>("a string", 2));
        
        
    
    }

    public void needDirectedGraphHere(Graph<? extends Object, ? extends DirectedEdge> directedGraph) {
        // nothing to do but check method call at compile time
    }

}
