# **IN PROGRESS**: Zorkesque

## Running the game
* Get JDK and run `./run.sh` to create and open a .jar file with the game
* Follow the instructions explained in the game and enter `quit` at any point to close it

## Controls
* To move around in the game, use
  * N: North
  * S: South
  * W: West
  * E: East
* To interact with creatures in the game, use:
  * caress <creature>: Caress a creature
  * kill <creature>: Kill the creature if you have a weapon in your inventory
* Inventoriable items like weapons can be carried, dropped and examined:
  * take <item>: Put an item in your inventory
  * drop <item>: Remove an item from your inventory
  * examine <item>: Examine an item in your inventory or at your current location
* Some items cannot be carried in your inventory but can still be examined:
  * examine <item>: Examine an uninventoriable item at your current location
* Top-level game controls are:
  * look: Prints information about your current location
  * inventory: Prints a list of items currently in your inventory
  * save: Saves the current game progress (**not yet implemented**)
  * help: Prints the list of game controls
  * quit: Exits the game
  
## Learning Log
* Read the Oracle [Java tutorials](https://docs.oracle.com/javase/tutorial/java/index.html) to learn Java
* Implemented a simple recursive-descent parser - the only way to interact with the game
* First time thinking about object-oriented design and how my classes and code should be organized
* First time designing a game
* Wrote my first bash shell script
* Learned how to package a .jar file
