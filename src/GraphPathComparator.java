import java.util.Comparator;
import org.jgrapht.graph.*;
import org.jgrapht.*;

public class GraphPathComparator implements Comparator<GraphPath<MapVertex, DefaultEdge>> {
	
	protected Integer getDrop(GraphPath<MapVertex, DefaultEdge> path) {
		return path.getStartVertex().getElevation() - path.getEndVertex().getElevation();
	}
	
	public int compare(GraphPath<MapVertex, DefaultEdge> a, GraphPath<MapVertex, DefaultEdge> b) {
		return getDrop(b).compareTo(getDrop(a));
	}
}
