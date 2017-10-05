
public class Object {

  private final ObjectType type;
  private final int initialHealth;
  private int currentHealth;
  private boolean status;

  private int id;

  public static int numObjects = 0;

  public Object(ObjectType type) {
    switch (type) {
      case TIGER:
        this.initialHealth = 65;
        break;
      case ELEPHANT:
        this.initialHealth = 75;
        break;
      case RABBIT:
        this.initialHealth = 25;
        break;
      default:
        this.initialHealth = 0;
        break;
    }
    this.type = type;
    this.currentHealth = this.initialHealth;
    this.status = true;
    this.id = numObjects++;
  }

  public int getID() {
    return id;
  }

  public ObjectType getType() {
    return type;
  }
}
