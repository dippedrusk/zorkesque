
import java.io.*;
import java.util.*;

public class Zorkesque {

	private static final int GAMESPACE_LENGTH = 10;
	private static final int START_XY = 5;
	private static final int START_HEALTH = 50;
	private static final int INVENTORY_CAPACITY = 5;

	public static void main(String[] args) {

		Zorkesque game = new Zorkesque();

		Tokenizer tokenizer = new Tokenizer();
		Parser parser = new Parser();
		Inventory inventory = new Inventory(INVENTORY_CAPACITY);
		Map map = new Map(GAMESPACE_LENGTH, START_XY, START_XY);
		boolean quit = false;
		boolean won = false;
		int health = START_HEALTH;

		System.out.format("%nWelcome to Zorkesque.%n");
		game.printHelpMessage();
		System.out.println();
		map.printLocationDescription();
		System.out.println();

		while(!quit) {
			if (health < 10) {
				System.out.println("You are low on health. Find food immediately!");
			}

			ZorkesqueObject healer = map.isHere(ZorkesqueObjectType.HEALER);
			if (healer != null) {
				System.out.println("A wild spirit emerges from the ground in front of you.");
				System.out.println("It heals you and then disappears into thin air.");
				health = 100;
			}

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

			parser.parseHumour(tokens);
			LinkedList<Token> gameplaytokens = parser.parseGameplayTokens(tokens);
			LinkedList<MotionToken> motiontokens = parser.parseMotionTokens(tokens);
			LinkedList<OverrideToken> overrides = parser.parseOverrides(tokens);

			if (tokens.isEmpty() && (gameplaytokens.isEmpty() && motiontokens.isEmpty() && overrides.isEmpty())) {
				System.out.println("I beg your pardon?");
			}

			while (!gameplaytokens.isEmpty()) {
				ObjectToken objtoken = (ObjectToken) gameplaytokens.pop();
				VerbToken verbtoken = (VerbToken) gameplaytokens.pop();
				ZorkesqueObjectType objtype = objtoken.getObjectType();
				VerbType verbtype = verbtoken.getVerbType();

				ZorkesqueObject obj = map.isHere(objtype);
				if (verbtype == VerbType.DROP) {
					if (inventory.isEmpty()) {
						System.out.format("Your inventory is empty. You have no %s to drop.%n", objtype.toString());
					}
					else {
						ZorkesqueObject todrop = inventory.containsItem(objtype);
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
					if (obj.isCreature() && !obj.isActive()) {
						System.out.println("Why must you torture this poor dead animal so?");
					}
					else if (inventory.isEmpty()) {
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
				else if (verbtype == VerbType.EAT) {
					if (obj.isCreature() && obj.isActive()) {
						System.out.format("You cannot eat a live %s.%n", objtype.toString());
						System.out.println("Try killing it first.");
					}
					else {
						health += obj.getHealth();
						map.removeObject(obj);
						System.out.format("Your energy is replenished by the %s you ate.%n", objtype.toString());
					}
				}
				else if (verbtype == VerbType.EXAMINE) {
					obj.printObjectDescription();
				}
			}

			while (!motiontokens.isEmpty()) {
				MotionToken curr = motiontokens.pop();
				map.updateCurrentLocation(curr.getMotionType());
				health--;
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
					case HEALTH:
						System.out.format("Your health is %d.%n", health);
						break;
					case HELP:
						game.printHelpMessage();
						break;
					case QUIT:
						quit = true;
						System.out.println("Goodbye!");
						break;
				}
			}

			if (healer != null) {
				map.removeObject(healer);
			}

			System.out.println();
			if (health == 0) {
				System.out.println("Having exhausted all your energy exploring, you are now dead.");
				System.out.println("Better luck next time.");
				System.out.println();
				quit = true;
			}
		}
	}

	private void printHelpMessage() {
		System.out.format("  N: Move North%n  S: Move South%n");
		System.out.format("  W: Move West%n  E: Move East%n");
		System.out.println("  kill (object): Kill creature if you have a weapon in your inventory");
		System.out.println("  eat (object): Eat food or a dead creature");
		System.out.println("  take (object): Add something to your inventory");
		System.out.println("  drop object: Remove something from your inventory");
		System.out.println("  examine object: Examine something at your location");
		System.out.println("  look: See what is around you");
		System.out.println("  inventory: Get a list of what is in your inventory");
		System.out.println("  quit: Exit Zorkesque");
		System.out.println("  help: Print this message");
	}
}
