
import java.util.*;

public class Map {

	private ArrayList<LinkedList<Integer>> locations = new ArrayList<LinkedList<Integer>>();;
	private ArrayList<Object> objects = new ArrayList<Object>();

  private final int GAMESPACE_LENGTH;
	private int curr_X;
	private int curr_Y;

	public Map(int GAMESPACE_LENGTH, int START_X, int START_Y) {
    for (int i = 0; i < GAMESPACE_LENGTH * GAMESPACE_LENGTH; i++) {
			LinkedList<Integer> list = new LinkedList<Integer>();
			this.locations.add(list);
		}
    this.GAMESPACE_LENGTH = GAMESPACE_LENGTH;
		this.curr_X = START_X;
		this.curr_Y = START_Y;
	}

  public void addObjectAtCoordinates(Object obj, int X, int Y) {
		int location = X * GAMESPACE_LENGTH + Y;
		int id = obj.getID();
		objects.add(obj);
		(locations.get(location)).add(id);
	}

	public void addObject(Object obj) {
		int current_location = curr_X * GAMESPACE_LENGTH + curr_Y;
		int id = obj.getID();
		objects.add(obj);
		(locations.get(current_location)).add(id);
	}

  public LinkedList<Object> getObjectsAtCurrentLocation() {
		int current_location = curr_X * GAMESPACE_LENGTH + curr_Y;
		LinkedList<Integer> ids = locations.get(current_location);
		LinkedList<Object> ret = new LinkedList<Object>();
		for (int i = 0; i < ids.size(); i++) {
			int id = ids.get(i);
			ret.add(objects.get(id));
		}
		return ret;
	}

  public void removeObject(Object obj) {
		int current_location = curr_X * GAMESPACE_LENGTH + curr_Y;
    (locations.get(current_location)).removeFirstOccurrence(obj.getID());
		objects.remove(obj);
  }

  public void printLocationDescription() {
    System.out.println("You are surrounded by thick tropical vegetation.");
		LinkedList<Object> objects = getObjectsAtCurrentLocation();
		if (objects.isEmpty()) {
			return;
		}
		else if (objects.size() == 1) {
			System.out.format("There is a %s here.%n", objects.peek().getType().toString());
		}
		else {
			System.out.println("Here, there are:");
			for (Object o : objects) {
				System.out.format("  %s%n", o.getType().toString());
			}
		}
  }

  public void updateCurrentLocation(MotionType direction) {
		switch (direction) {
			case N:
				curr_Y = (curr_Y + 1) % GAMESPACE_LENGTH;
				System.out.println("Moving North...");
				break;
			case S:
				curr_Y = (curr_Y - 1 + GAMESPACE_LENGTH) % GAMESPACE_LENGTH;
				System.out.println("Moving South...");
				break;
			case E:
				curr_X = (curr_X + 1) % GAMESPACE_LENGTH;
				System.out.println("Moving East...");
				break;
			case W:
				curr_X = (curr_X - 1 + GAMESPACE_LENGTH) % GAMESPACE_LENGTH;
				System.out.println("Moving West...");
				break;
		}
  }

	public Object isHere(ObjectType type) {
		LinkedList<Object> objects = getObjectsAtCurrentLocation();
		for (Object o : objects) {
			if (o.getType() == type) {
				return o;
			}
		}
		return null;
	}
}
