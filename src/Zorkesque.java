
import java.io.*;
import java.util.*;

public class Zorkesque {

	private static final int GAMESPACE_LENGTH = 10;
	private static final int START_XY = 5;
	private static final int START_HEALTH = 50;
	private static final int INVENTORY_CAPACITY = 5;
	private int health = START_HEALTH;

	public static void main(String[] args) {

		Zorkesque game = new Zorkesque();

		System.out.format("%nWelcome to Zorkesque.%n");
		game.printHelpMessage();
		System.out.println();

		Tokenizer tokenizer = new Tokenizer();
		Parser parser = new Parser();
		Inventory inventory = new Inventory(INVENTORY_CAPACITY);
		Map map = new Map(GAMESPACE_LENGTH, START_XY, START_XY);
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

				Object obj = map.isHere(objtype);
				if (verbtype == VerbType.DROP) {
					if (inventory.isEmpty()) {
						System.out.format("Your inventory is empty. You have no %s to drop.%n", objtype.toString());
					}
					else {
						Object todrop = inventory.containsItem(objtype);
						if (todrop == null) {
							System.out.format("There is no %s in your inventory.%n", objtype.toString());
						}
						else {
							inventory.removeItem(todrop);
							map.addObject(todrop);
							System.out.format("The %s has been dropped from your inventory.%n", objtype.toString());
						}
					}
				}
				else if (obj == null) {
					System.out.format("There is no %s to %s at the current location.%n", objtype.toString(), verbtype.toString());
				}
				else if (verbtype == VerbType.KILL) {
					if (inventory.isEmpty()) {
						System.out.format("You do not have the weapons in your inventory to kill this %s.%n", objtype.toString());
					}
					else {
						map.removeObject(obj);
						System.out.format("You have slain the %s.%n", objtype.toString());
					}
				}
				else if (verbtype == VerbType.TAKE) {
					if (inventory.isFull()) {
						System.out.format("You do not have the space to inventory this %s.%n", objtype.toString());
						System.out.println("If you wish, you can drop another item and try again.");
					}
					else {
						inventory.addItem(obj);
						map.removeObject(obj);
						System.out.format("The %s has been added to your inventory.%n", objtype.toString());
					}
				}
			}

			while (!motiontokens.isEmpty()) {
				MotionToken curr = motiontokens.pop();
				map.updateCurrentLocation(curr.getMotionType());
				map.printLocationDescription();
			}

			while (!overrides.isEmpty()) {
				OverrideToken curr = overrides.pop();
				switch (curr.getOverrideType()) {
					case LOOK:
						map.printLocationDescription();
						break;
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
			System.out.println();
		}
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
	}
}
