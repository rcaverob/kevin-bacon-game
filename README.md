# Kevin Bacon Game
This program tackles the important social network problem of finding an actor's "Bacon number". Starting with an actor, see if they have been in a movie with someone who has been in a movie with someone who has been in a movie... who has been in a movie with Kevin Bacon. 
## What I Did
- Wrote the `BFSlib` library class which provides some utility methods for working with Graphs (e.g., BFS traversal from a given vertex, averageSeparation between vertices, etc...)

- Implemented the `KevinBaconGame` class which handles most of the logic of the program, creating the Graph representations based on data read from the `actors.txt`, `movieactors.txt`, and `movies.txt` files. It also includes methods to change the actor at the center of the graph as well as order the actors based on proximity to the central actor. 


- Created the `GameInterface` class which provides a simple user interface to play the game using textual commands.


- Wrote the `BaconDriver` class which is in charge of running the other classes.
