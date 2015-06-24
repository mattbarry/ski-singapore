import org.jgrapht.*;
import org.jgrapht.graph.*;
import org.jgrapht.traverse.*;
import java.util.*;

public class LongestPaths<V, E> {

	private DirectedGraph<V, E> graph = null;
	private Map<V, Integer> longestPaths = null;
	
	public LongestPaths(DirectedGraph<V, E> graph) {
		this.graph = graph;
	}
	
	private void findLongestPaths(List<GraphPath<V, E>> pathList, V endVertex, V currentVertex, Stack<E> edgeStack) {
		
		// Inspect the surrounding nodes to determine which ones have the longest paths
		int nextLongest = -1;
		for (E edge : this.graph.incomingEdgesOf(currentVertex)) {
			V source = this.graph.getEdgeSource(edge);
			if (this.longestPaths.get(source) > nextLongest) {
				nextLongest = this.longestPaths.get(source);
			}
		}
		
		List<E> nextEdgeList = new ArrayList<E>();
		for (E edge : this.graph.incomingEdgesOf(currentVertex)) {
			V source = this.graph.getEdgeSource(edge);
			if (this.longestPaths.get(source) == nextLongest) {
				nextEdgeList.add(edge);
			}
		}
		
		if (nextEdgeList.isEmpty()) {
			// Reached a root node in the graph
			V startVertex = currentVertex;
			List<E> edgeList = new ArrayList<E>(edgeStack);
			Collections.reverse(edgeList);
			double weight = 0.0;
			pathList.add(new GraphPathImpl<V, E>(this.graph, startVertex, endVertex, edgeList, weight));
		} else {
			for (E nextEdge : nextEdgeList) { 
				edgeStack.push(nextEdge);
				V nextVertex = this.graph.getEdgeSource(nextEdge);
				findLongestPaths(pathList, endVertex, nextVertex, edgeStack);
				edgeStack.pop();
			}
		}
	}
	
	public List<GraphPath<V, E>> getLongestPaths() {	
		
		this.longestPaths = new HashMap<V, Integer>();
		
		// Create a topological iterator			
		final GraphIterator<V, E> iterator = new TopologicalOrderIterator<V, E>(this.graph);
		while (iterator.hasNext()) {
			V target = iterator.next();
			int max = 0;
			for (E edge : this.graph.incomingEdgesOf(target)) {
				V source = this.graph.getEdgeSource(edge);
				if (this.longestPaths.get(source) + 1 > max) {
					max = this.longestPaths.get(source) + 1;
				}
			}
			this.longestPaths.put(target, max);
		}
						
		// Find the length of the longest paths
		int longestPathLength = 0;
		for (V v : this.longestPaths.keySet()) {
			if (this.longestPaths.get(v) > longestPathLength) {
				longestPathLength = this.longestPaths.get(v);
			}
		}
		
		// Get the end vertices of each longest path
		List<V> endVertices = new ArrayList<V>();
		for (V v : this.longestPaths.keySet()) {
			if (this.longestPaths.get(v) == longestPathLength) {
				endVertices.add(v);
			}
		}
		
		List<GraphPath<V, E>> paths = new ArrayList<GraphPath<V, E>>();
		for (V v : endVertices) {
			Stack<E> edgeStack = new Stack<E>();
			findLongestPaths(paths, v, v, edgeStack);
		}
		
		return paths;
	}
}
