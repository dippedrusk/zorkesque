
public class GameplayToken extends Token {

  /*
   * Make this an abstract class???
   */

  public final GameplayTokenType GameplayType;

	public GameplayToken(GameplayTokenType gameplaytype) {
    super(TokenType.GAMEPLAY);
		this.GameplayType = gameplaytype;
	}

  public GameplayTokenType getGameplayTokenType() {
		return GameplayType;
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
    for (MotionType m : MotionType.values()) {
      if ((m.name()).equals(s)) {
        return new MotionToken(m);
      }
    }
    return null;
  }

}
