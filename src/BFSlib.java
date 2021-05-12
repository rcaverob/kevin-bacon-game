package cs10;
import java.util.*;

/**
 * Library for graph analysis, implements breadth-first search
 * 
 */
public class BFSlib {
	/**
	 * Orders vertices in decreasing order by their in-degree
	 * @param g		graph
	 * @return		list of vertices sorted by in-degree, decreasing (i.e., largest at index 0)
	 */
	public static <V,E> List<V> verticesByInDegree(Graph<V,E> g) {
		ArrayList<V> result = new ArrayList<V>();
		for (V vert : g.vertices()) {
			result.add(vert);
		}
		Collections.sort(result, new Comparator<V>() {

			@Override
			public int compare(V v1, V v2) {
				return g.inDegree(v2) - g.inDegree(v1);
			}
		});
		return result;
	}
	
	/**
	 * BFS to find shortest path tree for a current center of the universe. Returns a path tree as a Graph
	 */
	public static <V,E> Graph<V,E> bfs(Graph<V,E> g, V source){
		
		Graph<V,E> result = new AdjacencyMapGraph<V,E>();
		
		Queue<V> q = new LinkedList<V>();
		Set<V> visited = new HashSet<V>();	
		q.add(source);
		visited.add(source);
		while (!q.isEmpty()) {
			V cur = q.remove();
			result.insertVertex(cur);
			for (V next : g.outNeighbors(cur)) {
				if (!visited.contains(next)) {
					visited.add(next);
					result.insertVertex(next);
					result.insertDirected(next, cur, g.getLabel(next, cur));
					q.add(next);
				}
			}
		}
		return result;
	}
	
	/**
	 * Given a shortest path tree and a vertex, constructs a path from the vertex back to the center of the universe.
	 */
	public static <V,E> List<V> getPath(Graph<V,E> tree, V v){
		LinkedList<V> result = new LinkedList<V>();
		result.add(v);
		while (tree.outDegree(v) > 0) {
			for (V prev : tree.outNeighbors(v)) {
			result.add(prev);
			v = prev;
			}	
		}
		return result;
		
	}
	
	/**
	 * Given a graph and a subgraph (here shortest path tree), determine which vertices are in the graph but not the subgraph (here, not reached by BFS).
	 */
	public static <V,E> Set<V> missingVertices(Graph<V,E> graph, Graph<V,E> subgraph){
		Set<V> result = new HashSet<V>();
		for (V vert : graph.vertices()){
			if (!((Set<V>) subgraph.vertices()).contains(vert)) result.add(vert);
		}
		return result;
	}
	
	/**
	 * Returns the average distance-from-root in a shortest path tree
	 */
	public static <V,E> double averageSeparation(Graph<V,E> tree, V root) {
		double total = 0;
		for(V child : tree.inNeighbors(root)) {
			total += count(tree, child, 1);
		}
		return total / (((Set<V>) tree.vertices()).size() -1);
	}

	/**
	 * Helper method for averageSeparation, recursively adds the separation of each node.
	 */
	private static <V,E> int count(Graph<V,E> tree, V vrt, int sum) {
		int total = sum;
		if (tree.inDegree(vrt) == 0) return sum;
		for (V next : tree.inNeighbors(vrt)) {
			 total += count(tree, next, sum + 1);
		}
		return total;
	}

	}
