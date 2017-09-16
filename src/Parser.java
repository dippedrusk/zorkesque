
import java.util.LinkedList;

public class Parser {

	public Parser() {
	}

	public LinkedList<OverrideToken> parseOverrides(LinkedList<Token> gametokens) {

		LinkedList<OverrideToken> overrides = new LinkedList<OverrideToken>();

		for (Token curr : gametokens) {
			if ((curr.getTokenType()).equals("OVERRIDE")) {
				overrides.push((OverrideToken) curr);
			}
		}

		return overrides;
	}

	public LinkedList<GameplayToken> parseGameplayTokens(LinkedList<Token> gametokens) {

		LinkedList<GameplayToken> gameplaytokens = new LinkedList<GameplayToken>();

		VerbToken currverb = null;

		for (Token curr : gametokens) {
			if ((curr.getTokenType()).equals("GAMEPLAY")) {
				GameplayToken currg = (GameplayToken) curr;
				if ((currg.getGameplayTokenType()).equals("VERB")) {
					currverb = (VerbToken) currg;
				}
				else if ((currg.getGameplayTokenType()).equals("OBJECT")) {
					ObjectToken obj = (ObjectToken) currg;
					if (currverb != null) {
						if (validCommand(currverb.getVerbType(), obj.getObjectType())) {
							gameplaytokens.push(currverb);
							gameplaytokens.push(currg);
							System.out.format("VALID! (%s %s)%n", currverb.getToken().toLowerCase(), obj.getToken().toLowerCase());
							currverb = null;
						}
						else {
							System.out.format("You cannot %s a %s!%n", currverb.getToken().toLowerCase(), obj.getToken().toLowerCase());
						}
					}
				}
			}
		}

		return gameplaytokens;

	}

	public LinkedList<MotionToken> parseMotionTokens(LinkedList<Token> gametokens) {

		LinkedList<MotionToken> motiontokens = new LinkedList<MotionToken>();

		for (Token curr : gametokens) {
			if ((curr.getTokenType()).equals("MOTION")) {
				MotionToken curr_m = (MotionToken) curr;
				motiontokens.push(curr_m);
				String direction = "";
				switch (curr_m.getToken()) {
					case "N":
						direction = "North"; break;
					case "S":
						direction = "South"; break;
					case "E":
						direction = "East"; break;
					case "W":
						direction = "West"; break;
				}
				System.out.format("Moving %s...%n", direction);
			}
		}

		return motiontokens;
	}

	boolean validCommand(VerbType verb, ObjectType obj) {
    for (VerbType v : obj.availableGameplays) {
      if (v == verb) {
        return true;
      }
    }
    return false;
  }

}
