
public class Token {

	private TokenType type;

	public Token(TokenType type) {
		this.type = type;
	}

  public String getTokenType() {
		return type.name();
	}

	public static Token tokenizable (String s) {
		Token ret = OverrideToken.tokenizableAsOverride(s);
		if (ret == null)
		{
			ret = GameplayToken.tokenizableAsGameplay(s);
		}
		return ret;
	}

}
