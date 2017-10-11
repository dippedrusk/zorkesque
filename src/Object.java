
public class Object {

  private final ObjectType type;
  private final int initialHealth;
  private int currentHealth;
  private boolean active;

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
      case MANGO:
      case BANANA:
        this.initialHealth = 20;
        break;
      default:
        this.initialHealth = 0;
        break;
    }
    this.type = type;
    this.currentHealth = this.initialHealth;
    this.active = true;
    this.id = numObjects++;
  }

  public int getID() {
    return id;
  }

  public int getHealth() {
    return currentHealth;
  }

  public ObjectType getType() {
    return type;
  }

  public void deactivateObject() {
    this.active = false;
  }

  public void activateObject() {
    this.active = true;
  }

  public boolean isCreature() {
    if ((this.type == ObjectType.TIGER) ||
    (this.type == ObjectType.ELEPHANT) ||
    (this.type == ObjectType.RABBIT)) {
      return true;
    }
    return false;
  }

  public boolean isActive() {
    return active;
  }
}
