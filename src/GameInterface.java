package cs10;

import java.util.LinkedList;
import java.util.Scanner;
import java.util.Set;

/**
 * Class to handle the Game Interface for the Kevin Bacon Game. Reads user input
 * through a Scanner and reacts accordingly based on a list of commands.
 * 
 */

public class GameInterface {
	private boolean playing = true;
	private KevinBaconGame kbg;
	String currentCenter = "Kevin Bacon";

	public GameInterface(KevinBaconGame kbg) {
		this.kbg = kbg;
		kbg.makeCenter("Kevin Bacon");
	}

	/**
	 * Main loop to run the game
	 */
	public void runGame(Scanner scan){
		
		System.out.println("Commands:");
		System.out.println("c <#>: list top <#> centers of the universe, sorted by average separation");
		System.out.println("p <name>: find path from <name> to current center of the universe");
		System.out.println("d <#>: list top <#> centers of the universe, sorted by degree");
		System.out.println("u <name>: make <name> the center of the universe");
		System.out.println("q: quit game");
		
		System.out.println("\n"+currentCenter + " is now the center of the acting universe, connected to "+ (kbg.getBfsTree().numVertices()-1) 
						+ "/" +kbg.getGraph().numVertices()+ " actors with average separation "+BFSlib.averageSeparation(kbg.getBfsTree(), currentCenter));
		while(playing) {
			if (scan.hasNextLine()) {
				String input = scan.nextLine();
				if (input.length() != 0)handleInput(input);
			}
		}
	}

	/**
	 * Handles the input to perform the required actions based on the commands given.
	 */
	private void handleInput(String input) {
		char c = input.charAt(0);
		switch (c) {
			case 'c': listTopS(input.substring(2));
					  break;
			case 'd': listTopD(input.substring(2));
					  break;
			case 'p': findPath(input.substring(2));
					  break;
			case 'u': changeUniverse(input.substring(2));
				      break;
			case 'q': quitGame();
					  break;
			default: System.out.println("Wrong Input");
		}
	}

	// Lists top actors by in-degree. Considers all actors in the graph.
	private void listTopD(String num) {
		try {
			int n = Integer.parseInt(num);
			for (int i = 0; i < Math.min(n, kbg.getGraph().numVertices()); i++) {
				String actor = kbg.getActorsByDegree().get(i);
				System.out.println(actor + " has an average separation of "+kbg.getAvgPathsMap().get(actor) 
						+ " and "+kbg.getGraph().inDegree(actor)+ " costars");
			}
			} catch (NumberFormatException e) {
				System.out.println("Wrong Input");
			}
		
	}

	// Lists top actors by average separation. Considers only actors in the Bacon Universe.
	private void listTopS(String num) {
		try {
			int n = Integer.parseInt(num);
			for (int i = 0; i < Math.min(n, kbg.getBfsTree().numVertices()); i++) {
				String actor = kbg.getActorsByPath().get(i);
				System.out.println(actor + " has an average separation of "+kbg.getAvgPathsMap().get(actor) 
						+ " and "+kbg.getGraph().inDegree(actor)+ " costars");
			}
			} catch (NumberFormatException e) {
				System.out.println("Wrong Input");
			}
		
	}

	// finds path to current center
	private void findPath(String name) {
		Graph<String, Set<String>> tree = kbg.getBfsTree();
		if (!tree.hasVertex(name)) {
			System.out.println("No path could be found");
		} else {
		LinkedList<String> path = (LinkedList<String>)BFSlib.getPath(tree, name);
		int n = path.size() - 1;
		for (int i = 0; i < n; i ++) {
			System.out.println(path.peek() + " appeared in " + tree.getLabel(path.remove(), path.peek())+" with "+path.peek());
		}
		}
	}

	// changes universe to given name
	private void changeUniverse(String name) {
		if (kbg.getGraph().hasVertex(name)) {
			kbg.makeCenter(name);
			currentCenter = name;
			System.out.println(currentCenter + " is now the center of the acting universe, connected to "+ (kbg.getBfsTree().numVertices()-1) 
					+ "/" + kbg.getGraph().numVertices() +" actors with average separation "+BFSlib.averageSeparation(kbg.getBfsTree(), currentCenter));
		} else {
			System.out.println("The actor is not in our database");
		}
		
	}

	// quits the game
	private void quitGame() {
		playing = false;
		System.out.println("Game has been quit");
		
	}

}
