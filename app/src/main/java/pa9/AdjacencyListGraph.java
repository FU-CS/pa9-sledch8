package pa9;
import java.util.*;

public class AdjacencyListGraph {
    private static class Edge implements Comparable<Edge>{
        int source;
        int destination;
        int weight;
    
        public Edge(int source, int destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
    
        // Compare edges based on their weight
        @Override
        public int compareTo(Edge other) {
            return Integer.compare(this.weight, other.weight);
        }
    }

    private List<List<Edge>> adjList;
    private int vertices;
    private List<Edge> edges;

    public AdjacencyListGraph(int vertices){
        this.vertices = vertices;
        adjList = new ArrayList<>(vertices);
        edges = new ArrayList<>();
        for (int i = 0; i < vertices; i++) {
            adjList.add(new LinkedList<>());
        }
        
    }

    public void addWeightedEdge(int v, int w, int weight) {
        adjList.get(v).add(new Edge(v, w, weight));
        adjList.get(w).add(new Edge(w, v, weight));
        edges.add(new Edge(v, w, weight));
    }


    public int[] shortestPath(int v){
        int[] distances = new int[vertices];
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[v] = 0;

        int[] checklist = new int[vertices];
        Arrays.fill(checklist, Integer.MAX_VALUE);
        checklist[v]=0;
        

        for (int i=0; i < vertices-1; i++){
            for (int j = 0; j < vertices; j++){
                for (Edge e: adjList.get(j)){
                    if (distances[j] != Integer.MAX_VALUE && distances[j] + e.weight < distances[e.destination]){
                        distances[e.destination] = distances[j] + e.weight;
                    }
                }
            }
        }

        for (int j = 0; j < vertices; j++){
            for (Edge e: adjList.get(j)){
                if (distances[j] != Integer.MAX_VALUE && distances[j] + e.weight < distances[e.destination]){
                    return null;
                }
            }
        }
        return distances;
    }

    public boolean hasNegativeCycle(){
        int[] distances = new int[vertices];
        for (int i = 0; i< vertices-1; i ++){
            Arrays.fill(distances, Integer.MAX_VALUE);
            distances[i] = 0;

            for (int j=0; j < vertices-1; j++){
                for (int k = 0; k < vertices; k++){
                    for (Edge e: adjList.get(j)){
                        if (distances[k] != Integer.MAX_VALUE && distances[k] + e.weight < distances[e.destination]){
                            distances[e.destination] = distances[k] + e.weight;
                        }
                    }
                }
            }

        }

        for (int j = 0; j < vertices; j++){
            for (Edge e: adjList.get(j)){
                if (distances[j] != Integer.MAX_VALUE && distances[j] + e.weight < distances[e.destination]){
                    return true;
                }
            }
        }


        return false;
    }

    public int[] minimumSpanningTreePrim(){
        boolean[] visited = new boolean[vertices];
        int[] distances = new int[vertices];
        Edge[] parent = new Edge[vertices];     // Store edges forming the MST
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));

        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[0] = 0;
        pq.add(new int[]{0,0});

        while (!pq.isEmpty()){
            int[] current = pq.poll();
            int u = current[0];

            if (!visited[u]){
                visited[u] = true;
                //traverse all connected edges
                for (Edge edge: adjList.get(u)){
                    int v = edge.destination;
                    if (!visited[v] && edge.weight < distances[v]){
                        distances[v] = edge.weight;
                        parent[v] = edge;
                        pq.add(new int[]{v, distances[v]});
                    }
                }
            }


        }
        int[] mstEdgeWeights = new int[vertices-1];
        int index = 0;
        for (Edge edge : parent) {
            if (edge != null) {
                mstEdgeWeights[index++] = edge.weight;
            }
        }

        return mstEdgeWeights;
    }

    public int[] minimumSpanningTree() {
        // Step 1: Sort all edges by weight
        edges.sort(Edge::compareTo);
    
        // Step 2: Initialize components for each vertex
        int[] component = new int[vertices];
        for (int i = 0; i < vertices; i++) {
            component[i] = i; // Each vertex is its own component initially
        }
    
        int mstWeight = 0;               // Total weight of the MST
        List<Edge> mstEdges = new ArrayList<>(); // Store edges in the MST
    
        // Step 3: Process edges in increasing weight order
        for (Edge edge : edges) {
            int u = edge.source;
            int v = edge.destination;
    
            // Check if u and v belong to different components
            if (component[u] != component[v]) {
                // Add edge to MST
                mstEdges.add(edge);
                mstWeight += edge.weight;
    
                // Merge components: update all vertices in component[v] to component[u]
                int oldComponent = component[v];
                int newComponent = component[u];
                for (int i = 0; i < vertices; i++) {
                    if (component[i] == oldComponent) {
                        component[i] = newComponent;
                    }
                }
    
                // Stop if we have enough edges
                if (mstEdges.size() == vertices - 1) {
                    break;
                }
            }
        }
    
        // Step 4: Check if the graph is connected
        if (mstEdges.size() != vertices - 1) {
            System.out.println("Graph is disconnected; no MST exists.");
            return null;
        }

        int[] result = new int[vertices-1];
        int index = 0;
        for (Edge e: mstEdges){
            result[index++] = e.destination;
        }
    
        // Step 5: Print the MST
        // for (Edge e : mstEdges) {
        //     System.out.println("Edge: " + e.source + " - " + e.destination + ", Weight: " + e.weight);
        // }
        // System.out.println("Total MST Weight: " + mstWeight);
    
        // Return MST weight as an array
        return result;
    }
    



    





    public static void main(String[] args) {
        AdjacencyListGraph graph = new AdjacencyListGraph(5);
        graph.addWeightedEdge(0, 1, 2);
        graph.addWeightedEdge(0, 3, 6);
        graph.addWeightedEdge(1, 2, 3);
        graph.addWeightedEdge(1, 3, 8);
        graph.addWeightedEdge(1, 4, 5);
        graph.addWeightedEdge(2, 4, 7);
        graph.addWeightedEdge(3, 4, 9);
    
        int[] mst = graph.minimumSpanningTreePrim();
        System.out.println("Edge weights in the Minimum Spanning Tree: " + Arrays.toString(mst));
    }
    

}

