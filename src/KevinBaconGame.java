package cs10;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;


/**
 * Class made to play the Kevin Bacon Game. Utilizes a database of actors and movies to do so.
 * 
 */
public class KevinBaconGame {
	
	// Supplementary Maps used to build the main graph
	private Map<Integer, String> actorMap; 
	private Map<Integer, String> movieMap; 
	private Map<String, Set<String>> movieActorsMap; 
	
	// Main Graph, contains the names of actors as vertices, the edge labels are a set of movies in which they performed with the connected actor
	private AdjacencyMapGraph<String, Set<String>> graph;
	
	// bfsTree holds the shortest path from all nodes to the current center of the universe
	private Graph<String, Set<String>> bfsTree;
	
	// Maps and Lists for ordering the actors by degree and separation
	private List<Double> avgPathsList;
	private Map<String, Double> avgPathsMap;
	private List<String> actorsByPath;
	private List<String> actorsByDegree;
	
	public KevinBaconGame() {
		actorMap = new HashMap<Integer, String>();
		movieMap = new HashMap<Integer, String>();
		movieActorsMap = new HashMap<String, Set<String>>();
		graph = new AdjacencyMapGraph<String, Set<String>>();
	}
	
	// builds the actor map from the text file
	public void buildActorMap(Scanner scan) {
		while (scan.hasNextLine()) {
			String data[] = scan.nextLine().split("\\|");
			int id = Integer.parseInt(data[0]);
			String name = data[1];
			actorMap.put(id, name);
		}
	}
	
	// builds the movie map from the text file
	public void buildMovieMap(Scanner scan) {
		while (scan.hasNextLine()) {
			String data[] = scan.nextLine().split("\\|");
			int id = Integer.parseInt(data[0]);
			String name = data[1];
			movieMap.put(id, name);
		}	
	}

	// builds the movie-actors map using the text file movie-actors.
	public void buildMovieActorsMap(Scanner scan) {
		while (scan.hasNextLine()) {
			String data[] = scan.nextLine().split("\\|");
			int movID = Integer.parseInt(data[0]);
			int actID = Integer.parseInt(data[1]);
			if (!movieActorsMap.containsKey(movieMap.get(movID))) {
				HashSet<String> s  = new HashSet<String>();
				s.add(actorMap.get(actID));
				movieActorsMap.put(movieMap.get(movID), s);
			} else {
				movieActorsMap.get(movieMap.get(movID)).add(actorMap.get(actID));

			}
		}		
	}

	public void buildGraph() {
		for (int n : actorMap.keySet()) {
			graph.insertVertex(actorMap.get(n));
		}
		for (String mov : movieActorsMap.keySet()) {
			Set<String> actorSet = movieActorsMap.get(mov);
			for (String a : actorSet) {
				for (String b: actorSet) {
					if (a != b) {
						if (graph.getLabel(a, b) == null) {
							Set<String> movieSet = new HashSet<String>();
							movieSet.add(mov);
							graph.insertUndirected(a, b, movieSet);
						} else {
							graph.getLabel(a, b).add(mov);
						}
					}
				}
			}
			
			}
		}

	public void makeCenter(String name) {
		bfsTree = BFSlib.bfs(graph, name);
	}

	public Graph<String, Set<String>> getBfsTree() {
		return bfsTree;
	}

	public AdjacencyMapGraph<String, Set<String>> getGraph() {
		return graph;
	}
	
	
	// Loops through all the possible centers of the universe, finds their average separation, and stores it for sorting. 
	// Creates and stores a list of actors sorted by inDegree
	// Also stores supplementary Maps to relate these quantities to actor names
	public void orderActors() {
		avgPathsMap = new HashMap<String, Double>();
		avgPathsList = new ArrayList<Double>();
		actorsByPath = new LinkedList<String>();
		actorsByDegree = BFSlib.verticesByInDegree(graph);
		for (String actor: bfsTree.vertices()) {
			makeCenter(actor);
			double avgS = BFSlib.averageSeparation(bfsTree, actor);
			avgPathsMap.put(actor, avgS);
			actorsByPath.add(actor);
			avgPathsList.add(avgS);
		}
		
		Collections.sort(avgPathsList);
		Collections.sort(actorsByPath, new Comparator<String>() {
			@Override
			public int compare(String s1, String s2) {
				return avgPathsMap.get(s1) > avgPathsMap.get(s2)? 1 : -1;
			}
			
			});
	}

	public List<Double> getAvgPathsList() {
		return avgPathsList;
	}

	public Map<String, Double> getAvgPathsMap() {
		return avgPathsMap;
	}

	public List<String> getActorsByPath() {
		return actorsByPath;
	}

	public List<String> getActorsByDegree() {
		return actorsByDegree;
	}
		
}
