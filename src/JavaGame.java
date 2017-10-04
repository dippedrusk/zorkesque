
import java.io.*;
import java.util.*;
//import javax.xml.parsers.*;

public class JavaGame {

	public static final int GAMESPACE_LENGTH = 10;
	public int[] locations = new int[GAMESPACE_LENGTH * GAMESPACE_LENGTH];
	private int start_XY = 5;
	private int curr_X;
	private int curr_Y;
	private ArrayList<Object> objects = new ArrayList<Object>();

	public static void main(String[] args) {

		JavaGame game = new JavaGame();

		System.out.format("%nWelcome to Zorkesque.%n");
		game.printHelpMessage();

		game.curr_X = game.start_XY;
		game.curr_Y = game.start_XY;
		System.out.format("You are starting out at coordinates (%d, %d).%n", game.curr_X, game.curr_Y);

		game.initializeObjects();

		Tokenizer tokenizer = new Tokenizer();
		Parser parser = new Parser();
		Inventory inventory = new Inventory();
		boolean quit = false;

		while(!quit) {
			System.out.print("> ");
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

			try {
				String line = input.readLine();

				LinkedList<Token> tokens = tokenizer.tokenize(line);

				LinkedList<GameplayToken> gameplaytokens = parser.parseGameplayTokens(tokens);
				LinkedList<MotionToken> motiontokens = parser.parseMotionTokens(tokens);
				LinkedList<OverrideToken> overrides = parser.parseOverrides(tokens); // redesign this?

				if (tokens.isEmpty() && (gameplaytokens.isEmpty() && motiontokens.isEmpty() && overrides.isEmpty())) {
					System.out.println("Your command could not be understood. Try again.");
				}
				else if (!tokens.isEmpty() && (gameplaytokens.isEmpty() && motiontokens.isEmpty() && overrides.isEmpty())) {
					System.out.println("Your command is incomplete. Try again.");
				}

				while (!gameplaytokens.isEmpty()) {
					ObjectToken obj = (ObjectToken) gameplaytokens.pop();
					VerbToken verb = (VerbToken) gameplaytokens.pop();

					if (obj.getObjectType() != game.getObjectTypeAtCurrentLocation()) {
						System.out.format("There is no %s at the current location.%n", obj.getObjectType().toString());
					}
					else if (verb.getVerbType() == VerbType.KILL) {
						if (inventory.isEmpty()) {
							System.out.format("You do not have the weapons in your inventory to kill this %s.%n", obj.getObjectType().toString());
						}
						else {
							System.out.println("Killing it.");
							// remove from location and deactivate it
						}
					}
					else if (verb.getVerbType() == VerbType.TAKE) {
						if (inventory.isFull()) {
							System.out.format("You do not have the space to inventory this %s.", obj.getObjectType().toString());
							System.out.println("If you wish, you can drop another item and try again.");
						}
						else {
							inventory.addItem(game.getObjectAtCurrentLocation());
							// remove it from the location
						}
					}
				}

				while (!motiontokens.isEmpty()) {
					MotionToken curr = motiontokens.pop();
					String direction = "";
					switch (curr.getMotionType()) {
						case N:
							game.moveNorth(); break;
						case S:
							game.moveSouth(); break;
						case E:
							game.moveEast(); break;
						case W:
							game.moveWest(); break;
					}
					System.out.format("Your new coordinates are (%d, %d).%n", game.curr_X, game.curr_Y);
					game.printLocationInfo();
				}

				while (!overrides.isEmpty()) {
					OverrideToken curr = overrides.pop();
					if ((curr.getToken()).equals("SAVE")) {
						System.out.println("Saving game state...");
					}
					if ((curr.getToken()).equals("QUIT")) {
						System.out.println("Goodbye!");
						quit = true;
					}
				}
			}

			catch (IOException e) {
				System.err.println("Caught IOException: " + e.getMessage());
			}

		}
	}

	private void initializeObjects() {
		for (int location : locations) {
			location = 0;
		}

		addObject(ObjectType.TIGER, 6, 6);
		addObject(ObjectType.DAGGER, 4, 5);
	}

	private void addObject(ObjectType type, int X, int Y) {
		Object obj = new Object(type);
		objects.add(obj);
		locations[X*GAMESPACE_LENGTH + Y] = obj.getID();
	}

	private void moveNorth() {
		curr_Y = (curr_Y + 1) % GAMESPACE_LENGTH;
		System.out.println("Moving North...");
	}

	private void moveSouth() {
		curr_Y = (curr_Y - 1 + GAMESPACE_LENGTH) % GAMESPACE_LENGTH;
		System.out.println("Moving South...");
	}

	private void moveEast() {
		curr_X = (curr_X + 1) % GAMESPACE_LENGTH;
		System.out.println("Moving East...");
	}

	private void moveWest() {
		curr_X = (curr_X - 1 + GAMESPACE_LENGTH) % GAMESPACE_LENGTH;
		System.out.println("Moving West...");
	}

	private void printHelpMessage() {
		System.out.format("%nTo move around in the game, use:%n");
		System.out.format("  N: North%n  S: South%n  W: West%n  E: East%n");
		System.out.println("To interact with creatures in the game, use:");
		System.out.format("  caress <creature>: Put an item in your inventory%n");
		System.out.format("  drop <creature>: Remove an item from your inventory%n");
		System.out.println("Inventoriable items like weapons can be carried and dropped:");
		System.out.format("  take <item>: Put an item in your inventory%n");
		System.out.format("  drop <item>: Remove an item from your inventory%n");
		System.out.format("  examine <item>: Examine an item in your inventory or at your current location%n");
		System.out.println("Some items cannot be carried in your inventory but can still be examined.");
		System.out.format("  examine <item>: Examine an uninventoriable item at your current location%n%n");
		printLocationInfo();
	}

	private void printLocationInfo() {
		int curr_location = curr_X * GAMESPACE_LENGTH + curr_Y;
		ObjectType obj = getObjectTypeAtCurrentLocation();
		if (obj != null) {
			System.out.println("There's something here.");
			System.out.format("It is a %s.%n", obj.toString());
		}
	}

	private ObjectType getObjectTypeAtCurrentLocation() {
		Object obj = getObjectAtCurrentLocation();
		return (obj == null) ? null : obj.getType();
	}

	private Object getObjectAtCurrentLocation() {
		int curr_location = curr_X * GAMESPACE_LENGTH + curr_Y;
		if (locations[curr_location] != 0) {
			return objects.get(locations[curr_location] - 1);
		}
		return null;
	}
}
