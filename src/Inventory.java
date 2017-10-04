
import java.util.LinkedList;

public class Inventory {

  private LinkedList<Object> objects;
  public static final int MAX_CAPACITY = 5;

  public Inventory() {
    this.objects = new LinkedList<Object>();
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
}
