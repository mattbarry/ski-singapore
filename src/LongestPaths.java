import java.util.*;
import org.jgrapht.*;
import org.jgrapht.graph.*;
import org.jgrapht.traverse.*;

public class LongestPaths<V, E> {

	private DirectedGraph<V, E> graph = null;
	private Map<V, Integer> longestPathToVertex = null;
	
	public LongestPaths(DirectedGraph<V, E> graph) {
		this.graph = graph;
	}
	
	private void findLongestPaths(List<GraphPath<V, E>> pathList, V endVertex, V currentVertex, Stack<E> edgeStack) {
		
		// Inspect the neighboring vertices to determine which ones have the longest path
		int longestNeighboringPath = -1;
		for (E edge : this.graph.incomingEdgesOf(currentVertex)) {
			V source = this.graph.getEdgeSource(edge);
			if (this.longestPathToVertex.get(source) > longestNeighboringPath) {
				longestNeighboringPath = this.longestPathToVertex.get(source);
			}
		}
		
		List<E> nextEdgeList = new ArrayList<E>();
		for (E edge : this.graph.incomingEdgesOf(currentVertex)) {
			V source = this.graph.getEdgeSource(edge);
			if (this.longestPathToVertex.get(source) == longestNeighboringPath) {
				nextEdgeList.add(edge);
			}
		}
		
		if (!nextEdgeList.isEmpty()) {
			// Visit each edge that came from a vertex with the longest path
			for (E nextEdge : nextEdgeList) { 
				edgeStack.push(nextEdge);
				V nextVertex = this.graph.getEdgeSource(nextEdge);
				findLongestPaths(pathList, endVertex, nextVertex, edgeStack);
				edgeStack.pop();
			}
		} else {
			// Create a new graph path when we reach a node with no incoming edges
			V startVertex = currentVertex;
			List<E> edgeList = new ArrayList<E>(edgeStack);
			Collections.reverse(edgeList);
			double weight = 0.0;
			for (E edge : edgeList) {
				weight += this.graph.getEdgeWeight(edge);
			}
			pathList.add(new GraphPathImpl<V, E>(this.graph, startVertex, endVertex, edgeList, weight));
		}
	}
	
	private List<V> findEndVertices() {
		// Find the length of the longest path in the graph
		int longestPathLength = 0;
		for (V v : this.longestPathToVertex.keySet()) {
			if (this.longestPathToVertex.get(v) > longestPathLength) {
				longestPathLength = this.longestPathToVertex.get(v);
			}
		}
		// Find the vertices with the longest path length
		List<V> endVertices = new ArrayList<V>();
		for (V v : this.longestPathToVertex.keySet()) {
			if (this.longestPathToVertex.get(v) == longestPathLength) {
				endVertices.add(v);
			}
		}
		return endVertices;
	}
	
	public List<GraphPath<V, E>> getLongestPaths() {	
		
		// Determine the length of the longest path leading to each vertex in the graph			
		this.longestPathToVertex = new HashMap<V, Integer>();
		GraphIterator<V, E> iterator = new TopologicalOrderIterator<V, E>(this.graph);
		while (iterator.hasNext()) {
			V target = iterator.next();
			Set<E> incomingEdges = this.graph.incomingEdgesOf(target);
			if (!incomingEdges.isEmpty()) {
				int longestNeighboringPath = 0;
				for (E edge : incomingEdges) {
					V source = this.graph.getEdgeSource(edge);
					if (this.longestPathToVertex.get(source) > longestNeighboringPath) {
						longestNeighboringPath = this.longestPathToVertex.get(source);
					}
				}
				this.longestPathToVertex.put(target, longestNeighboringPath + 1);
			} else {
				this.longestPathToVertex.put(target, 0);
			}
		}
						
		// Find the end vertices of the longest paths in the graph
		List<V> endVertices = findEndVertices();
		
		// Recurse back up the graph to find the starting vertices of each path
		List<GraphPath<V, E>> paths = new ArrayList<GraphPath<V, E>>();
		for (V v : endVertices) {
			Stack<E> edgeStack = new Stack<E>();
			findLongestPaths(paths, v, v, edgeStack);
		}
		
		return paths;
	}
}
