
public class ObjectToken extends Token {

  private final ZorkesqueObjectType objecttype;

	public ObjectToken(ZorkesqueObjectType objecttype) {
    super(TokenType.OBJECT);
		this.objecttype = objecttype;
	}

  public ZorkesqueObjectType getObjectType() {
    return objecttype;
  }

  public static Token tokenizableAsObject(String s) {
    for (ZorkesqueObjectType o : ZorkesqueObjectType.values()) {
      if ((o.name()).equals(s)) {
        return new ObjectToken(o);
      }
    }
    return null;
  }

}
