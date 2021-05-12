package cs10;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


/**
 * Driver for the Kevin Bacon Game. Handles file reading and the setup necessary to play the game.
 * 
 */
public class BaconDriver {

	public static void main(String[] args) throws FileNotFoundException {
		KevinBaconGame kbg = new KevinBaconGame();
		Scanner scan = new Scanner(new File("files/moviesTest.txt"), "UTF-8");
		kbg.buildMovieMap(scan);
		scan.close();
		Scanner scan2 = new Scanner(new File("files/actorsTest.txt"), "UTF-8");
		kbg.buildActorMap(scan2);
		scan2.close();
		Scanner scan3 = new Scanner(new File("files/movie-actorsTest.txt"));
		kbg.buildMovieActorsMap(scan3);
		scan3.close();
		
		kbg.buildGraph();
		kbg.makeCenter("Kevin Bacon");
		kbg.orderActors();
		
		GameInterface gameUI = new GameInterface(kbg);
		Scanner sc = new Scanner(System.in);
		gameUI.runGame(sc);
		sc.close();

	}

}
