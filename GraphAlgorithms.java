import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 * Your implementation of various different graph algorithms.
 *
 * @author Samay Chandna
 * @version 1.0
 * @userid schandna3
 * @GTID 903380585
 * <p>
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 * <p>
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * the parameterized starting vertex.
     * <p>
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     * <p>
     * You may import/use java.util.Set, java.util.List, java.util.Queue, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     * <p>
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     * <p>
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {
        if (graph == null || start == null) {
            throw new IllegalArgumentException("Inputs cannot be null");
        } else if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("Starting vertex is not in graph");
        }

        List<Vertex<T>> visited = new ArrayList<>();
        Queue<Vertex<T>> queue = new LinkedList<>();
        Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();

        visited.add(start);
        queue.add(start);
        while (!queue.isEmpty()) {
            Vertex<T> v = queue.remove();
            for (VertexDistance<T> vd : adjList.get(v)) {
                Vertex<T> unchecked = vd.getVertex();
                if (!visited.contains(unchecked)) {
                    visited.add(unchecked);
                    queue.add(unchecked);
                }
            }
        }

        return visited;
    }

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     * <p>
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     * <p>
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * all points for this method.
     * <p>
     * You may import/use java.util.Set, java.util.List, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     * <p>
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     * <p>
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        if (graph == null || start == null) {
            throw new IllegalArgumentException("Inputs cannot be null");
        } else if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("Starting vertex is not in graph");
        }

        List<Vertex<T>> visited = new ArrayList<>();
        recDFS(start, graph, visited);
        return visited;
    }

    /**
     * Recursive helper method for DFS
     *
     * @param current current node being checked
     * @param graph   graph whose vertices are being checked
     * @param visited list of visited vertices
     * @param <T>     generic type
     */
    private static <T> void recDFS(Vertex<T> current, Graph<T> graph, List<Vertex<T>> visited) {
        visited.add(current);
        for (VertexDistance<T> distance : graph.getAdjList().get(current)) {
            if (!visited.contains(distance.getVertex())) {
                recDFS(distance.getVertex(), graph, visited);
            }
        }
    }

    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     * <p>
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     * <p>
     * You may import/use java.util.PriorityQueue,
     * java.util.Map, and java.util.Set and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     * <p>
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     * <p>
     * 1) Check if all of the vertices have been visited.
     * 2) Check if the PQ is empty yet.
     * <p>
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every
     * other node in the graph
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                        Graph<T> graph) {
        if (graph == null || start == null) {
            throw new IllegalArgumentException("Inputs cannot be null");
        } else if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("Starting vertex is not in graph");
        }
        List<Vertex<T>> visited = new ArrayList<>();
        Map<Vertex<T>, Integer> shortest = new HashMap<>();
        Queue<VertexDistance<T>> heap = new PriorityQueue<>();
        Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();
        for (Vertex<T> vertex : graph.getVertices()) {
            shortest.put(vertex, Integer.MAX_VALUE);
        }
        heap.add(new VertexDistance<>(start, 0));
        while (!heap.isEmpty() && visited.size() != adjList.size()) {
            VertexDistance<T> dequeue = heap.remove();
            if (!visited.contains(dequeue.getVertex())) {
                visited.add(dequeue.getVertex());
                shortest.put(dequeue.getVertex(), dequeue.getDistance());
                for (VertexDistance<T> distance : adjList.get(dequeue.getVertex())) {
                    if (!visited.contains(distance.getVertex())) {
                        heap.add(new VertexDistance<>(distance.getVertex(), dequeue.getDistance() +
                            distance.getDistance()));
                    }
                }
            }
        }
        return shortest;
    }

    /**
     * Runs Kruskal's algorithm on the given graph and returns the Minimal
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     * <p>
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     * <p>
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     * <p>
     * You may assume that there will only be one valid MST that can be formed.
     * <p>
     * Kruskal's will also require you to use a Disjoint Set which has been
     * provided for you. A Disjoint Set will keep track of which vertices are
     * connected given the edges in your current MST, allowing you to easily
     * figure out whether adding an edge will create a cycle. Refer
     * to the DisjointSet and DisjointSetNode classes that
     * have been provided to you for more information.
     * <p>
     * You should NOT allow self-loops or parallel edges into the MST.
     * <p>
     * By using the Disjoint Set provided, you can avoid adding self-loops and
     * parallel edges into the MST.
     * <p>
     * You may import/use java.util.PriorityQueue,
     * java.util.Set, and any class that implements the aforementioned
     * interfaces.
     * <p>
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param graph the graph we are applying Kruskals to
     * @return the MST of the graph or null if there is no valid MST
     * @throws IllegalArgumentException if any input is null
     */
    public static <T> Set<Edge<T>> kruskals(Graph<T> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("Graph cannot be null");
        }
        DisjointSet<Vertex<T>> disjoint = new DisjointSet<>();
        for (Vertex<T> vertex : graph.getVertices()) {
            disjoint.find(vertex);
        }
        Set<Edge<T>> mst = new HashSet<>();
        Queue<Edge<T>> heap = new PriorityQueue<>(graph.getEdges());
        while (!heap.isEmpty() && mst.size() < 2 * (graph.getVertices().size() - 1)) {
            Edge<T> edge = heap.remove();
            Vertex<T> u = edge.getU();
            Vertex<T> v = edge.getV();
            if (!disjoint.find(u).equals(disjoint.find(v))) {
                mst.add(edge);
                mst.add(new Edge<>(v, u, edge.getWeight()));
                disjoint.union(u, v);
            }
        }
        if (mst.size() < 2 * (graph.getVertices().size() - 1)) {
            return null;
        }
        return mst;
    }
}