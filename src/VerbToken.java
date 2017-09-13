
public class VerbToken extends GameplayToken {

  private final VerbType verbtype;

	public VerbToken(VerbType verbtype) {
    super(GameplayTokenType.VERB);
		this.verbtype = verbtype;
	}

  @Override
  public String getToken() {
    return verbtype.name();
  }

}
