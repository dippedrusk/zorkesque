
public class ObjectToken extends Token {

  private final ObjectType objecttype;

	public ObjectToken(ObjectType objecttype) {
    super(TokenType.OBJECT);
		this.objecttype = objecttype;
	}

  public ObjectType getObjectType() {
    return objecttype;
  }

  public static Token tokenizableAsObject(String s) {
    for (ObjectType o : ObjectType.values()) {
      if ((o.name()).equals(s)) {
        return new ObjectToken(o);
      }
    }
    return null;
  }

}
