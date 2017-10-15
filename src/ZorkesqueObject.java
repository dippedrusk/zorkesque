
public class ZorkesqueObject {

  private final ZorkesqueObjectType type;
  private final int initialHealth;
  private int currentHealth;
  private boolean active;
  private String message;

  private int id;

  public static int numObjects = 0;

  public ZorkesqueObject(ZorkesqueObjectType type) {
    this(type, null);
  }

  public ZorkesqueObject(ZorkesqueObjectType type, String custom_message) {
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
      case HEALER:
        this.initialHealth = 505;
        break;
      default:
        this.initialHealth = 0;
        break;
    }
    this.type = type;
    this.currentHealth = this.initialHealth;
    this.active = true;
    this.id = numObjects++;
    this.message = custom_message;
    if (custom_message != null) {
      message = custom_message;
    }
    else {
      switch (type) {
        case TIGER:
          message = "This is nothing but a generic tiger that might maul you to bits if you don't kill it soon.";
          break;
        case ELEPHANT:
          message = "This is a run-of-the-mill elephant that appears to be foraging for food.";
          break;
        case RABBIT:
          message = "This is a sweet bunny hopping around the forest floor.";
          break;
        case MANGO:
        case BANANA:
          message = "This is a generic piece of tropical fruit that appears edible";
          break;
        case HEALER:
          message = "I'm afraid the healer has vanished into thin air as I mentioned earlier.";
          break;
        case SWORD:
          message = "This is a slightly rusty iron sword with no distinguishing marks.";
          break;
        case FLAMETHROWER:
          message = "This is a generic flamethrower. Although I have to say, flamethrowers are pretty special things to begin with.";
          break;
        case DAGGER:
          message = "This is a sharp dagger that could do good damage if well-wielded. Use it wisely.";
          break;
        default:
          message = "";
          break;
      }
    }
  }

  public int getID() {
    return id;
  }

  public int getHealth() {
    return currentHealth;
  }

  public ZorkesqueObjectType getType() {
    return type;
  }

  public void deactivateObject() {
    this.active = false;
  }

  public void activateObject() {
    this.active = true;
  }

  public boolean isCreature() {
    if ((this.type == ZorkesqueObjectType.TIGER) ||
    (this.type == ZorkesqueObjectType.ELEPHANT) ||
    (this.type == ZorkesqueObjectType.RABBIT)) {
      return true;
    }
    return false;
  }

  public boolean isActive() {
    return active;
  }

  public void printObjectDescription() {
    if (!active) {
      System.out.println("This is a dead carcass and nothing more.");
    }
    else {
      System.out.format("%s%n", this.message);
    }
  }
}
