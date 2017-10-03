
import java.io.*;
import java.util.LinkedList;
//import javax.xml.parsers.*;

public class JavaGame {

	public static final int GAMESPACE_LENGTH = 10;
	public int[] locations = new int[GAMESPACE_LENGTH * GAMESPACE_LENGTH];
	private int start_XY = 5;
	private int curr_X;
	private int curr_Y;

	public static void main(String[] args) {

		JavaGame game = new JavaGame();

		System.out.format("%nWelcome to Zorkesque.%n");

		game.curr_X = game.start_XY;
		game.curr_Y = game.start_XY;
		System.out.format("You are starting out at coordinates (%d, %d).%n", game.curr_X, game.curr_Y);

		game.initializeObjects();

		Tokenizer tokenizer = new Tokenizer();
		Parser parser = new Parser();
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

		Object tiger = new Object(ObjectType.TIGER);
		locations[6*GAMESPACE_LENGTH + 6] = tiger.getID();

		Object dagger = new Object(ObjectType.DAGGER);
		locations[4*GAMESPACE_LENGTH + 5] = dagger.getID();
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

}
