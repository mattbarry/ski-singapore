import java.util.Comparator;
import org.jgrapht.GraphPath;
import org.jgrapht.graph.DefaultEdge;

public class GraphPathComparator implements Comparator<GraphPath<MapVertex, DefaultEdge>> {
	
	protected int getDrop(GraphPath<MapVertex, DefaultEdge> path) {
		return path.getStartVertex().getElevation() - path.getEndVertex().getElevation();
	}
	
	protected int getLength(GraphPath<MapVertex, DefaultEdge> path) {
		return path.getEdgeList().size() + 1;
	}
	
	public int compare(GraphPath<MapVertex, DefaultEdge> a, GraphPath<MapVertex, DefaultEdge> b) {
		int lengthCompare = Integer.compare(getLength(a), getLength(b));
		if (lengthCompare != 0) {
			return lengthCompare;
		}
		return Integer.compare(getDrop(a), getDrop(b));
	}
}
