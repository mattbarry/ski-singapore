public class MapVertex {
	private int elevation;
	public MapVertex(int elevation) {
		this.elevation = elevation;
	}
	public int getElevation() {
		return this.elevation;
	}
	@Override public String toString() {
		return Integer.toString(elevation);
	}
};
