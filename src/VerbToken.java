
public class VerbToken extends Token {

  private final VerbType verbtype;

	public VerbToken(VerbType verbtype) {
    super(TokenType.VERB);
		this.verbtype = verbtype;
	}

  public VerbType getVerbType() {
    return verbtype;
  }

  public static Token tokenizableAsVerb(String s) {
    for (VerbType v : VerbType.values()) {
      if ((v.name()).equals(s)) {
        return new VerbToken(v);
      }
    }
    return null;
  }

}
