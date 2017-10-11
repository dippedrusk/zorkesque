
import java.util.LinkedList;

public class Parser {

	public Parser() {
	}

	public void parseHumour(LinkedList<Token> gametokens) {
		for (Token curr : gametokens) {
			if (curr.getTokenType() == TokenType.HUMOUR) {
				((HumourToken) curr).respondToHumour();
			}
		}
	}

	public LinkedList<OverrideToken> parseOverrides(LinkedList<Token> gametokens) {

		LinkedList<OverrideToken> overrides = new LinkedList<OverrideToken>();

		for (Token curr : gametokens) {
			if (curr.getTokenType() == TokenType.OVERRIDE) {
				overrides.push((OverrideToken) curr);
			}
		}

		return overrides;
	}

	public LinkedList<Token> parseGameplayTokens(LinkedList<Token> gametokens) {

		LinkedList<Token> gameplaytokens = new LinkedList<Token>();

		VerbToken currverb = null;

		for (Token curr : gametokens) {
			if (curr.getTokenType() == TokenType.VERB) {
				currverb = (VerbToken) curr;
			}
			else if (curr.getTokenType() == TokenType.OBJECT) {
				ObjectToken obj = (ObjectToken) curr;
				if (currverb != null) {
					if (validCommand(currverb.getVerbType(), obj.getObjectType())) {
						gameplaytokens.push(currverb);
						gameplaytokens.push(curr);
						currverb = null;
					}
					else {
						System.out.format("You cannot %s a %s!%n", currverb.getVerbType().toString(), obj.getObjectType().toString());
					}
				}
			}
		}

		return gameplaytokens;
	}

	public LinkedList<MotionToken> parseMotionTokens(LinkedList<Token> gametokens) {

		LinkedList<MotionToken> motiontokens = new LinkedList<MotionToken>();

		for (Token curr : gametokens) {
			if (curr.getTokenType() == TokenType.MOTION) {
				MotionToken curr_m = (MotionToken) curr;
				motiontokens.push(curr_m);
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
