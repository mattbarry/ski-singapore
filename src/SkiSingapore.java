import java.net.*;
import org.jgrapht.*;
import org.jgrapht.graph.*;

public class SkiSingapore { 
	
	public static void dumpGraphPath(GraphPath<Vertex, DefaultEdge> path) {
		System.out.println("Start: " + path.getStartVertex().getElevation());
		System.out.println("End: " + path.getEndVertex().getElevation());
		System.out.println("Drop: " + (path.getStartVertex().getElevation() - path.getEndVertex().getElevation()));
		for (DefaultEdge edge : path.getEdgeList()) {
			System.out.println(path.getGraph().getEdgeSource(edge).getElevation() 
					+ " -> " + path.getGraph().getEdgeTarget(edge).getElevation());
		}
	}
	
	public static void main(String[] args) {
		
		try {
			// Load the map from Amazon S3
			System.out.println("Loading map...");
			MapLoader loader = new MapLoader();
			URL url = new URL("http://s3-ap-southeast-1.amazonaws.com/geeks.redmart.com/coding-problems/map.txt");
			DirectedGraph<Vertex, DefaultEdge> graph = loader.loadMap(url);
			
			// Find the longest paths and dump them to System.out
			System.out.println("Finding longest paths...");
			LongestPaths<Vertex, DefaultEdge> longestPaths = new LongestPaths<Vertex, DefaultEdge>(graph);
			for (GraphPath<Vertex, DefaultEdge> path : longestPaths.getLongestPaths()) {
				dumpGraphPath(path);
			}
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}
		
	}

}
