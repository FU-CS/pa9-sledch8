package pa9;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;


public class AdjacencyListGraphTest {
    @Test
    void shortestPathTest1() {
        AdjacencyListGraph graph = new AdjacencyListGraph(5);
        graph.addWeightedEdge(0, 1, 2);
        graph.addWeightedEdge(1, 2, 3);
        graph.addWeightedEdge(0, 3, 6);
        graph.addWeightedEdge(3, 4, 9);
        graph.addWeightedEdge(2, 4, 7);
        
        // Expected shortest path from vertex 0 to all vertices
        int[] expected = {0, 2, 5, 6, 12};
        assertArrayEquals(expected, graph.shortestPath(0));
    }

    @Test
    void shortestPathTest2() {
        AdjacencyListGraph graph = new AdjacencyListGraph(4);
        graph.addWeightedEdge(0, 1, 1);
        graph.addWeightedEdge(1, 2, -1);
        graph.addWeightedEdge(2, 3, -1);
        graph.addWeightedEdge(3, 0, -1);
        
        // With negative cycle, the distances should indicate an issue
        assertArrayEquals(null, graph.shortestPath(0));
    }

    @Test
    void hasNegativeCycleTest1() {
        AdjacencyListGraph graph = new AdjacencyListGraph(4);
        graph.addWeightedEdge(0, 1, 1);
        graph.addWeightedEdge(1, 2, -1);
        graph.addWeightedEdge(2, 3, -1);
        graph.addWeightedEdge(3, 0, -1);

        assertTrue(graph.hasNegativeCycle());

        AdjacencyListGraph graphWithoutCycle = new AdjacencyListGraph(4);
        graphWithoutCycle.addWeightedEdge(0, 1, 2);
        graphWithoutCycle.addWeightedEdge(1, 2, 3);
        graphWithoutCycle.addWeightedEdge(2, 3, 4);

        assertFalse(graphWithoutCycle.hasNegativeCycle());
    }

    @Test
    void minimumSpanningTreePrimTest1() {
        AdjacencyListGraph graph = new AdjacencyListGraph(5);
        graph.addWeightedEdge(0, 1, 2);
        graph.addWeightedEdge(0, 3, 6);
        graph.addWeightedEdge(1, 2, 3);
        graph.addWeightedEdge(1, 3, 8);
        graph.addWeightedEdge(1, 4, 5);
        graph.addWeightedEdge(2, 4, 7);
        graph.addWeightedEdge(3, 4, 9);

        int[] mstEdgeWeights = graph.minimumSpanningTreePrim();
        int[] expectedWeights = {2, 3, 5, 6};
        Arrays.sort(mstEdgeWeights);
        assertArrayEquals(expectedWeights, mstEdgeWeights);
    }

    @Test
    void minimumSpanningTreeTest1() {
        AdjacencyListGraph graph = new AdjacencyListGraph(5);
        graph.addWeightedEdge(0, 1, 2);
        graph.addWeightedEdge(0, 3, 6);
        graph.addWeightedEdge(1, 2, 3);
        graph.addWeightedEdge(1, 3, 8);
        graph.addWeightedEdge(1, 4, 5);
        graph.addWeightedEdge(2, 4, 7);
        graph.addWeightedEdge(3, 4, 9);

        int[] mstEdges = graph.minimumSpanningTree();
        int[] expectedDestinations = {1, 2, 4, 3}; // Expected edge destinations forming MST
        Arrays.sort(mstEdges);
        Arrays.sort(expectedDestinations);
        assertArrayEquals(expectedDestinations, mstEdges);
    }

    @Test
    void mstTestDisconnected() {
        AdjacencyListGraph graph = new AdjacencyListGraph(6); // Disconnected graph
        graph.addWeightedEdge(0, 1, 1);
        graph.addWeightedEdge(1, 2, 2);
        graph.addWeightedEdge(3, 4, 3);
        graph.addWeightedEdge(4, 5, 4);

        assertNull(graph.minimumSpanningTree());
    }

    
}
