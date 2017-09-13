
public class OverrideToken extends Token {

  public final OverrideTokenType overridetokentype;

	public OverrideToken(OverrideTokenType overridetokentype) {
    super(TokenType.OVERRIDE);
		this.overridetokentype = overridetokentype;
	}

  public String getToken() {
		return overridetokentype.name();
	}

  public static Token tokenizableAsOverride(String s) {
    for (OverrideTokenType o : OverrideTokenType.values()) {
      if ((o.name()).equals(s)) {
        return new OverrideToken(o);
      }
    }
    return null;
  }

}
