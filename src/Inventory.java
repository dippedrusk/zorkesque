
import java.util.LinkedList;

public class Inventory {

  private LinkedList<Object> objects;
  public final int MAX_CAPACITY;

  public Inventory(int MAX_CAPACITY) {
    this.objects = new LinkedList<Object>();
    this.MAX_CAPACITY = MAX_CAPACITY;
  }

  public boolean isEmpty() {
    return objects.isEmpty();
  }

  public boolean isFull() {
    return (objects.size() == MAX_CAPACITY);
  }

  public void addItem(Object obj) {
    objects.add(obj);
  }

  public void removeItem(Object obj) {
    objects.remove(obj);
  }

  public Object containsItem(ObjectType type) {
    for (Object obj : objects) {
      if (obj.getType() == type) {
        return obj;
      }
    }
    return null;
  }

  public void printItems() {
    if (isEmpty()) {
      System.out.println("Your inventory is currently empty.");
      return;
    }
    System.out.println("Your inventory contains:");
    for (Object obj : objects) {
      System.out.format("  %s%n", obj.getType().toString());
    }
  }
}
