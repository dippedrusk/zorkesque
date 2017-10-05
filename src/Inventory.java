
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

  public void addItem(Object obj) {
    objects.add(obj);
  }

  public boolean isFull() {
    return (objects.size() == MAX_CAPACITY);
  }

  public void printItems() {
    if (isEmpty()) {
      System.out.format("Your inventory is currently empty.%n%n");
      return;
    }
    System.out.println("Your inventory contains:");
    for (Object obj : objects) {
      System.out.format("  %s%n", obj.getType().toString());
    }
  }
}
