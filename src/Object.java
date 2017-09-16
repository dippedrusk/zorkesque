
public abstract class Object {

  private final ObjectType type;
  private final int initialHealth;
  private int currentHealth;
  private boolean status;

  public Object() {
    this.type = ObjectType.TIGER;
    this.initialHealth = 65;
    this.currentHealth = this.initialHealth;
    this.status = true;
  }

  /*
   * Verbs that can be called on this object (boolean array?) <<<<<<<<<<<<<<<<
   * Health in case of a creature (current and initial)
   * Amount of damage dealt by an attack
   * Status: active-alive / inactive-dead
   */

}
