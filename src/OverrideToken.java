
public class OverrideToken extends Token {

  private final OverrideTokenType overridetokentype;

	public OverrideToken(OverrideTokenType overridetokentype) {
    super(TokenType.OVERRIDE);
		this.overridetokentype = overridetokentype;
	}

  public OverrideTokenType getOverrideType() {
		return overridetokentype;
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
