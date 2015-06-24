import org.jgrapht.*;
import org.jgrapht.graph.*;

public class SkiSingapore { 
	
	public static void dumpGraphPath(GraphPath<Vertex, DefaultEdge> path) {
		System.out.println(path.getStartVertex().getElevation());
		System.out.println(path.getEndVertex().getElevation());
		for (DefaultEdge edge : path.getEdgeList()) {
			System.out.println(path.getGraph().getEdgeSource(edge).getElevation() 
					+ " -> " + path.getGraph().getEdgeTarget(edge).getElevation());
		}
	}
	
	public static void main(String[] args) {
		
		try {
			final Vertex map[][] = new Vertex[][] {
					{ new Vertex(4), new Vertex(8), new Vertex(7), new Vertex(3) },
					{ new Vertex(2), new Vertex(5), new Vertex(9), new Vertex(3) },
					{ new Vertex(6), new Vertex(3), new Vertex(2), new Vertex(5) },
					{ new Vertex(4), new Vertex(4), new Vertex(1), new Vertex(6) }
			};
			
			final DirectedGraph<Vertex, DefaultEdge> graph = 
					new DefaultDirectedGraph<Vertex, DefaultEdge>(DefaultEdge.class);
			
			// Add vertices to graph
			for (int row = 0; row < map.length; row++) {
				for (int col = 0; col < map[row].length; col++) {
					graph.addVertex(map[row][col]);				
				}
			}
			
			// Add edges to graph
			for (int row = 0; row < map.length; row++) {
				for (int col = 0; col < map[row].length; col++) {
					if (row > 0 && map[row-1][col].getElevation() < map[row][col].getElevation()) {
						graph.addEdge(map[row][col], map[row-1][col]);
					}
					if (row + 1 < map.length && map[row+1][col].getElevation() < map[row][col].getElevation()) {
						graph.addEdge(map[row][col], map[row+1][col]);
					}
					if (col > 0 && map[row][col-1].getElevation() < map[row][col].getElevation()) {
						graph.addEdge(map[row][col], map[row][col-1]);
					}
					if (col + 1 < map[row].length && map[row][col+1].getElevation() < map[row][col].getElevation()) {
						graph.addEdge(map[row][col], map[row][col+1]);
					}
				}
			}
			
			// Find the longest paths and dump them to System.out
			LongestPath<Vertex, DefaultEdge> longestPath = new LongestPath<Vertex, DefaultEdge>(graph);
			for (GraphPath<Vertex, DefaultEdge> path : longestPath.getLongestPaths()) {
				dumpGraphPath(path);
			}

		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}
		
	}

}
