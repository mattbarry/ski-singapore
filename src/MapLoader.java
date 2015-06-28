import org.jgrapht.*;
import org.jgrapht.graph.*;
import java.io.*;
import java.net.*;

public class MapLoader {
	
	public DirectedGraph<MapVertex, DefaultEdge> loadMap(InputStream in) throws IOException {
		
		DirectedGraph<MapVertex, DefaultEdge> graph = 
				new DefaultDirectedGraph<MapVertex, DefaultEdge>(DefaultEdge.class);
		BufferedReader reader = null;
		
		try {
			reader = new BufferedReader(new InputStreamReader(in));
			
			// Read the map dimensions
			String inputLine = reader.readLine();
			String[] dimensions = inputLine.split("\\s+");
			if (dimensions.length != 2) {
				throw new IOException("Input stream does not contain map dimensions.");
			}
			int columns = Integer.parseInt(dimensions[0]);
			int rows = Integer.parseInt(dimensions[1]);
		
			// Read the map vertices
			MapVertex[][] v = new MapVertex[rows][columns];
			for (int row = 0; row < rows; row++) {
				inputLine = reader.readLine();
				String[] rowData = inputLine.split("\\s+");
				for (int col = 0; col < columns; col++) {
					v[row][col] = new MapVertex(Integer.parseInt(rowData[col]));
					graph.addVertex(v[row][col]);
				}
			}
		
			// Generate the graph edges
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
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
		
		return graph;
	}
	
	public DirectedGraph<MapVertex, DefaultEdge> loadMap(URL url) 
			throws IOException {
		URLConnection conn = url.openConnection();
		return loadMap(conn.getInputStream());
	}
}
