import org.jgrapht.*;
import org.jgrapht.graph.*;
import java.io.*;
import java.net.*;

public class MapLoader {

	public MapLoader() {
	}
	
	public DirectedGraph<Vertex, DefaultEdge> loadMap(InputStream in) throws IOException {
		DirectedGraph<Vertex, DefaultEdge> graph = 
				new DefaultDirectedGraph<Vertex, DefaultEdge>(DefaultEdge.class);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String inputLine = br.readLine();
		if (inputLine != null) {
			String[] dimensions = inputLine.split(" ");
			if (dimensions.length == 2) {
				// Parse map dimensions
				int columns = Integer.parseInt(dimensions[0]);
				int rows = Integer.parseInt(dimensions[1]);
				// Load the vertices
				Vertex[][] v = new Vertex[rows][columns];
				for (int row = 0; row < rows; row++) {
					inputLine = br.readLine();
					String[] rowData = inputLine.split(" ");
					for (int col = 0; col < columns; col++) {
						v[row][col] = new Vertex(Integer.parseInt(rowData[col]));
						graph.addVertex(v[row][col]);
					}
				}
				// Generate the edges
				for (int row = 0; row < rows; row++) {
					for (int col = 0; col < columns; col++) {
						if (row > 0 && v[row-1][col].getElevation() < v[row][col].getElevation()) {
							graph.addEdge(v[row][col], v[row-1][col]);
						}
						if (row + 1 < rows && v[row+1][col].getElevation() < v[row][col].getElevation()) {
							graph.addEdge(v[row][col], v[row+1][col]);
						}
						if (col > 0 && v[row][col-1].getElevation() < v[row][col].getElevation()) {
							graph.addEdge(v[row][col], v[row][col-1]);
						}
						if (col + 1 < columns && v[row][col+1].getElevation() < v[row][col].getElevation()) {
							graph.addEdge(v[row][col], v[row][col+1]);
						}
					}
				}
			}
		}
		br.close();
		return graph;
	}
	
	public DirectedGraph<Vertex, DefaultEdge> loadMap(URL url) 
			throws IOException {
		URLConnection conn = url.openConnection();
		return loadMap(conn.getInputStream());
	}
}
