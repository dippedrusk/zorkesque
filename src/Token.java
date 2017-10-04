
public class Token {

	private TokenType type;

	public Token(TokenType type) {
		this.type = type;
	}

  public TokenType getTokenType() {
		return type;
	}

	public static Token tokenizable (String s) {
		Token ret = OverrideToken.tokenizableAsOverride(s);
		if (ret == null)
		{
			ret = ObjectToken.tokenizableAsObject(s);
		}
		if (ret == null)
		{
			ret = VerbToken.tokenizableAsVerb(s);
		}
		if (ret == null)
		{
			ret = MotionToken.tokenizableAsMotion(s);
		}
		return ret;
	}

}
