
import java.io.*;
import java.util.*;

public class Zorkesque {

	private static final int GAMESPACE_LENGTH = 10;
	private static final int START_XY = 5;
	private static final int START_HEALTH = 50;
	private static final int INVENTORY_CAPACITY = 5;

	private ArrayList<Object> objects;
	private Map map;

	private int curr_X;
	private int curr_Y;
	private int health;

	public Zorkesque() {
		this.curr_X = START_XY;
		this.curr_Y = START_XY;
		this.health = START_HEALTH;

		this.objects = new ArrayList<Object>();
		this.map = new Map(GAMESPACE_LENGTH);

		this.addObjects();
	}

	private void addObjects() {
		addObject(ObjectType.TIGER, 6, 6);
		addObject(ObjectType.DAGGER, 4, 5);
	}

	public static void main(String[] args) {

		Zorkesque game = new Zorkesque();

		System.out.format("%nWelcome to Zorkesque.%n");
		game.printHelpMessage();

		Tokenizer tokenizer = new Tokenizer();
		Parser parser = new Parser();
		Inventory inventory = new Inventory(INVENTORY_CAPACITY);
		boolean quit = false;
		boolean won = false;

		while(!quit) {
			System.out.print("> ");
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			String line = null;

			try {
				line = input.readLine();
			}
			catch (IOException e) {
				System.err.println("Caught IOException: " + e.getMessage());
			}

			LinkedList<Token> tokens = tokenizer.tokenize(line);

			LinkedList<Token> gameplaytokens = parser.parseGameplayTokens(tokens);
			LinkedList<MotionToken> motiontokens = parser.parseMotionTokens(tokens);
			LinkedList<OverrideToken> overrides = parser.parseOverrides(tokens);

			if (tokens.isEmpty() && (gameplaytokens.isEmpty() && motiontokens.isEmpty() && overrides.isEmpty())) {
				System.out.println("Your command could not be understood. Try again.");
			}
			else if (!tokens.isEmpty() && (gameplaytokens.isEmpty() && motiontokens.isEmpty() && overrides.isEmpty())) {
				System.out.println("Your command is incomplete. Try again.");
			}

			while (!gameplaytokens.isEmpty()) {
				ObjectToken objtoken = (ObjectToken) gameplaytokens.pop();
				VerbToken verbtoken = (VerbToken) gameplaytokens.pop();
				ObjectType objtype = objtoken.getObjectType();
				VerbType verbtype = verbtoken.getVerbType();

				Object obj = game.isHere(objtype);
				if (obj == null) {
					System.out.format("There is no %s at the current location.%n", objtype.toString());
				}
				else if (verbtype == VerbType.KILL) {
					if (inventory.isEmpty()) {
						System.out.format("You do not have the weapons in your inventory to kill this %s.%n", objtype.toString());
					}
					game.removeObject(obj);
					System.out.format("You have slain the %s.%n", objtype.toString());
				}
				else if (verbtype == VerbType.TAKE) {
					if (inventory.isFull()) {
						System.out.format("You do not have the space to inventory this %s.", objtype.toString());
						System.out.println("If you wish, you can drop another item and try again.");
					}
					else {
						inventory.addItem(obj);
						game.removeObject(obj);
						System.out.format("The %s has been added to your inventory.%n", objtype.toString());
					}
				}
			}

			while (!motiontokens.isEmpty()) {
				MotionToken curr = motiontokens.pop();
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
				game.printLocationInfo();
			}

			while (!overrides.isEmpty()) {
				OverrideToken curr = overrides.pop();
				switch (curr.getOverrideType()) {
					case INVENTORY:
						inventory.printItems();
						break;
					case HELP:
						game.printHelpMessage();
						break;
					case SAVE:
						System.out.println("Saving game state...");
						// TODO: Implement this
						break;
					case QUIT:
						quit = true;
						System.out.println("Goodbye!");
						break;
				}
			}

			System.out.println("");
		}
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
		System.out.println("To move around in the game, use:");
		System.out.format("  N: North%n  S: South%n  W: West%n  E: East%n");
		System.out.println("To interact with creatures in the game, use:");
		System.out.format("  caress <creature>: Caress a creature");
		System.out.format("  kill <creature>: Kill the creature if you have a weapon in your inventory%n");
		System.out.println("Inventoriable items like weapons can be carried and dropped:");
		System.out.format("  take <item>: Put an item in your inventory%n");
		System.out.format("  drop <item>: Remove an item from your inventory%n");
		System.out.format("  examine <item>: Examine an item in your inventory or at your current location%n");
		System.out.println("Some items cannot be carried in your inventory but can still be examined.");
		System.out.println("  examine <item>: Examine an uninventoriable item at your current location");
		printLocationInfo();
	}

	private void printLocationInfo() {
		LinkedList<Object> objects = getObjectsAtCurrentLocation();
		if (objects.isEmpty()) {
			return;
		}
		if (objects.size() == 1) {
			System.out.format("There is a %s here.%n", objects.peek().getType().toString());
		}
		else {
			System.out.println("Here, there are:");
			for (Object o : objects) {
				System.out.format("  %s%n", o.getType().toString());
			}
		}
	}

	private LinkedList<Object> getObjectsAtCurrentLocation() {
		LinkedList<Integer> ids = map.getObjectsAtLocation(curr_X, curr_Y);
		LinkedList<Object> ret = new LinkedList<Object>();
		for (int i = 0; i < ids.size(); i++) {
			int id = ids.get(i);
			ret.add(objects.get(id));
		}
		return ret;
	}

	private Object isHere(ObjectType type) {
		LinkedList<Object> objects = getObjectsAtCurrentLocation();
		for (Object o : objects) {
			if (o.getType() == type) {
				return o;
			}
		}
		return null;
	}

	private void addObject(ObjectType type, int X, int Y) {
		Object obj = new Object(type);
		int id = obj.getID();
		objects.add(obj);
		map.addObject(obj, X, Y);
	}

	private void removeObject(Object obj) {
		objects.remove(obj);
		map.removeObject(obj, curr_X, curr_Y);
	}
}
