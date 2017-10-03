
import java.util.LinkedList;

public class Tokenizer {

	public Tokenizer() {
	}

	public LinkedList<Token> tokenize(String line) {

		String[] tokens = line.split("[^a-zA-Z]+");

		LinkedList<Token> gametokens = new LinkedList<Token>();

		for (int i = 0; i < tokens.length; i++) {
			Token gametoken = Token.tokenizable(tokens[i].toUpperCase());
			if (gametoken != null) {
				gametokens.add(gametoken);
			}
		}

		return gametokens;
	}

}
