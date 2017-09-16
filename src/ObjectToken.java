
public class ObjectToken extends GameplayToken {

  private final ObjectType objecttype;

	public ObjectToken(ObjectType objecttype) {
    super(GameplayTokenType.OBJECT);
		this.objecttype = objecttype;
	}

  @Override
  public String getToken() {
    return objecttype.name();
  }

  public ObjectType getObjectType() {
    return objecttype;
  }

}
