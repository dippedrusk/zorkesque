
import java.io.*;
import java.util.LinkedList;

public class JavaParser {

	public static void main(String[] args) {
		System.out.println("Welcome to this attempt at a Java lexer-parser.");
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
				LinkedList<OverrideToken> overrides = parser.parseOverrides(tokens);

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
}
