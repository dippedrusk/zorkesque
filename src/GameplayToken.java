
public class GameplayToken extends Token {

  /*
   * Make this an abstract class???
   */

  public final GameplayTokenType GameplayType;

	public GameplayToken(GameplayTokenType gameplaytype) {
    super(TokenType.GAMEPLAY);
		this.GameplayType = gameplaytype;
	}

  public String getGameplayTokenType() {
		return GameplayType.name();
	}

  public String getToken() {
    return null;
  }

  public static Token tokenizableAsGameplay(String s) {
    for (ObjectType o : ObjectType.values()) {
      if ((o.name()).equals(s)) {
        return new ObjectToken(o);
      }
    }
    for (VerbType v : VerbType.values()) {
      if ((v.name()).equals(s)) {
        return new VerbToken(v);
      }
    }
    return null;
  }

}
