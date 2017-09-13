
import java.util.LinkedList;

public class Parser {

	public Parser() {
	}

	public LinkedList<OverrideToken> parseOverrides(LinkedList<Token> gametokens) {

		LinkedList<OverrideToken> overrides = new LinkedList<OverrideToken>();

		while (!gametokens.isEmpty()) {
			Token curr = gametokens.pop();
			if ((curr.getTokenType()).equals("OVERRIDE")) {
				overrides.push((OverrideToken) curr);
			}
		}

		return overrides;
	}

	public LinkedList<GameplayToken> parseGameplayTokens(LinkedList<Token> gametokens) {

		LinkedList<GameplayToken> gameplaytokens = new LinkedList<GameplayToken>();

		// Parse gameplay stuff

		/*while (!gametokens.isEmpty()) {
			Token curr = gametokens.pop();
			if ((curr.getTokenType()).equals("OVERRIDE")) {
				overrides.push((OverrideToken) curr);
			}
		}*/

		return gameplaytokens;

	}

}
