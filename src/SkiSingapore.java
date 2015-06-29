import java.net.*;
import java.util.*;
import org.jgrapht.*;
import org.jgrapht.graph.*;

public class SkiSingapore { 
	public static void main(String[] args) {
		try {
			// Load the map from Amazon S3
			System.out.println("Loading map...");
			URL url = new URL("http://s3-ap-southeast-1.amazonaws.com/geeks.redmart.com/coding-problems/map.txt");
			MapLoader loader = new MapLoader();
			DirectedGraph<MapVertex, DefaultEdge> graph = loader.loadMap(url);
			
			// Find the longest paths
			System.out.println("Finding longest paths...");
			LongestPaths<MapVertex, DefaultEdge> longestPaths = new LongestPaths<MapVertex, DefaultEdge>(graph);
			List<GraphPath<MapVertex, DefaultEdge>> pathList = longestPaths.getLongestPaths();
			
			// Sort the paths to find the steepest drop and print it to System.out
			Collections.sort(pathList, Collections.reverseOrder(new GraphPathComparator()));
			GraphPath<MapVertex, DefaultEdge> path = pathList.get(0);
			System.out.println("Length: " + (path.getEdgeList().size() + 1));
			System.out.println("Drop: " + (path.getStartVertex().getElevation() - path.getEndVertex().getElevation()));
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}
	}

}
